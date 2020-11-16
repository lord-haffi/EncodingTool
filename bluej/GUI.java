import java.awt.*;
import java.awt.event.*;
import anm.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 19.05.2016
 * @author Leon Haffmans
 */

public class GUI extends Frame {
    // Anfang Attribute
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuDatei = new Menu(Main.language[1]);
    private final MenuItem menuDateiMenuItemDateiauswählen = new MenuItem(Main.language[35]);
    private final Label labelDatei = new Label();
    private final Button buttonBrowseFile = new Button();
    private final Button buttonCode = new Button();
    private final TextField textFieldPassword = new TextField();
    private final Menu menuOptions = new Menu(Main.language[36]);
    private final MenuItem menuOptionsMenuItemOptionen = new MenuItem(Main.language[36]);
    private final Label labelPassword = new Label();
    private final Button buttonKey = new Button();
    private final TextField textFieldPassword2 = new TextField();
    private final Label labelPassword2 = new Label();
    private final TextField textFieldDatei = new TextField();
    private final TextField textFieldOutputFile = new TextField();
    private final Button buttonBrowseOutput = new Button();
    private final Label labelOutput = new Label();
    private final Label infoLabel = new Label();
    //public ShowProcess procFrame = null;
    // Ende Attribute
    
    private File defaultDir = new File("J:\\");
    private final Panel cp = new Panel(null);
    private byte[] coding_data = null;
    private String coding_ext = null;
    private int mode = 0; //Default = 0, Encoding = 1, Decoding = 2
    //private final Animation anmCode = new Animation(buttonCode), anmKey = new Animation(buttonKey);
    //private AnimationObject anmCode
    private AnimationObjectGroup toEncode = new AnimationObjectGroup(), toDecode = new AnimationObjectGroup(), toDefault = new AnimationObjectGroup();

