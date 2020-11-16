package anm;


/**
 * Write a description of class Keyframe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Keyframe
{
    public final int x, y, w, h, time;
    
    /**
     * Time in ms
     */
    public Keyframe(int xLoc, int yLoc, int width, int height, int time){
        x=xLoc;
        y=yLoc;
        w=width;
        h=height;
        this.time=time;
    }
}
