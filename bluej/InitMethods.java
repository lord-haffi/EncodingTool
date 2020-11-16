import java.io.File;
import javax.swing.JOptionPane;
/**
 * Write a description of class InitMethods here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InitMethods
{
    private final static String[][] codes = {{"userhome",System.getProperty("user.home")}};
    /**
     * Splittet ein byte-array.
     */
    public static Liste<byte[]> split(byte[] sequence, byte[] input){
        if(sequence != null && input != null && sequence.length > 0){
            Liste<byte[]> l = new Liste<>();
            int pos = 0;
            for(int i=0; i<input.length; i++){
                boolean b = true;
                if((i+sequence.length-1)<input.length){
                    for(int j=0; j<sequence.length; j++){
                        if(sequence[j] != input[i+j]){
                            b = false;
                            j = sequence.length;
                        }
                    }
                }else{
                    b = false;
                    i = input.length;
                }
                
                if(b){
                    byte[] el = new byte[i-pos];
                    System.arraycopy(input, pos, el, 0, i-pos);
                    pos = i+sequence.length;
                    i += sequence.length-1;
                    l.hintersLetzteEinfuegen(el);
                }
            }
            byte[] el = new byte[input.length-pos];
            System.arraycopy(input, pos, el, 0, el.length);
            l.hintersLetzteEinfuegen(el);
            
            return l;
        }else
            return null;
    }
    public static String getWorkDir(){
        File wdfile = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"workDir");
        byte[] dat = Streamer.datenAuslesen(wdfile);
        if(dat != null){
            String str = new String(dat), str2 = "";
            int i=0;
            for(; i<str.length()-1; i++){
                if(str.charAt(i) == '\\' && str.charAt(i+1) != '\\'){
                    String code = "";
                    for(int j=i+1; j<str.length(); j++){
                        if(str.charAt(j) != '\\')
                            code+=str.charAt(j);
                        else{
                            i=j;
                            j=str.length();
                        }
                    }
                    int j=0;
                    for(; j<codes.length; j++){
                        if(codes[j][0].equals(code)){
                            str2+=codes[j][1];
                            j=codes.length+1;
                        }
                    }
                    if(j==codes.length+1){
                        System.err.println("Der Tag '"+code+"' ist nicht definiert. (Default workdir ausgewählt)");
                        return System.getProperty("user.dir")+System.getProperty("file.separator");
                    }
                }else if(str.charAt(i) == '\\' && str.charAt(i+1) == '\\'){
                    str2+=System.getProperty("file.separator");
                    i++;
                }else
                    str2+=str.charAt(i);
            }
            if(i==str.length()-1) str2+=str.charAt(str.length()-1);
            return str2;
        }else
            return System.getProperty("user.dir")+System.getProperty("file.separator");
    }
    public static String[] getSettInfos(){
        File set = new File(Main.workdir+"settings");
        byte[] dat = Streamer.datenAuslesen(set);
        if(dat != null){
            byte[] seq = {(byte)'\r',(byte)'\n'};
            Liste<byte[]> list = split(seq, dat);
            list.toFirst();
            String[] ret = new String[list.length()];
            for(int i=0; i<list.length(); i++){
                ret[i] = "";
                //byte[] dat = list.getCurObject();
                for(int j=list.getCurObject().length-1; j>=0; j--){
                    if(list.getCurObject()[j] != (byte)':'){
                        ret[i]=(char)(list.getCurObject()[j]) + ret[i];
                    }else j=-1;
                }
                list.next();
            }
            return ret;
        }else{
            System.out.println("Error: No settings-file found.");
            System.exit(0);
            return null;
        }
    }
    public static String[] loadLan(String lan){
        File fl = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"language_"+lan);
        if(fl.exists() && lan != null && !lan.equalsIgnoreCase("de") && !lan.equals("")){
            byte[] dat = Streamer.datenAuslesen(fl), seq = {(byte)'\r',(byte)'\n'};
            Liste<byte[]> l = split(seq, dat);
            String[] ret = new String[l.length()];
            l.toFirst();
            for(int i=0; i<l.length(); i++){
                ret[i] = new String(l.getCurObject());
                l.next();
            }
            return ret;
        }else{
            String[] def = Main.defaultLan;
            
            if(lan != null && !lan.equalsIgnoreCase("de") && !lan.equals(""))
                JOptionPane.showMessageDialog(null, "Error: Fehler beim Laden des Language-Paketes ("+lan+").", def[0]+" - Error", JOptionPane.ERROR_MESSAGE);
            else if(lan == null || lan.equals(""))
                JOptionPane.showMessageDialog(null, "Error: Unbekannter Fehler aufgetreten: Variable 'lan' ist nicht initialisiert", def[0]+" - Error", JOptionPane.ERROR_MESSAGE);
            return def;
        }
    }
}
