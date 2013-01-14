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
public class StorageCraneTest 
{
        private Container container;
        private Storage_Area storageField;
        private Storage_Area storage;
        private StorageCrane crane;
        
    public StorageCraneTest() 
    {
        Date one = new Date(1900, 20, 1);
        Date two = new Date(1900, 20, 3);
        try
        {
            Node node = new Node();
            Parkinglot park = new Parkinglot(1, node);
            storage = new Storage_Area(1,1,1, new Vector3f(1,1,1));
            storageField = new Storage_Area(2,2,2, new Vector3f(1,1,1));
            crane = new StorageCrane(1,1,park,park, storageField, new Vector3f(1,1,1));
            container = new Container("Cont");
            container.setDeparture(one, two, Container.TransportType.trein, "uy");
            
            storage.pushContainer(container, 0, 0);
            crane.unloadContainer(storage);
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
     * Test of getContainer method, of class StorageCrane.
     */
    @Test
    public void testGetContainer() throws Exception 
    {
        crane.storeContainer();
        
        try { crane.getContainer("cont"); }
        catch (Exception e) 
        { 
            if (e.getMessage().equals("Specified ID doesn't exist within this storage.") != true)
                { fail("The storage crane doesn't detect a false id."); }
        }
        
        crane.getContainer("Cont");
    }

    /**
     * Test of storeContainer method, of class StorageCrane.
     */
    @Test
    public void testStoreContainer() throws Exception 
    {
        crane.storeContainer();
        
        crane.getContainer("Cont");
    }

    /**
     * Test of update method, of class StorageCrane.
     */
    @Test
    public void testUpdate() 
    {
        //fail("The test case is a prototype.");
    }
}
