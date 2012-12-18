/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import Main.Container;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Karel
 */
public class Container_StackTest {
    
    private Container_Stack stack;
    private Container container;
    
    public Container_StackTest() 
    {
        container = new Container("Cont");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getHeight method, of class Container_Stack.
     */
    @Test
    public void testGetHeight() 
    {
        stack = new Container_Stack(6);
        
        stack.push(container);
        stack.push(container);
        
        int result = stack.getHeight();
        int expResult = 2;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of pop method, of class Container_Stack.
     */
    @Test
    public void testPop() 
    {
        stack = new Container_Stack(6);
        
        stack.push(container);
        
        if (stack.pop() == null)
        {
            fail("The method doesn't return a container.");
        }
        
        if (stack.pop() != null)
        {
            fail("The method doesn't return a null while the stack is empty.");
        }
    }

    /**
     * Test of peak method, of class Container_Stack.
     */
    @Test
    public void testPeak() 
    {
        stack = new Container_Stack(6);
        
        stack.push(container);
        
        Container cont = stack.peak();
        
        if (cont.getId() != "Cont")
        {
            fail("Doesn't return the right container");
        }
    }

    /**
     * Test of push method, of class Container_Stack.
     */
    @Test
    public void testPush() 
    {        
        stack = new Container_Stack(6);
        
        if (stack.push(null) == true)
        {
            fail("Null is accepted.");
        }
        
        if (stack.push(container) == false)
        {
            fail("Push doesn't return true when given a valid container.");
        }
        
        if(stack.getHeight() != 1)
        {
            fail("There's more than 1 container ");
        }
    }
}
