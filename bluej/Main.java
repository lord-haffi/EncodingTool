import java.util.List;
import javax.swing.JOptionPane;
/**
 * Write a description of class Main here.
 * 
 * @author Leon Haffmans
 * @version 13. 03. 2016
 */
public class Main
{
    //public final static String workdir = System.getProperty("user.home")+System.getProperty("file.separator")+"LManager_data"+System.getProperty("file.separator")+"LCoder_data"+System.getProperty("file.separator");
    public final static String[] defaultLan = {"LCoder v2.0","Datei","Verschlüsseln","Entschlüsseln","Passwort","Keydatei erstellen/laden","Passwort bestätigen","Output-Datei","Verschlüsselte Datei","Öffnen","existiert nicht.","Zugriffsfehler der Datei","Die Datei scheint beschädigt zu sein.",
        "Das Passwort muss mindestens 4 Zeichen enthalten.","Keydatei überschreiben","Keydatei ändern","Abbrechen","existiert bereits. Was möchten Sie tun?","Warnung","Keydatei","Speichern","Zieldatei überschreiben","Zieldatei ändern","Die Zieldatei ist ein Verzeichnis.","Der Vorgang wurde erfolgreich abgeschlossen.",
        "Schreibefehler der Datei","Die Passwörter stimmen nicht überein.","Kein Passwort angegeben.","Keydatei erstellen","Ungültige Datei ausgewählt","Sie müssen eine Datei mit der Endung","auswählen.","Entschlüsselte Datei","ohne Dateiendung","Keydatei laden","Datei auswählen","Optionen","Sprache","Übernehmen",
        "Bitte starten Sie das Programm neu, um die Änderungen zu übernehmen.","Die Datei wird verschlüsselt...","Die Datei wird entschlüsselt...","wird gespeichert...","Schließen","Fortschritt:","Der Vorgang wurde abgebrochen.","Datei wird ausgelesen..."};
    public final static String workdir = InitMethods.getWorkDir();
    public final static String[] setInfos = InitMethods.getSettInfos();
    public final static String[] language = InitMethods.loadLan(setInfos[0]);
    public static void main(String[] args){
		anm.Animater.hz = Double.parseDouble(setInfos[1]);
        if(args.length == 1)
            new GUI(new java.io.File(args[0]));
        else
            new GUI();
    }
}
