/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import java.text.ParseException;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gebruiker
 */
public class TransportVehicleTest {
    
    public TransportVehicleTest() {
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
     * Test of update method, of class TransportVehicle.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        float gameTime = 1f;
        Pathfinding.Pathfinder.generateArea();
        TransportVehicle instance = new TransportVehicle(new Date(), new Date(), "comp1", Vehicle.VehicleType.truck, new Vector3f(1,1,1), Pathfinding.Pathfinder.Nodes[111]);
        try{
            instance.update(gameTime);
        }
        catch(Exception ex){
            fail("Updating fails");
        };
    }

    /**
     * Test of MatchesContainer method, of class TransportVehicle.
     */
    @Test
    public void testMatchesContainer() throws ParseException, Exception {
        System.out.println("MatchesContainer");
        
        Date a = Container.df.parse("01-01-01 10:00");
        Date b = Container.df.parse("01-01-01 10:30");
        Date c = Container.df.parse("01-01-01 11:00");
        Date d = Container.df.parse("01-01-01 11:30");
        
        String company = "Some1";
        Container container1 = new Container("XD");
        Container container2 = new Container("XD");
        Container container3 = new Container("XD");
        Container container4 = new Container("XD");
        
        try{ 
            container1.setArrival(a, b, Container.TransportType.trein, company, new Helpers.Vector3f()); 
            container2.setArrival(b, c, Container.TransportType.trein, company, new Helpers.Vector3f());             
            container3.setArrival(c, d, Container.TransportType.trein, company, new Helpers.Vector3f()); 
            container4.setArrival(a, d, Container.TransportType.trein, company, new Helpers.Vector3f());          
        }
        catch(Exception ex) {}
        
        TransportVehicle instance = new TransportVehicle(b, c, company, Vehicle.VehicleType.train, new Vector3f(10, 1, 1), new Node());
        
        boolean result1 = instance.MatchesContainer(container1);
        boolean result2 = instance.MatchesContainer(container2);
        boolean result3 = instance.MatchesContainer(container3);
        boolean result4 = instance.MatchesContainer(container4);
        
        assertEquals(false, result1);
        assertEquals(true, result2);
        assertEquals(false, result3);
        assertEquals(false, result4);
    }
}