    public GUI(){
        this(null);
    }
    public GUI(File toCode) { 
        // Frame-Initialisierung
        super(Main.language[0]);
        addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent evt){System.exit(0);}
            });
        int frameWidth = 641; 
        int frameHeight = 345;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setResizable(false);
        
        add(cp);
        // Anfang Komponenten

        setMenuBar(menuBar);
        menuBar.add(menuDatei);
        menuDateiMenuItemDateiauswählen.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    browseCodingfile(evt);
                }
            });

        labelDatei.setBounds(40, 24, 110, 20);
        labelDatei.setText(Main.language[1]+":");
        cp.add(labelDatei);
        buttonBrowseFile.setBounds(568, 48, 27, 25);
        buttonBrowseFile.setLabel("...");
        buttonBrowseFile.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    browseCodingfile(evt);
                }
            });
        buttonBrowseFile.setFont(new Font("Arial", Font.PLAIN, 18));
        cp.add(buttonBrowseFile);
        buttonCode.setBounds(40, 200, 195, 25);
        buttonCode.setLabel(Main.language[2]+"/"+Main.language[3]);
        buttonCode.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    decodeEncode(evt);
                }
            });
        buttonCode.setFont(new Font("Arial", Font.PLAIN, 12));
        buttonCode.setEnabled(false);
        cp.add(buttonCode);
        textFieldPassword.setBounds(40, 160, 555, 25);
        textFieldPassword.setEchoChar('*');
        textFieldPassword.addKeyListener(new KeyListener(){
                public void keyPressed(KeyEvent e) {
                    //System.out.println("'"+e.getKeyChar()+"' pressed");
                    if(textFieldPassword.getEchoChar() != '*'){
                        textFieldPassword.setText("");
                        textFieldPassword.setEchoChar('*');
                        textFieldPassword2.setEnabled(true);
                    }
                }
                public void keyReleased(KeyEvent e) {
                    //System.out.println(e.getKeyChar() + " released");
                }
                public void keyTyped(KeyEvent e) {
                    //System.out.println(e.getKeyChar() + " typed");
                }
            });
        cp.add(textFieldPassword);
        menuBar.add(menuOptions);
        menuOptionsMenuItemOptionen.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    preferences(evt);
                }
            });

        menuOptions.add(menuOptionsMenuItemOptionen);
        menuDatei.add(menuDateiMenuItemDateiauswählen);
        labelPassword.setBounds(40, 136, 110, 20);
        labelPassword.setText(Main.language[4]+":");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        cp.add(labelPassword);
        buttonKey.setBounds(432, 200, 163, 25);
        buttonKey.setLabel(Main.language[5]);
        buttonKey.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    loadCreateKey(evt);
                }
            });
        buttonKey.setFont(new Font("Arial", Font.PLAIN, 12));
        buttonKey.setEnabled(false);
        cp.add(buttonKey);
        textFieldPassword2.setBounds(40, 216, 555, 25);
        textFieldPassword2.setEchoChar('*');
        //cp.add(textFieldPassword2);
        labelPassword2.setBounds(40, 192, 200, 20);
        labelPassword2.setText(Main.language[6]+":");
        //cp.add(labelPassword2);
        textFieldDatei.setBounds(40, 48, 518, 25);

        textFieldDatei.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldDatei.setEditable(false);
        cp.add(textFieldDatei);
        textFieldOutputFile.setBounds(40, 104, 518, 25);
        textFieldOutputFile.setEditable(false);
        cp.add(textFieldOutputFile);
        buttonBrowseOutput.setBounds(568, 104, 27, 25);
        buttonBrowseOutput.setLabel("...");
        buttonBrowseOutput.addActionListener(new ActionListener(){ 
                public void actionPerformed(ActionEvent evt){ 
                    browseOutput(evt);
                }
            });
        buttonBrowseOutput.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonBrowseOutput.setEnabled(false);
        cp.add(buttonBrowseOutput);
        labelOutput.setBounds(40, 80, 110, 20);
        labelOutput.setText(Main.language[7]+":");
        cp.add(labelOutput);
        infoLabel.setBounds(168, 256, 291, 25);
        //infoLabel.setText("Hallo");
        cp.add(infoLabel);
        // Ende Komponenten
        // Anfang Animation initialisieren
        AnimationObject anmCodeToEncode = new AnimationObject(buttonCode), anmKeyToEncode = new AnimationObject(buttonKey),
            anmCodeToDecode = new AnimationObject(buttonCode), anmKeyToDecode = new AnimationObject(buttonKey),
            anmCodeToDefault = new AnimationObject(buttonCode), anmKeyToDefault = new AnimationObject(buttonKey);
        
        anmCodeToEncode.addKeyframe(40, 256, 123, 25, 500);
        anmKeyToEncode.addKeyframe(464, 256, 131, 25, 500);
        anmCodeToDecode.addKeyframe(40, 200, 123, 25, 500);
        anmKeyToDecode.addKeyframe(464, 200, 131, 25, 500);
        anmCodeToDefault.addKeyframe(40, 200, 195, 25, 500);
        anmKeyToDefault.addKeyframe(432, 200, 163, 25, 500);
        
        toEncode.addAnimationObject(anmCodeToEncode);
        toEncode.addAnimationObject(anmKeyToEncode);
        toDecode.addAnimationObject(anmCodeToDecode);
        toDecode.addAnimationObject(anmKeyToDecode);
        toDefault.addAnimationObject(anmCodeToDefault);
        toDefault.addAnimationObject(anmKeyToDefault);
        
        
        toEncode.cmd = new CmdAfterAnimation(){
                public void run(){
                    cp.add(textFieldPassword2);
                    cp.add(labelPassword2);
                    buttonCode.setEnabled(true);
                    buttonKey.setEnabled(true);
                    buttonBrowseOutput.setEnabled(true);
                }
            };
        toDecode.cmd = new CmdAfterAnimation(){
                public void run(){
                    buttonCode.setEnabled(true);
                    buttonKey.setEnabled(true);
                    buttonBrowseOutput.setEnabled(true);
                }
            };
        toDefault.cmd = new CmdAfterAnimation(){
                public void run(){
                    buttonCode.setLabel(Main.language[2]+"/"+Main.language[3]);
                    buttonKey.setLabel(Main.language[5]);
                }
            };
        // Ende Animation initialisieren
        if(toCode != null)
            setSrcFile(toCode);
        setupDrop();
        
        setVisible(true);
    } // end of public GUI
    private void setupDrop(){
        DropTarget dropTargetSrc = new DropTarget(){
                public synchronized void dragEnter(DropTargetDragEvent dtde) {
                    //System.out.println("Drag enter");
                    
                    // Check the data format and reject if we can't accept it
                    //System.err.println(dtde.getCurrentDataFlavors().toString());
                    //System.out.println("DragEnter: "+dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor));
                    //dtde.acceptDrag(DnDConstants.ACTION_COPY);
                    if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.rejectDrag();
                    } else {
                        // Otherwise, indicate that we can take the operation as a copy
                        try{
                            java.util.List<File> list = (java.util.List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            for(int i=0; i<list.size(); i++){
                                if(list.get(i).isFile()){
                                    dtde.acceptDrag(DnDConstants.ACTION_COPY);
                                    //System.out.println("File gefunden! "+list.get(i).getAbsolutePath());
                                    return;
                                }
                            }
                            dtde.rejectDrag();
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                public synchronized void drop(DropTargetDropEvent dtde) {
                    //System.out.println("Drop");
                    
                    // Verify we can accept the data format
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        
                        // Indicate we'll accept the drop
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        
                        try {
                            // Get the data and set our label's image icon to the new image.
                            // Save a copy of the image so we can support dragging it out
                            java.util.List<File> list = (java.util.List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            //assert image != null;
                            if(list.size() == 1)
                                setSrcFile(list.get(0));
                            else
                                for(int i=0; i<list.size(); i++){
                                    if(list.get(i).isFile()){
                                        setSrcFile(list.get(i));
                                        i = list.size();
                                    }
                                }
                            //System.out.println("Drop was successful");
                            //imageLabel.setIcon(new ImageIcon(image));
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                public void dragExit(DropTargetEvent dte) {
                    //System.out.println("Drag exit");
                }
                
                public void dragOver(DropTargetDragEvent dtde) {
                    //System.out.println("Drag over");
                }
                
                public void dropActionChanged(DropTargetDragEvent dtde) {
                    //System.out.println("Drop action changed");
                }
            }, dropTargetKey = new DropTarget(){
                public synchronized void dragEnter(DropTargetDragEvent dtde) {
                    //System.out.println("Drag enter");
                    
                    // Check the data format and reject if we can't accept it
                    //System.err.println(dtde.getCurrentDataFlavors().toString());
                    //System.out.println("DragEnter: "+dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor));
                    //dtde.acceptDrag(DnDConstants.ACTION_COPY);
                    if(mode != 2){
                        dtde.rejectDrag();
                        return;
                    }
                    if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.rejectDrag();
                    } else {
                        // Otherwise, indicate that we can take the operation as a copy
                        try{
                            java.util.List<File> list = (java.util.List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            for(int i=0; i<list.size(); i++){
                                String ext = Streamer.getEnd(list.get(i));
                                if(list.get(i).isFile() && ext != null && ext.equals(Coder.keyExt)){
                                    dtde.acceptDrag(DnDConstants.ACTION_COPY);
                                    //System.out.println("File gefunden! "+list.get(i).getAbsolutePath());
                                    return;
                                }
                            }
                            dtde.rejectDrag();
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                public synchronized void drop(DropTargetDropEvent dtde) {
                    //System.out.println("Drop");
                    
                    // Verify we can accept the data format
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        
                        // Indicate we'll accept the drop
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        
                        try {
                            // Get the data and set our label's image icon to the new image.
                            // Save a copy of the image so we can support dragging it out
                            java.util.List<File> list = (java.util.List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            //assert image != null;
                            if(list.size() == 1)
                                setKeyFile(list.get(0));
                            else
                                for(int i=0; i<list.size(); i++){
                                    String ext = Streamer.getEnd(list.get(i));
                                    if(list.get(i).isFile() && ext != null && ext.equals(Coder.keyExt)){
                                        setKeyFile(list.get(i));
                                        i = list.size();
                                    }
                                }
                            //System.out.println("Drop was successful");
                            //imageLabel.setIcon(new ImageIcon(image));
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                public void dragExit(DropTargetEvent dte) {
                    //System.out.println("Drag exit");
                }
                
                public void dragOver(DropTargetDragEvent dtde) {
                    //System.out.println("Drag over");
                }
                
                public void dropActionChanged(DropTargetDragEvent dtde) {
                    //System.out.println("Drop action changed");
                }
            };
        textFieldDatei.setDropTarget(dropTargetSrc);
        textFieldPassword.setDropTarget(dropTargetKey);
    }

    // Anfang Methoden

    public void browseCodingfile(ActionEvent evt){
        //changeToEncode();
        JFileChooser chooser = new JFileChooser();
        FileFilter f = new FileNameExtensionFilter(Main.language[8]+" (."+Coder.codedExt+")",Coder.codedExt);
        chooser.setFileFilter(f);
        chooser.setCurrentDirectory(defaultDir);
        chooser.setDialogTitle(Main.language[0]+" - "+Main.language[9]);
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File choosed = chooser.getSelectedFile();
            setSrcFile(choosed);
            if(textFieldPassword.getEchoChar() != '*'){
                textFieldPassword.setText("");
                textFieldPassword.setEchoChar('*');
                textFieldPassword2.setEnabled(true);
            }
        }
    }
    private void setSrcFile(File choosed){
        if(!choosed.exists()){
            //System.out.println("Error: the file '"+choosed.getAbsolutePath()+"' doesn't exists.");
            showError("'"+choosed.getAbsolutePath()+"' "+Main.language[10]);
            return;
        }
        infoLabel.setText(Main.language[46]);
        coding_data = Streamer.datenAuslesen(choosed);
        infoLabel.setText("");
        if(coding_data == null){
            showError(Main.language[11]+" '"+choosed.getAbsolutePath()+"': "+Streamer.getAusleseFehler());
            return;
        }
        if(choosed.isFile()){
            File out = choosed;
            if(Coder.codedExt.equals(Streamer.getEnd(choosed))){
                coding_ext = "";
                int i=coding_data.length-1;
                for(; i>=0; i--){
                    if(coding_data[i] == (byte)'.')
                        i=-1;
                    else
                        coding_ext = ((char)coding_data[i])+coding_ext;
                }
                if(i == -1){
                    showError(Main.language[12]);
                    coding_data = null;
                    coding_ext = null;
                    return;
                }
                changeToDecode();
            }else{
                changeToEncode();
                coding_ext = Coder.codedExt;
            }
            textFieldDatei.setText(choosed.getAbsolutePath());
            textFieldOutputFile.setText(Streamer.setEnding(coding_ext,choosed).getAbsolutePath());
        }else
            showError("choosed file is not a file: isDirectory(): "+choosed.isDirectory()+", exists(): "+choosed.exists());
    }
    
    /*private class ProcessFrame extends ShowProcess{
        public ProcessFrame(String title, String message, GUI cont){
            super(null, title, message, true, cont);
        }
        public void runProgram(){
            setVisible(true);
        }
    }*/

    public void decodeEncode(ActionEvent evt){
        File src = new File(textFieldDatei.getText()), dest = new File(textFieldOutputFile.getText()), keyFile = null;
        //procFrame = new ProcessFrame();
        
        if(textFieldPassword.getEchoChar() == '\0' || textFieldPassword.getText().equals(textFieldPassword2.getText()) && mode == 1 || textFieldPassword.getText().length() > 0 && mode == 2){
            byte[] key = textFieldPassword.getText().getBytes();
            if(textFieldPassword.getEchoChar() == '*'){
                if(textFieldPassword.getText().length() < 4){
                    showError(Main.language[13]);
                    return;
                }
            }else if(mode == 1){
                key = Coder.keymaker(128); //ein zufälliger 1024-bit-key wird erzeugt
                keyFile = Streamer.setEnding(Coder.keyExt, dest);
                boolean flag = true;
                while(keyFile.exists() && flag){
                    String[] choices = {Main.language[14],Main.language[15],Main.language[16]};
                    int ret = JOptionPane.showOptionDialog(null,"'"+keyFile.getAbsolutePath()+"' "+Main.language[17],Main.language[0]+" - "+Main.language[18],JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,choices,choices[1]);
                    switch(ret){
                        case 0:
                            flag = false;
                            break;
                        case 1:
                            JFileChooser chooser = new JFileChooser();
                            FileFilter f = new FileNameExtensionFilter(Main.language[19]+" (."+Coder.keyExt+")",Coder.keyExt);
                            chooser.setAcceptAllFileFilterUsed(false);
                            chooser.setFileFilter(f);
                            chooser.setCurrentDirectory(keyFile.getParentFile());
                            chooser.setDialogTitle(Main.language[0]+" - "+Main.language[20]);
                            if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                                File out = chooser.getSelectedFile();
                                if(!Coder.keyExt.equals(Streamer.getEnd(out)))
                                    out = new File(out.getAbsolutePath()+"."+Coder.keyExt);
                                keyFile = out;
                                System.out.println(keyFile.getAbsolutePath());
                            }
                            break;
                        case 2:
                        case -1:
                            return;
                        default:
                            showError("Wat zum Teufel ist hier passiert?");
                            return;
                    }
                }
            }else{
                keyFile = new File(textFieldPassword.getText());
                key = Streamer.datenAuslesen(keyFile);
                if(key == null){
                    showError(Main.language[11]+" '"+keyFile.getAbsolutePath()+"': "+Streamer.getAusleseFehler());
                    return;
                }
            }
            
            if(dest.isFile()){
                String[] choices = {Main.language[21],Main.language[22],Main.language[16]};
                int ret = JOptionPane.showOptionDialog(null,"'"+dest.getAbsolutePath()+"' "+Main.language[17],Main.language[0]+" - "+Main.language[18],JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,choices,choices[1]);
                switch(ret){
                    case 0:
                        
                        break;
                    case 1:
                        browseOutput(null);
                        decodeEncode(evt);
                        return;
                    case 2:
                    case -1:
                        return;
                    default:
                        showError("Wat zum Teufel ist hier passiert?");
                        return;
                }
            }else if(dest.exists()){
                showError(Main.language[23]);
                return;
            }
            //Datei verschlüsseln
            //byte[] daten = Streamer.datenAuslesen(src);
            if(coding_data != null){
                String ext = Streamer.getEnd(src);
                if(ext == null) ext="";
                byte[] deEncoded_data = null;
                final ShowProcess procFrame;
                if(mode == 1){
                    procFrame = new ShowProcess(null, Main.language[0]+" - "+Main.language[2], Main.language[40], true, this){
                            public void runProgram(){
                                setVisible(true);
                            }
                        };
                    deEncoded_data = new byte[coding_data.length+1+ext.length()];
                    deEncoded_data = Coder.datenVerschluesseln(coding_data, deEncoded_data, key, procFrame);
                    if(procFrame.abbruch()){
                        procFrame.writeLine(Main.language[45]);
                        return;
                    }
                    for(int i=ext.length()-1; i>=0; i--){
                        deEncoded_data[deEncoded_data.length-ext.length()+i] = (byte)ext.charAt(i);
                    }
                    deEncoded_data[deEncoded_data.length-1-ext.length()] = (byte)'.';
                }else{
                    procFrame = new ShowProcess(null, Main.language[0]+" - "+Main.language[3], Main.language[41], true, this){
                            public void runProgram(){
                                setVisible(true);
                            }
                        };
                    deEncoded_data = new byte[coding_data.length-1-coding_ext.length()];
                    deEncoded_data = Coder.datenEntschluesseln(coding_data, deEncoded_data, key, procFrame);
                    if(procFrame.abbruch()){
                        procFrame.writeLine(Main.language[45]);
                        return;
                    }
                }
                //procFrame.ready();
                procFrame.writeLine("'"+dest.getAbsolutePath()+"' "+Main.language[42]);
                if(Streamer.datenSchreiben(deEncoded_data, dest, true, false)){
                    if(keyFile != null && mode == 1){
                        if(Streamer.datenSchreiben(key, keyFile, true, false))
                            procFrame.writeLine(Main.language[24]);
                        else
                            procFrame.writeLine("Error: "+Main.language[25]+" '"+keyFile.getAbsolutePath()+"': "+Streamer.getSchreibeFehler());
                    }else
                        procFrame.writeLine(Main.language[24]);
                }else
                    procFrame.writeLine("Error: "+Main.language[25]+" '"+dest.getAbsolutePath()+"': "+Streamer.getSchreibeFehler());
                procFrame.ready();
            }else
                showError(Main.language[11]+" '"+src.getAbsolutePath()+"': "+Streamer.getAusleseFehler());
        }else if(mode == 1)
            showError(Main.language[26]);
        else
            showError(Main.language[27]);
    } // end of buttonCode_ActionPerformed

    public void preferences(ActionEvent evt){
        new PrefGUI(this);
        /*procFrame = new ShowProcess(null, "Titel", "Hier steht die Nachricht", true, this){
                public void runProgram(){
                    setVisible(true);
                    ready();
                }
            };*/
    } // end of menuOptionsMenuItemOptionen_ActionPerformed

    public void loadCreateKey(ActionEvent evt){
        if(mode == 1){
            //VERSCHLÜSSELN
            textFieldPassword.setEchoChar('\0');
            textFieldPassword.setText(Main.language[28]);
            //textFieldPassword2.setEchoChar('\0');
            textFieldPassword2.setText("n");
            textFieldPassword2.setText("");
            /*try{
                Thread.sleep(100);
            }catch(InterruptedException e){}*/
            textFieldPassword2.setEnabled(false);
            
            //if(!textFieldDatei.getText.equals("") && !textFielOutput.getText.equals(""))
        }else if(mode == 2){
            //ENTSCHLÜSSELN
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(Main.language[0]+" - "+Main.language[9]);
            chooser.setCurrentDirectory((new File(textFieldDatei.getText())).getParentFile());
            FileFilter f = new FileNameExtensionFilter(Main.language[19]+" (."+Coder.keyExt+")",Coder.keyExt);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(f);
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                setKeyFile(chooser.getSelectedFile());
        }
    } // end of buttonKey_ActionPerformed
    private void setKeyFile(File choosed){
        if(!choosed.exists()){
            showError("'"+choosed.getAbsolutePath()+"' "+Main.language[11]);
            return;
        }
        String ext = Streamer.getEnd(choosed);
        if(ext != null && ext.equals(Coder.keyExt)){
            textFieldPassword.setEchoChar('\0');
            textFieldPassword2.setText("n");
            textFieldPassword2.setText("");
            textFieldPassword2.setEnabled(false);
            textFieldPassword.setText(choosed.getAbsolutePath());
        }else
            showError(Main.language[29]+". "+Main.language[30]+" '"+Coder.keyExt+"' "+Main.language[31]);
    }

    public void browseOutput(ActionEvent evt){
        //changeToDefault();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(Main.language[0]+" - "+Main.language[20]);
        chooser.setCurrentDirectory(defaultDir);
        String ext = Coder.codedExt;
        if(mode == 1){
            FileFilter f = new FileNameExtensionFilter(Main.language[8]+" (."+Coder.codedExt+")",Coder.codedExt);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(f);
        }else if(mode == 2){
            //ext = irgendwas, steht in der verschlüsselten Datei;
            FileFilter f = new FileFilter(){
                @Override public boolean accept(File file){
                    return file.isDirectory() || coding_ext.equalsIgnoreCase(Streamer.getEnd(file)) || coding_ext.equals("") && Streamer.getEnd(file) == null;
                }

                @Override public String getDescription(){
                    if(!coding_ext.equals(""))
                        return Main.language[32]+" (."+coding_ext+")";
                    else
                        return Main.language[32]+" ("+Main.language[33]+")";
                }
            };
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(f);
            ext = coding_ext;
        }
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            File choosed = chooser.getSelectedFile();
            String extC = Streamer.getEnd(choosed), path = choosed.getAbsolutePath();
            if((extC == null || !ext.equals(extC)) && !ext.equals("")) path+="."+ext;
            else if(ext.equals("") && extC != null){
                showError(Main.language[29]+": '"+choosed.getName()+"'");
                return;
            }
            
            textFieldOutputFile.setText(path);
        }
    } // end of buttonBrowseOutput_ActionPerformed

    // Ende Methoden
    private void initAnimating(){
        Thread tEnc = toEncode.getThread(), tDec = toDecode.getThread(), tDef = toDefault.getThread();
        if(tEnc != null && tEnc.isAlive())
            toEncode.interrupt();
        if(tDec != null && tDec.isAlive())
            toDecode.interrupt();
        if(tDef != null && tDef.isAlive())
            toDefault.interrupt();
    }
    public void changeToEncode(){
        initAnimating();
        mode = 1;
        
        buttonCode.setLabel(Main.language[2]);
        buttonKey.setLabel(Main.language[28]);
        textFieldPassword.setText("n");
        textFieldPassword.setText("");
        textFieldPassword2.setText("n");
        textFieldPassword2.setText("");
        textFieldPassword2.setEnabled(true);
        toEncode.run();
    }
    public void changeToDecode(){
        initAnimating();
        mode = 2;
        
        cp.remove(textFieldPassword2);
        cp.remove(labelPassword2);
        textFieldPassword.setText("n");
        textFieldPassword.setText("");
        textFieldPassword2.setText("n");
        textFieldPassword2.setText("");
        buttonCode.setLabel(Main.language[3]);
        buttonKey.setLabel(Main.language[34]);
        toDecode.run();
    }
    public void changeToDefault(){
        initAnimating();
        mode = 0;
        
        buttonCode.setEnabled(false);
        buttonKey.setEnabled(false);
        cp.remove(textFieldPassword2);
        cp.remove(labelPassword2);
        textFieldPassword.setText("n");
        textFieldPassword.setText("");
        textFieldPassword2.setText("n");
        textFieldPassword2.setText("");
        toDefault.run();
    }
    public static void showError(String message){
        JOptionPane.showMessageDialog(null, "Error: "+message, Main.language[0]+" - Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void showMessage(String message){
        JOptionPane.showMessageDialog(null, message, Main.language[0], JOptionPane.PLAIN_MESSAGE);
    }
} // end of class GUI
