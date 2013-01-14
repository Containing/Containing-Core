/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import Helpers.Vector3f;
import Main.Container;
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
public class Storage_AreaTest 
{
    private Container container;
    private Storage_Area storage;
    
    public Storage_AreaTest() throws Exception
    {
        container = new Container("Cont");
        try { storage = new Storage_Area(2,3,2, new Vector3f(1,1,1)); }
        catch(Exception E)
            { throw new Exception("Couldn't construct the storage area."); }
        
        storage.pushContainer(container, 0, 0);
    }

    /**
     * Test of rowEmpty method, of class Storage_Area.
     */
    @Test
    public void testRowEmpty() throws Exception 
    {
        if (storage.rowEmpty(0) ==  true)
            { fail("Returns true when the row contains a container."); }
        
        if (storage.rowEmpty(1) ==  false)
            { fail("Returns false when the row is empty."); }
    }

    /**
     * Test of rowFull method, of class Storage_Area.
     */
    @Test
    public void testRowFull() throws Exception 
    {
        if (storage.rowFull(0) ==  true)
            { fail("Returns true when the row isn't full."); }
        
        if (storage.rowFull(1) ==  true)
            { fail("Returns true when the row contains no containers."); }
        
        storage.pushContainer(container, 0, 0);
        storage.pushContainer(container, 0, 1);
        storage.pushContainer(container, 0, 1);
        storage.pushContainer(container, 0, 2);
        storage.pushContainer(container, 0, 2);
        
        if (storage.rowFull(1) ==  false)
            { fail("Returns false when the row is full."); }
    }

    /**
     * Test of peekContainer method, of class Storage_Area.
     */
    @Test(expected = Exception.class)
    public void testPeakContainer() throws Exception 
    {
        if (storage.peekContainer(0,0).getId().equals("Cont") == false)
            { fail("Couldn't return the correct container."); }
        
        storage.peekContainer(1,0);
    }

    /**
     * Test of popContainer method, of class Storage_Area.
     */
    @Test
    public void testPopContainer() throws Exception {
        System.out.println("popContainer");
        int x = 0;
        int z = 0;
        Storage_Area instance = null;
        Container expResult = null;
        Container result = instance.popContainer(x, z);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pushContainer method, of class Storage_Area.
     */
    @Test
    public void testPushContainer() throws Exception {
        System.out.println("pushContainer");
        Container container = null;
        int x = 0;
        int z = 0;
        Storage_Area instance = null;
        instance.pushContainer(container, x, z);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class Storage_Area.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        Storage_Area instance = null;
        int expResult = 0;
        int result = instance.getLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class Storage_Area.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Storage_Area instance = null;
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class Storage_Area.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Storage_Area instance = null;
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Count method, of class Storage_Area.
     */
    @Test
    public void testCount_0args() {
        System.out.println("Count");
        Storage_Area instance = null;
        int expResult = 0;
        int result = instance.Count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Count method, of class Storage_Area.
     */
    @Test
    public void testCount_int_int() 
    {

    }

    /**
     * Test of isFilled method, of class Storage_Area.
     */
    @Test
    public void testIsFilled() {
        System.out.println("isFilled");
        Storage_Area instance = null;
        boolean expResult = false;
        boolean result = instance.isFilled();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Storage_Area.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Storage_Area instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
