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
    @Test (expected = Exception.class)
    public void testPeakContainer() throws Exception 
    {
        if (storage.peekContainer(0,0).getId().equals("Cont") == false)
            { fail("Couldn't return the correct container."); }
        
        storage.peekContainer(1,0);
    }

    /**
     * Test of popContainer method, of class Storage_Area.
     */
    @Test (expected = Exception.class)
    public void testPopContainer() throws Exception 
    {
        Container temp = storage.popContainer(0, 0);
        
        if (temp == null)
            { fail("The method returns a null object."); }
        
        temp = storage.popContainer(0,0);
    }

    /**
     * Test of pushContainer method, of class Storage_Area.
     */
    @Test (expected = Exception.class)
    public void testPushContainer() throws Exception 
    {
        if (storage.Count(0,0) != 1)
            { fail("The stack doesn't contain the correct number of containers."); }
        
        storage.pushContainer(container, 0, 0);
        
        if (storage.Count(0,0) != 2)
            { fail("The stack doesn't contain the correct number of containers."); }
        
        storage.pushContainer(container, 0, 0);
    }

    /**
     * Test of getLength method, of class Storage_Area.
     */
    @Test
    public void testGetLength() 
    {
        if (storage.getLength() != 2)
            { fail("The method doesn't return the correct length."); }
    }

    /**
     * Test of getWidth method, of class Storage_Area.
     */
    @Test
    public void testGetWidth() 
    {
        if (storage.getWidth() != 3)
            { fail("The method doesn't return the correct length."); }
    }

    /**
     * Test of getHeight method, of class Storage_Area.
     */
    @Test
    public void testGetHeight() 
    {
        if (storage.getHeight() != 2)
            { fail("The method doesn't return the correct length."); }
    }

    /**
     * Test of Count method, of class Storage_Area.
     */
    @Test
    public void testCount_0args() 
    {
        if (storage.Count() != 1)
            { fail("The method doesn't return the correct amount of containers."); }
    }

    /**
     * Test of Count method, of class Storage_Area.
     */
    @Test
    public void testCount_int_int() throws Exception
    {
        if (storage.Count(0,0) != 1)
            { fail("The method doesn't return the correct amount of containers."); }
        
        if (storage.Count(1,0) != 0)
            { fail("The method doesn't return the correct amount of containers."); }
    }

    /**
     * Test of isFilled method, of class Storage_Area.
     */
    @Test
    public void testIsFilled() throws Exception
    {
        if (storage.isFilled() == true)
            { fail("Returns true when the storage isn't full yet."); }
        
        storage.pushContainer(container, 0, 0);
        
        storage.pushContainer(container, 0, 1);
        storage.pushContainer(container, 0, 1);
        
        storage.pushContainer(container, 0, 2);
        storage.pushContainer(container, 0, 2);
        
        storage.pushContainer(container, 1, 0);
        storage.pushContainer(container, 1, 0);
        
        storage.pushContainer(container, 1, 1);
        storage.pushContainer(container, 1, 1);
        
        storage.pushContainer(container, 1, 2);
        storage.pushContainer(container, 1, 2);
        
        if (storage.isFilled() == false)
            { fail("Returns false when the storage is full."); }
    }
}
