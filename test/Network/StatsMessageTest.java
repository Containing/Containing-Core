/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christiaan
 */
public class StatsMessageTest {
    
    public StatsMessageTest() {
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
     * Test of getAreaKeys method, of class StatsMessage.
     */
    @Test
    public void testGetAreaKeys() {
        System.out.println("getAreaKeys");
        StatsMessage instance = new StatsMessage();
        instance.areas.put("key2", 2);
        instance.areas.put("key1", 1);
        String[] expResult = {"key2", "key1"};
        String[] result = instance.getAreaKeys();
        assertEquals(expResult[0], result[0]);
        assertEquals(expResult[1], result[1]);
    }

    /**
     * Test of getAreaValues method, of class StatsMessage.
     */
    @Test
    public void testGetAreaValues() {
        System.out.println("getAreaValues");
        StatsMessage instance = new StatsMessage();
        instance.areas.put("key2", 2);
        instance.areas.put("key1", 1);
        Integer[] expResult = {2, 1};
        Integer[] result = instance.getAreaValues();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getVehicleKeys method, of class StatsMessage.
     */
    @Test
    public void testGetVehicleKeys() {
        System.out.println("getVehicleKeys");
        StatsMessage instance = new StatsMessage();
        instance.vehicles.put("key2", 2);
        instance.vehicles.put("key1", 1);
        String[] expResult = {"key2", "key1"};
        String[] result = instance.getVehicleKeys();
        assertEquals(expResult[0], result[0]);
        assertEquals(expResult[1], result[1]);
    }

    /**
     * Test of getVehicleValues method, of class StatsMessage.
     */
    @Test
    public void testGetVehicleValues() {
        System.out.println("getVehicleValues");
        StatsMessage instance = new StatsMessage();
        instance.vehicles.put("key2", 2);
        instance.vehicles.put("key1", 1);
        Integer[] expResult = {2, 1};
        Integer[] result = instance.getVehicleValues();
        assertArrayEquals(expResult, result);
    }
}
