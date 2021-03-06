package updateTimer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EightOneGulf
 */
public class updateTimer {
    private Method updateMethod;
    private Object updateObject;
    private boolean running;

    private float targetFPS = 30;
    private float currentFPS = 0;
    private long prevTime;

    public updateTimer(Method updateMethod, Object updateObject){
        this.updateMethod = updateMethod;
        this.updateObject = updateObject;
    }
    
    public void start(){
        running = true;
        prevTime = System.nanoTime();
    }
    
    public void stop(){
        running = false;
    }

    public void setTargetFPS(float targetFPS){
        this.targetFPS = targetFPS;
    }
    public float getTargetFPS(){
        return targetFPS;
    }
    public float getActualFPS(){
        return currentFPS;
    }

    public void run() {
        long curTime, timeDiff;
        
        if(running) {
            curTime = System.nanoTime();
            try {
                updateMethod.invoke(updateObject, (curTime-prevTime)/1000000000f);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
                System.out.println("ERROR1");
                Logger.getLogger(updateTimer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                System.out.println("ERROR2");
                Logger.getLogger(updateTimer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
                System.out.println("ERROR3");
                Logger.getLogger(updateTimer.class.getName()).log(Level.SEVERE, null, ex);
            }

            prevTime = curTime;
            //Calculate difference and adjust sleep
            try {
                timeDiff = (System.nanoTime() - curTime)/1000000;
                float sleepTime = 1000f/targetFPS - timeDiff;
                if(sleepTime>0) Thread.sleep( (long)sleepTime );
            } catch (InterruptedException ex) {
                Logger.getLogger(updateTimer.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            currentFPS = (1000f/((System.nanoTime() - curTime)/1000000f));
            
        }
    }
}