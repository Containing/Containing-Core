/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Crane.Crane;
import Helpers.Message.ACTION;
import Main.Container;
import Pathfinding.Node;
import Vehicles.AGV;
import Vehicles.TransportVehicle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin_Notebook
 */
public class MessageTest {
    
    public MessageTest() {
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
     * Test of GetAction method, of class Message.
     */
    @Test
    public void testGetAction() {
        System.out.println("GetAction");
        try{
            Message instance = new Message(Crane.class,AGV.class,null, null);
            fail("Action can't be null");  
        }
        catch(Exception ex){            
        }
    }

    /**
     * Test of Deliver method, of class Message.
     */
    @Test
    public void testDeliver() throws Exception {
        System.out.println("Deliver");
        Message instance = new Message(Crane.class,AGV.class,ACTION.Fetch, null);
        
        if(instance.Deliver()){
            fail("It ain't a delivery message");
        }
        instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        
        if(!instance.Deliver()){
            fail("It is a delivery message");
        }
    }

    /**
     * Test of Fetch method, of class Message.
     */
    @Test
    public void testFetch() throws Exception {
        System.out.println("Fetch");
        Message instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        
        if(instance.Fetch()){
            fail("It ain't a fetch message");
        }
        instance = new Message(Crane.class,AGV.class,ACTION.Fetch, null);
        
        if(!instance.Fetch()){
            fail("It is a fetch message");
        }
    }

    /**
     * Test of Load method, of class Message.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("Load");
        Message instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        
        if(instance.Load()){
            fail("It ain't a load message");
        }
        instance = new Message(Crane.class,AGV.class,ACTION.Load, null);
        
        if(!instance.Load()){
            fail("It is a load message");
        }
    }

    /**
     * Test of UnLoad method, of class Message.
     */
    @Test
    public void testUnLoad() throws Exception {
        System.out.println("UnLoad");
        Message instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        
        if(instance.UnLoad()){
            fail("It ain't a UnLoad message");
        }
        instance = new Message(Crane.class,AGV.class,ACTION.Unload, null);
        
        if(!instance.UnLoad()){
            fail("It is a UnLoad message");
        }
    }

    /**
     * Test of DestinationNode method, of class Message.
     */
    @Test
    public void testDestinationNode() throws Exception {
        System.out.println("DestinationNode");
        try{
            Message instance = new Message(TransportVehicle.class,Crane.class,ACTION.Deliver, null);
            Node node = instance.DestinationNode();
            fail("Can load node it's an empty class");  
        }
        catch(Exception ex){            
        }   
    }

    /**
     * Test of GetContainer method, of class Message.
     */
    @Test
    public void testGetContainer() throws Exception {
        System.out.println("GetContainer");
    }

    /**
     * Test of DestinationObject method, of class Message.
     */
    @Test
    public void testDestinationObject() throws Exception {
        System.out.println("DestinationObject");
        try{
            Message instance = new Message(AGV.class,Crane.class,ACTION.Deliver, null);
            fail("sender can't be a AGV");  
        }
        catch(Exception ex){            
        }
        try{
            Message instance = new Message(null,Crane.class,ACTION.Deliver, null);
            fail("sender can't be a null");  
        }
        catch(Exception ex){            
        }
        
        try{
            Message instance = new Message(Crane.class,Crane.class,ACTION.Deliver, null);
            fail("sender can't be equal to the requested");  
        }
        catch(Exception ex){            
        }
        
        try{
            Message instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        }
        catch(Exception ex){   
            fail("it ain't wrong input");           
        }
    }

    /**
     * Test of RequestedObject method, of class Message.
     */
    @Test
    public void testRequestedObject() throws Exception {
        System.out.println("RequestedObject");
        try{
            Message instance = new Message(Crane.class,TransportVehicle.class,ACTION.Deliver, null);
            fail("Request can't be a transportVehicle");  
        }
        catch(Exception ex){            
        }
        try{
            Message instance = new Message(null,Crane.class,ACTION.Deliver, null);
            fail("sender can't be a null");  
        }
        catch(Exception ex){            
        }
        try{
            Message instance = new Message(Crane.class,Crane.class,ACTION.Deliver, null);
            fail("Request can't be equal to the sender");  
        }
        catch(Exception ex){            
        }
        
        try{
            Message instance = new Message(Crane.class,AGV.class,ACTION.Deliver, null);
        }
        catch(Exception ex){   
            fail("it ain't wrong input");           
        }
    }
}
