package anm;

import java.util.LinkedList;
import java.awt.Component;
/**
 * Write a description of class AnimationGroup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationObjectGroup extends Animater
{
    public final LinkedList<AnimationObject> anms = new LinkedList<>();
    public AnimationObjectGroup(){
        
    }
    public AnimationObjectGroup(AnimationObject... anms){
        for(AnimationObject anm : anms){
            this.anms.add(anm);
        }
    }
    
    public void addAnimationObject(AnimationObject anm){
        if(anm != null)
            anms.add(anm);
    }
    public AnimationObject getAnmOfComponent(Component comp){
        for(int i=0; i<anms.size(); i++){
            if(anms.get(i).comp.equals(comp))
                return anms.get(i);
        }
        return null;
    }
    /*public void setCmd(CmdAfterAnimation cmd){
        this.cmd = cmd;
    }*/
    
    public void run(){
        cur = new Thread(){
                private boolean flag = false;
                @Override
                public void run(){
                    for(int i=0; i<anms.size(); i++){
                        anms.get(i).run();
                    }
                    
                    boolean allDeath = false;
                    while(!allDeath && !isInterrupted()){
                        allDeath = true;
                        for(int i=0; i<anms.size(); i++){
                            Thread t = anms.get(i).getThread();
                            //System.out.println("Thread in Group (running): "+i+": "+t);
                            if(t != null && t.isAlive())
                                allDeath = false;
                        }
                    }
                    if(cmd != null && !isInterrupted()) cmd.run();
                    cur = null;
                }
                @Override
                public void interrupt(){
                    flag = true;
                }
                @Override
                public boolean isInterrupted(){
                    return flag;
                }
            };
        //System.out.println("Thread in Group (run()-method): "+anmt);
        cur.start();
    }
    
    @Override
    public void interrupt(){
        for(int i=0; i<anms.size(); i++){
            Thread t = anms.get(i).getThread();
            //System.out.print("Thread in Group to interrupt: "+i+": "+t);
            if(t != null) t.interrupt();//System.out.print(", isInterrupted(): "+t.isInterrupted());}
            //System.out.println();
        }
        if(cur != null) cur.interrupt();
    }
    public void interruptAnimationOf(Component comp){
        for(int i=0; i<anms.size(); i++){
            if(anms.get(i).comp.equals(comp)){
                Thread t = anms.get(i).getThread();
                if(t != null) t.interrupt();
                return;
            }
        }
    }
}
