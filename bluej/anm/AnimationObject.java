package anm;

import java.util.LinkedList;
import java.awt.Component;
/**
 * Write a description of class Animation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationObject extends Animater
{
    protected final LinkedList<Keyframe> keyframes = new LinkedList<>();
    public final Component comp;

    public AnimationObject(Component comp){
        this.comp = comp;
    }

    public void addKeyframe(Keyframe keyframe){
        sortIn(keyframe);
    }

    public void addKeyframe(int xLoc, int yLoc, int width, int height, int time){
        sortIn(new Keyframe(xLoc,yLoc,width,height,time));
    }

    public void removeKeyframe(Keyframe keyframe){
        keyframes.remove(keyframe);
    }
    public void clear(){
        keyframes.clear();
    }

    private void sortIn(Keyframe k){
        boolean found = false;
        for(int i=0; i<keyframes.size(); i++){
            if(k.time < keyframes.get(i).time){
                keyframes.add(i,k);
                found = true;
            }else if(k.time == keyframes.get(i).time){
                keyframes.set(i,k);
                found = true;
            }
        }
        if(!found) keyframes.add(k);
    }

    public void run(){
        cur = new Thread(){
                private boolean flag = false;
                @Override
                public void run(){
                    //System.out.println(this+" started");
                    if(keyframes.size() > 0){
                        Keyframe last = keyframes.get(0), cur;
                        boolean firstTemp = false, interrupted = false;
                        if(last.time > 0){
                            keyframes.add(0, last=new Keyframe(comp.getX(),comp.getY(),comp.getSize().width,comp.getSize().height,0));
                            firstTemp = true;
                        }
                        if(keyframes.size() <= 1)
                            return;
                        for(int i=1; i<keyframes.size() && !this.isInterrupted(); i++){
                            cur = keyframes.get(i);
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
                            else break;
                            last = cur;
                        }
                        if(firstTemp) keyframes.remove(0);
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
        //anm = null;
    }

    /*private static int getMax(int[] arr){
        int max = arr[1];
        if(max < 0) max*=-1;
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max)
                max = arr[i];
            else if((arr[i]*-1) > max)
                max = arr[i]*-1;
        }
        return max;
    }*/
}
