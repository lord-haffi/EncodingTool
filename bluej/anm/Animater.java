package anm;


/**
 * Write a description of interface Animater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Animater
{
    protected Thread cur = null;
    public static double hz = 60;
    public CmdAfterAnimation cmd = null;
    
    public abstract void run();
    public void interrupt(){
        if(cur != null) cur.interrupt();
    }
    public Thread getThread(){
        return cur;
    }
}
