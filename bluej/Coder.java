
/**
 * Diese Klasse hat die Aufgabe, Daten in Form von byte-Arrays zu verschlüsseln und zu entschlüsseln.
 * 
 * @author Leon Haffmans
 * @version 20. 12. 2014
 */
public class Coder
{
    public final static String codedExt = "lcoded", keyExt = "lkey";
    /**
     * Diese Methode erzeugt einen zufälligen Key von einer parameterbedingten
     * Länge.
     */
    public static byte[] keymaker(int keylaenge){
        byte[] key = new byte[keylaenge];
        for(int i=0; i<keylaenge; i++){
            key[i] = (byte)(zufallszahlZiehen(1,255)-128);
        }
        return key;
    }
    private static int zufallszahlZiehen(int minimum, int maximum){
        if(maximum>1){
            int wert=0;
            wert =(int)Math.round(Math.random()*(maximum-minimum-1))+1+minimum;
            return wert;
        }else{
            return 0;
        }
    }
    /**
     * Diese Methode verschlüsselt übergebene Daten. Der Key muss im Parameter
     * mit angegeben werden.
     */
    public static byte[] datenVerschluesseln2(byte[] daten, byte[] key){
        if(daten != null && key != null && key.length > 0){
            int keyz=0;
            for(int i=0; i<daten.length; i++){
                if(daten[i] + key[keyz] <= 127)
                    daten[i] = (byte)(daten[i] + key[keyz]);
                else
                    daten[i] = (byte)(-128+key[keyz]-(128-daten[i]));
                if(keyz >= key.length-1)
                    keyz = 0;
                else
                    keyz++;
            }
            return daten;
        }else
            return null;
    }
    /**
     * Diese Methode entschlüsselt übergebene Daten mit einem übergebenen Key.
     */
    public static byte[] datenEntschluesseln2(byte[] daten, byte[] key){
        if(daten != null && key != null && !key.equals("")){
            int keyz=0;
            for(int i=0; i<daten.length; i++){
                if(daten[i] - key[keyz] >= -128)
                    daten[i] = (byte)(daten[i] - key[keyz]);
                else
                    daten[i] = (byte)(128-(key[keyz]-(128+daten[i])));
                if(keyz >= key.length-1)
                    keyz = 0;
                else
                    keyz++;
            }
            return daten;
        }else
            return null;
    }
    public static byte[] datenVerschluesseln(byte[] daten, byte[] key){
        if(daten != null && key != null && !key.equals("")){
            int keyz=0;
            for(int i=0; i<daten.length; i++){
                if(daten[i] + key[keyz] <= 127)
                    daten[i] = (byte)(daten[i] + key[keyz]);
                else
                    daten[i] = (byte)(-128+key[keyz]-(128-daten[i]));
                if(i%2 == 0)
                    keyz = key.length-1-keyz;
                else{
                    keyz = key.length-keyz;
                    if(keyz == key.length)
                        keyz = 0;
                }
            }
            return daten;
        }else
            return null;
    }
    public static byte[] datenEntschluesseln(byte[] daten, byte[] key){
        if(daten != null && key != null && !key.equals("")){
            int keyz=0;
            for(int i=0; i<daten.length; i++){
                if(daten[i] - key[keyz] >= -128)
                    daten[i] = (byte)(daten[i] - key[keyz]);
                else
                    daten[i] = (byte)(128-(key[keyz]-(128+daten[i])));
                if(i%2 == 0)
                    keyz = key.length-1-keyz;
                else{
                    keyz = key.length-keyz;
                    if(keyz == key.length)
                        keyz = 0;
                }
            }
            return daten;
        }else
            return null;
    }
    public static byte[] datenVerschluesseln(byte[] daten, byte[] decoded_data, byte[] key, ShowProcess g){
        if(daten != null && key != null && !key.equals("") && decoded_data != null){
            g.writeLine(Main.language[40]);
            int keyz=0;
            for(int i=0; i<daten.length && i<decoded_data.length && !g.abbruch(); i++){
                g.setProcessState((byte)((double)i/(double)daten.length*100));
                if(daten[i] + key[keyz] <= 127)
                    decoded_data[i] = (byte)(daten[i] + key[keyz]);
                else
                    decoded_data[i] = (byte)(-128+key[keyz]-(128-daten[i]));
                if(i%2 == 0)
                    keyz = key.length-1-keyz;
                else{
                    keyz = key.length-keyz;
                    if(keyz == key.length)
                        keyz = 0;
                }
            }
            if(g.abbruch())
                return null;
            return decoded_data;
        }else{
            return null;
        }
    }
    public static byte[] datenEntschluesseln(byte[] daten, byte[] encoded_data, byte[] key, ShowProcess g){
        if(daten != null && key != null && !key.equals("") && encoded_data != null){
            g.writeLine(Main.language[41]);
            int keyz=0;
            for(int i=0; i<daten.length && i<encoded_data.length && !g.abbruch(); i++){
                g.setProcessState((byte)((double)i/(double)daten.length*100));
                if(daten[i] - key[keyz] >= -128)
                    encoded_data[i] = (byte)(daten[i] - key[keyz]);
                else
                    encoded_data[i] = (byte)(128-(key[keyz]-(128+daten[i])));
                if(i%2 == 0)
                    keyz = key.length-1-keyz;
                else{
                    keyz = key.length-keyz;
                    if(keyz == key.length)
                        keyz = 0;
                }
            }
            if(g.abbruch())
                return null;
            return encoded_data;
        }else{
            return null;
        }
    }
}
