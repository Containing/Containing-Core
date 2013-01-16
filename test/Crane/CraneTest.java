/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crane;

import Helpers.Vector3f;
import Main.Container;
import Parkinglot.Parkinglot;
import Pathfinding.Node;
import Storage.Storage_Area;
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
public class CraneTest 
{
    private Container container;
    private Storage_Area storage;
    private Crane crane;
    
    public CraneTest() 
    {
        Date one = new Date(1900, 20, 1);
        Date two = new Date(1900, 20, 3);
        try
        {
            Node node = new Node();
            Parkinglot park = new Parkinglot(1, node);
            storage = new Storage_Area(2,1,2, new Vector3f(1,1,1));
            crane = new Crane(1,1,park,park);
            container = new Container("Cont");
            container.setDeparture(one, two, Container.TransportType.trein, "uy");
        }
        catch (Exception E) { }
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
     * Test of loadContainer method, of class Crane.
     */
    @Test
    public void testLoadContainer_Storage_Area() throws Exception 
    {
        storage.pushContainer(container, 0, 0);
        crane.setRow(0);
        crane.unloadContainer(storage);
        
        crane.loadContainer(storage);
        
        if (storage.Count() != 1)
        { fail("The crane doesn't push correctly."); }
        
        if (storage.Count(0,0) != 1)
        { fail("The crane doesn't push correctly."); }
    }

    /**
     * Test of loadContainer method, of class Crane.
     */
    @Test
    public void testLoadContainer_3args() throws Exception 
    {
        storage.pushContainer(container, 0, 0);
        crane.setRow(0);
        crane.unloadContainer(storage);  
        crane.loadContainer(storage, 1, 0);
        
        if (storage.Count() != 1)
        { fail("The crane doesn't push correctly."); }
        
        if (storage.Count(1,0) != 1)
        { fail("The crane doesn't push correctly."); }
    }

    /**
     * Test of unloadContainer method, of class Crane.
     */
    @Test
    public void testUnloadContainer_Storage_Area() throws Exception 
    {
        storage.pushContainer(container, 0, 0);
        crane.unloadContainer(storage);
        
        if (storage.Count() != 0)
        { fail("The crane doesn't pop correctly."); }
        
        crane.loadContainer(storage, 1, 0);
        crane.unloadContainer(storage);     
        
        if (storage.Count(1,0) != 0)
        { fail("The crane doesn't pop correctly."); }
    }

    /**
     * Test of unloadContainer method, of class Crane.
     */
    @Test
    public void testUnloadContainer_3args() throws Exception 
    {
        storage.pushContainer(container, 0, 0);
        crane.unloadContainer(storage, 0,0);
        
        if (storage.Count() != 0)
        { fail("The crane doesn't pop correctly."); }
        
        crane.loadContainer(storage, 1, 0);
        crane.unloadContainer(storage, 1 , 0);     
        
        if (storage.Count(1,0) != 0)
        { fail("The crane doesn't pop correctly."); }
    }

    /**
     * Test of update method, of class Crane.
     */
    @Test
    public void testUpdate() 
    {
        //fail("The test case is a prototype.");
    }

    /**
     * Test of Available method, of class Crane.
     */
    @Test
    public void testAvailable() 
    {
        //fail("The test case is a prototype.");
    }

    /**
     * Test of SendMessage method, of class Crane.
     */
    @Test
    public void testSendMessage() 
    {
        //fail("The test case is a prototype.");
    }
}
