//import library.streaming.Streamer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * Diese Klasse ist dazu gedacht einen Prozess eines Programms für den Benutzer anzuzeigen.
 * Sie ist abstrakt, da von ihr nur geerbt werden soll, um in den jeweiligen "Unterkonstruktoren" die jeweiligen Programme zu starten.
 * In diesen Konstruktoren muss außerdem noch "setVisible(true)" eingefügt werden.
 *
 * @version 1.2 vom 20. 06. 15
 * @author Leon Haffmans
 */

public abstract class ShowProcess extends Frame {
    // Anfang Attribute
    private Container cont = null;
    private TextArea textArea1 = new TextArea("", 1, 1, TextArea.SCROLLBARS_BOTH);
    private Label label1 = new Label();
    private Label fortschritt = new Label();
    private Label labelProcess = new Label();
    private Button button1 = new Button();
    private String log = "";
    private Button buttonCreateLogFile = new Button();
    private Button buttonAbbruch = new Button();
    private File dir = null;
    //public Abbruch abbruch = new Abbruch();
    private boolean abb = false;
    //@Deprecated private boolean logBActivated = true;
    // Ende Attribute

    /**
     * Initialisiert das GUI.
     */
    public ShowProcess(File logDir, String title, String message, boolean withAbbruch, Container cont) { 
        // Frame-Initialisierung
        super(title);
        this.cont = cont;
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {}
            });
        int frameWidth = 520; 
        int frameHeight = 366;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setResizable(false);
        Panel cp = new Panel(null);
        add(cp);
        // Anfang Komponenten

        textArea1.setBounds(24, 80, 456, 196);
        cp.add(textArea1);
        label1.setBounds(24, 16, 520, 36);
        label1.setText(message);
        label1.setFont(new Font("Arial", Font.PLAIN, 18));
        cp.add(label1);
        fortschritt.setBounds(24, 52, 78, 20);
        fortschritt.setText(Main.language[44]);
        fortschritt.setFont(new Font("Arial", Font.PLAIN, 14));
        cp.add(fortschritt);
        labelProcess.setBounds(102, 52, 134, 20);
        labelProcess.setText("0%");
        labelProcess.setFont(new Font("Arial", Font.PLAIN, 14));
        cp.add(labelProcess);
        button1.setBounds(344, 288, 139, 33);
        button1.setLabel(Main.language[43]);
        button1.setEnabled(false);
        button1.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    close(evt);
                }
            });
        button1.setFont(new Font("Arial", Font.PLAIN, 14));
        cp.add(button1);
        buttonCreateLogFile.setBounds(24, 288, 171, 33);
        buttonCreateLogFile.setLabel("Log-File erstellen");
        buttonCreateLogFile.setEnabled(false);
        buttonCreateLogFile.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    buttonCreateLogFile_ActionPerformed(evt);
                }
            });
        buttonCreateLogFile.setFont(new Font("Arial", Font.PLAIN, 14));
        if(logDir != null){
            cp.add(buttonCreateLogFile);
            this.dir = logDir;
        }
        buttonAbbruch.setBounds(208, 288, 123, 33);
        buttonAbbruch.setLabel(Main.language[16]);
        buttonAbbruch.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    buttonAbbruch_ActionPerformed(evt);
                }
            });
        buttonAbbruch.setFont(new Font("Arial", Font.PLAIN, 14));
        if(withAbbruch) cp.add(buttonAbbruch);
        if(this.cont != null) this.cont.setEnabled(false);
        runProgram();
        //ready();
        // Ende Komponenten
    } // end of public ShowProcess

    // Anfang Methoden

    public boolean abbruch(){
        return abb;
    }
    public abstract void runProgram();

    /**
     * Diese Methode muss ausgeführt werden, wenn das Programm fertig ist.
     * Sie aktiviert den Button, um das Fenter zu schließen und den log-Button (falls er nicht vorher deaktiviert wurde).
     */
    protected void ready(){
        button1.setEnabled(true);
        //if(this.logBActivated) buttonCreateLogFile.setEnabled(true);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt){
                    close(null);
                }
            });
        setProcessState((byte)100);
        //writeLine("Vorgang abgeschlossen.");
    }
    
    public void setProcessState(byte percent){
        if(percent >= 0 && percent <= 100)
            labelProcess.setText(percent+"%");
    }

    /**
     * Schreibt eine Zeile ins TextArea.
     */
    public void writeLine(String line){
        appendToLogText(line);
        textArea1.append("\n"+line);
    }
    
    /**
     * Funktioniert wie die Methode getStackTrace(), schreibt sie aber in's Textarea.
     */
    public void writeException(Exception e){
        String fehler = e.getMessage()+"\r\n"+e.toString();
        StackTraceElement[] st = e.getStackTrace();
        for(int i=0; i<st.length; i++){
            fehler+="\r\n\tat "+st[i].toString();
        }
        writeLine("Error: \r\n"+fehler);
    }
    /**
     * Fügt eine Zeile zum log-Text hinzu, die aber nicht im TextArea ausgegeben wird.
     */
    public void appendToLogText(String line){
        log += line+"\r\n";
    }

    private void close(ActionEvent evt) {
        dispose();
        if(cont != null)
            cont.setEnabled(true);
    } // end of button1_ActionPerformed

    private void buttonCreateLogFile_ActionPerformed(ActionEvent evt) {
        if(dir != null && Streamer.datenSchreiben(log.getBytes(), new File(dir.getAbsolutePath()+"\\log.txt"), true, false))
            textArea1.append("\nLog-Datei '"+dir.getAbsolutePath()+"' wurde erstellt.");
        else if(dir != null)
            textArea1.append("\n"+Streamer.getSchreibeFehler());
        else
            textArea1.append("\nKein Output angegeben.");
    } // end of buttonCreateLogFile_ActionPerformed

    private void buttonAbbruch_ActionPerformed(ActionEvent evt) {
        abb = true;
    } // end of buttonAbbruch_ActionPerformed
    // Ende Methoden
} // end of class Process