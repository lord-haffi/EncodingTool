import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 15.08.2015
 * @author 
 */

public class PrefGUI extends Frame {
    // Anfang Attribute
    private String[][] codes = {{"DE","Deutsch"},{"EN","English"},{"ES","Espanol"}};
    private Label labelLan = new Label();
    private Choice choice1 = new Choice();
    private Button buttonOverride = new Button();
    private Button buttonCancel = new Button();
    //private Checkbox checkboxBackground = new Checkbox();
    //private Button browseBackground = new Button();
    //private Label labelBackground = new Label();
    
    private GUI g;
    //private File backgIm = null;
    // Ende Attribute

    public PrefGUI(GUI g) { 
        // Frame-Initialisierung
        super(Main.language[0]+" - "+Main.language[36]);
        this.g = g;
        g.setEnabled(false);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) { close();}
            });
        int frameWidth = 540; 
        int frameHeight = 120;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setResizable(false);
        Panel cp = new Panel(null);
        add(cp);
        // Anfang Komponenten

        labelLan.setBounds(24, 16, 150, 23);
        labelLan.setText(Main.language[37]+":");
        labelLan.setFont(new Font("Arial", Font.PLAIN, 12));
        cp.add(labelLan);
        choice1.setBounds(184, 16, 246, 23);
        choice1.add(codes[0][1]);
        listLanguages();
        //choice1.select(Main.setInfos[0]);
        cp.add(choice1);
        buttonOverride.setBounds(352, 50, 163, 33); //72 x-Loc
        buttonOverride.setLabel(Main.language[38]);
        buttonOverride.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    apply(evt);
                }
            });
        buttonOverride.setFont(new Font("Arial", Font.PLAIN, 18));
        cp.add(buttonOverride);
        buttonCancel.setBounds(168, 50, 155, 33);
        buttonCancel.setLabel(Main.language[16]);
        buttonCancel.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    close();
                }
            });
        buttonCancel.setFont(new Font("Arial", Font.PLAIN, 18));
        cp.add(buttonCancel);
        /*checkboxBackground.setBounds(9, 40, 165, 23);
        checkboxBackground.setLabel(Main.language[45]+":");
        checkboxBackground.setFont(new Font("Arial", Font.PLAIN, 12));
        if(!Main.setInfos[1].equals("")) checkboxBackground.setState(true);
        cp.add(checkboxBackground);
        browseBackground.setBounds(448, 40, 35, 25);
        browseBackground.setLabel("...");
        browseBackground.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    browseBackground_ActionPerformed(evt);
                }
            });
        browseBackground.setFont(new Font("Arial", Font.PLAIN, 18));
        cp.add(browseBackground);
        labelBackground.setBounds(184, 40, 246, 23);
        labelBackground.setText(Main.setInfos[2]);
        labelBackground.setFont(new Font("Arial", Font.PLAIN, 12));
        cp.add(labelBackground);*/
        // Ende Komponenten

        setVisible(true);
    } // end of public PrefGUI

    // Anfang Methoden

    private void listLanguages(){
        File userDir = new File(System.getProperty("user.dir"));
        File[] list = userDir.listFiles();
        //System.out.println("Dir: "+userDir.getAbsolutePath());
        for(int i=0; i<list.length; i++){
            String n = list[i].getName();
            //System.out.println(i+": Name des Files: "+n);
            if(!list[i].isDirectory() && n.startsWith("language_")){
                String lan = n.substring(n.lastIndexOf('_')+1,n.length());
                for(int j=0; j<codes.length; j++){
                    if(lan.equals(codes[j][0])){
                        choice1.add(codes[j][1]);
                        if(Main.setInfos[0].equals(lan))
                            choice1.select(codes[j][1]);
                        j=codes.length;
                    }
                }
                //System.out.println("'"+n+"' gefunden.");
            }
        }
    }
    /*private void deleteOldBackground(){
        File[] list = new File(Main.workdir).listFiles();
        for(int i=0; i<list.length; i++){
            if(!list[i].isDirectory() && list[i].getName().startsWith("background")){
                list[i].delete();
            }
        }
    }*/

    private void apply(ActionEvent evt){
        File settings = new File(Main.workdir+"settings");
        String selLan = choice1.getSelectedItem();
        for(int i=0; i<codes.length; i++){
            if(selLan.equals(codes[i][1])){
                Main.setInfos[0] = codes[i][0];
                i=codes.length;
            }
        }
        //Main.setInfos[0] = choice1.getSelectedItem();
        /*if(backgIm != null) deleteOldBackground();
        if(!checkboxBackground.getState()){
            deleteOldBackground();
            Main.setInfos[2] = "";
        }else if(labelBackground.getText().equals(Main.setInfos[2])){
            
        }else if(Streamer.copyFile(backgIm, new File(Main.workdir+"background."+Streamer.getEnd(backgIm)), true)){
            Main.setInfos[2] = labelBackground.getText();
        }else{
            g.showError(Main.language[48]+": "+Streamer.getCopyFehler());
            Main.setInfos[2] = "";
        }*/
        
        g.showMessage(Main.language[39]);

        Streamer.datenSchreiben(("lan:"+Main.setInfos[0]+"\r\nfreq:"+Main.setInfos[1]).getBytes(), settings, true, false);
        close();
    } // end of buttonOverride_ActionPerformed
    public void close(){
        dispose();
        g.setEnabled(true);
        g.toFront();
    } // end of buttonCancel_ActionPerformed

    /*private void browseBackground_ActionPerformed(ActionEvent evt){
        JFileChooser chooser = new JFileChooser();
        FileFilter fil = new FileNameExtensionFilter(Main.language[46]+" (*.jpeg, *.jpg, *.png, *.gif)", "gif", "jpg", "jpeg", "png");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(fil);
        if(chooser.showDialog(null, Main.language[47]) == JFileChooser.APPROVE_OPTION){
            backgIm = chooser.getSelectedFile();
            labelBackground.setText(backgIm.getName());
            checkboxBackground.setState(true);
        }
    }*/

    // Ende Methoden
} // end of class PrefGUI
