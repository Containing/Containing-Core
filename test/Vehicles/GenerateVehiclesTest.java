/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vehicles;

import Main.Database;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateVehiclesTest {
        
    public GenerateVehiclesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Database.restoreDump();
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
     * Test of main method, of class GenerateVehicles.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        GenerateArrivalVehicles.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetSeaBoats method, of class GenerateVehicles.
     */
    @Test
    public void testGetSeaBoats() throws Exception {
        System.out.println("GetSeaBoats");
        List<Boat> result = GenerateArrivalVehicles.GetSeaBoats();
        int count = 13;
        
        // check if their are enough boats
        assertEquals(count, result.size());
        
        // check if their is only 1 boat at the same time in the dock.
        for (int i = 0; i < result.size()-1; i++) {
            if(result.get(i).GetDepartureDate().after(result.get(i+1).GetArrivalDate())){
                fail("Their can only be one boet in the dock");
            }
        }
        
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).storage.Count() == 0){
                fail("Boats can't be empty");
            }
        }
    }

    /**
     * Test of GetInlandBoats method, of class GenerateVehicles.
     */
    @Test
    public void testGetInlandBoats() throws Exception {
        System.out.println("GetInlandBoats");
        List<Boat> result = GenerateArrivalVehicles.GetInlandBoats();
        int count = 60;
        
        // check if their are enough boats
        assertEquals(count, result.size());
        
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).storage.Count() == 0){
                fail("Boats can't be empty");
            }
        }
    }

    /**
     * Test of GetTrains method, of class GenerateVehicles.
     */
    @Test
    public void testGetTrains() throws Exception {
        System.out.println("GetTrains");
        List expResult = null;
        List result = GenerateArrivalVehicles.GetTrains();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetTrucks method, of class GenerateVehicles.
     */
    @Test
    public void testGetTrucks() throws Exception {
        System.out.println("GetTrucks");
        List expResult = null;
        List result = GenerateArrivalVehicles.GetTrucks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
