package anm;

import java.util.LinkedList;
import java.awt.Component;
/**
 * Write a description of class AnimationStateObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationStateObject extends Animater
{
    private Keyframe[] states;
    private int curState;
    public final Component comp;
    
    public AnimationStateObject(Component comp, Keyframe... states){
        this.comp = comp;
        curState = -1;
        this.states = states;
    }
    public void run(){}
    
    public void anmToState(int state){
        if(states.length <= state || state < 0)
            return;
        cur = new Thread(){
                private boolean flag = false;
                @Override
                public void run(){
                    //System.out.println(this+" started");
                    if(states.length > 0){
                        Keyframe last = new Keyframe(comp.getX(),comp.getY(),comp.getSize().width,comp.getSize().height,0),
                            cur = states[state];
                        boolean interrupted = false;
                        
                        int xDif = cur.x-last.x, yDif = cur.y-last.y, wDif = cur.w-last.w, hDif = cur.h-last.h, tDif = cur.time-last.time,
                            nGes = (int)(((double)tDif)/1000*hz);
        
                        /*xCur = last.x, yCur = last.y, wCur = last.w, hCur = last.h,
                        curTime = 0;*/
                        for(int nCur=0; nCur < nGes && !this.isInterrupted(); nCur++){
                            comp.setBounds((int)(last.x + ((double)(nCur*xDif))/(hz*((double)tDif)/1000)), 
                                (int)(last.y + ((double)(nCur*yDif))/(hz*((double)tDif)/1000)), 
                                (int)(last.w + ((double)(nCur*wDif))/(hz*((double)tDif)/1000)), 
                                (int)(last.h + ((double)(nCur*hDif))/(hz*((double)tDif)/1000)));
                            try{
                                Thread.sleep((long)(((double)1000)/hz));
                            }catch(InterruptedException e){/*interrupted=true;*/break;}
                            //curTime+=(int)(((double)1000)/hz);
                        }
                        if(!this.isInterrupted()/* && !interrupted*/) comp.setBounds(cur.x, cur.y, cur.w, cur.h);
                    }
                    if(cmd != null && !isInterrupted()) cmd.run();
                    //System.out.println(this+" isInterrupted(): "+isInterrupted());
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
        cur.start();
    }
}
