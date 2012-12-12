/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package updateTimer;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author EightOneGulf
 */
public class updateTimerTest {
    
    public updateTimerTest() {
    }

    static updateTimer instance;
    
    @BeforeClass
    public static void setUpClass() throws Exception {

        instance = new updateTimer(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class updateTimer.
     */
    @Test
    public void testStart() throws InterruptedException {
        System.out.println("start");
        instance.start();

        // TODO review the generated test code and remove the default call to fail.

        Thread.sleep(1000);
        System.out.println(instance.getActualFPS());
        instance.setTargetFPS(40);
        Thread.sleep(1000);
        System.out.println(instance.getActualFPS());
        instance.setTargetFPS(50);
        Thread.sleep(1000);
        System.out.println(instance.getActualFPS());
        instance.setTargetFPS(10);
        Thread.sleep(1000);
        System.out.println(instance.getActualFPS());
    }
    
    public void updateTest(float sec){
        System.out.println("Update: " + sec);
    }

    /**
     * Test of stop method, of class updateTimer.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        
        
        
        instance.stop();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(updateTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if(instance.isAlive())fail("The thread is still alive.");
        
       
        
    }
}
