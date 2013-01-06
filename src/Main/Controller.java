/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Helpers.*;
import Pathfinding.Node;
import Vehicles.*;
import Storage.Storage_Area;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import updateTimer.updateTimer;
import Crane.Crane;


/**
 *
 * @author Martin_Notebook
 */
public class Controller {
    // Updates the update method
    updateTimer timer;
    
    List<Vehicle> dockedVehicles;
    // List with all AGV's
    List<Vehicle> agvList;
    // List with all Cranes
    List<Crane> craneList;
    // List with all current messages
    List<Message> messages;
    
    // The storage area where all the containers are stored
    Storage_Area storageArea;
    
    // Simulation Time
    Date simulationTime;
    // Date when the controller needs to send containers
    Date deliveryTime;
    
    /**
     * Class Constructor,
     * Call's the initialize method
     */
    public Controller() throws Exception
    {
        Initialize();
        
        // Walk's through all the method's in this class
        for(Method method : this.getClass().getMethods())
        {
            // When the method == update initialize the timer
            if("Update".equals(method.getName()))
            {
                timer = new updateTimer(method, this);
                break;
            }
        }
        // Start's and run's the timer for updating the application
        timer.start();
        timer.run();
    }
    
    /**
     * Initializes the class variables
     **/
    private void Initialize() throws Exception
    {
        // Initiallizes the List's variables
        messages = new ArrayList();
        dockedVehicles = new ArrayList();        
        agvList = new ArrayList();
        craneList = new ArrayList();        
        
        // Add's 100 AGV's to the class all waiting
        for(int i = 0; i < 100; i++){
            agvList.add(new AGV(new Node(1,0)));
        }
        
        //Er zijn in totaal  10 zeeschipkranen, 8 binnenvaartkranen, 4 treinkranen en 20 truckkranen 
        for(int i = 0; i < 10; i++)
        {    
            // Add's 10 seaShipCranes
            //craneList.add(Crane);
        }
        for(int i = 0 ; i < 8; i++)
        {     
            // Add's 8 BargeCranes
            //craneList.add(Crane);
        }        
        for(int i  =0 ; i < 4; i++)
        {         
            // Add's 10 trianCranes
            //craneList.add(Crane);
        }        
        for (int i = 0; i < 20; i++)
        {          
            // Add's 20 truckCranes
            //craneList.add(Crane);
        }
        
        // To do Generate all vehicles with their containers
        
    }
    
    /**
     * Updates class logic
     * @param gameTime 
     */
    public void Update(float gameTime ) throws Exception
    {
        System.out.println(gameTime);
        
        // Updates the logic of each AGV
        for(Vehicle agv : agvList){
            agv.update(gameTime);
        }
        // Updates the logic of each crane
        for(Crane crane : craneList){
            crane.update(gameTime);
        }
        // Updates the logic of each docked vehicle
        for(Vehicle vehicle : dockedVehicles){
            vehicle.update(gameTime);
        }        
        
        // When the simulation time is equal or greater than the deliveryTime
        if(simulationTime.getTime() >= deliveryTime.getTime())
        {
            // Walks around the whole storage area and checks every contianer
            for(int column = 0; column< storageArea.getWidth(); column++){
                for(int row =0;row< storageArea.getLength(); row++){
                    // Checks every container on the stack
                    for(int stack = storageArea.stackHeight(row, column); stack > 0; stack--){
                        // When the container needs to be transported
                        if(simulationTime.getTime() >= 
                           storageArea.CheckContainer(row, column, stack).getDepartureDateStart().getTime()){
                            // Adds a fetch message for an AGV
                            messages.add(new Message(
                                    storageArea.getClass(),
                                    AGV.class,
                                    Message.ACTION.Fetch));
                        }
                    }
                }
            }
            /**
             * TODO:
             * Get all the contianers that need to be deliverd
             * Send a message for each container that need's to be deliverd
             */
        }
        
        // Check's every message
        for(Message mess : messages)
        {
            // When this message is an agv request 
            if(mess.RequestedObject() == AGV.class)
            {  
                for(Vehicle agv : agvList)
                {
                    // When the agv has nothing to do
                    if(((AGV)agv).Available())
                    {
                        //Set's the destination of the AGV
                        agv.setDestination(mess.destinationNode());
                        //Copy's the message to the message que
                        ((AGV)agv).SendMessage(mess);
                        //Message was handeld so remove it
                        messages.remove(mess);
                        
                        /**
                         * 
                         * 
                         * TODO: When it's a fetch assignment add also a delivery assignment 
                         * 
                         * 
                         */
                    }
                }
            }
            else if(mess.RequestedObject() == Crane.class)
            {
                for(Crane crane : craneList)
                {
                    if(crane.Available())
                    {
                        //Set's the destination of the AGV
                        //crane.setDestination(mess.destinationNode());
                        //Copy's the message to the message que
                        crane.SendMessage(mess);
                        //Message was handeld so remove it
                        messages.remove(mess);
                    }
                    // Send a Crane
                }
            }
        }
        /**
         * TODO:
         *  Send messages naar AGV's wat en waar ze wat moeten doen
         *  Send messages to Crane's if the have to load or unload 
         */
    }
    
    /**
     * When a vehicle dock's
     * @param vehicle 
     */
    public void VehicleDocking(Vehicle vehicle)
    {      
        // Add the docked Vehicle
        dockedVehicles.add(vehicle);
        // Interger that holds the amount of cranes this vehicle can hold for loading and unloading
        int craneRequests = 0;
        
        //When this vehicle is a Train
        if(vehicle.getClass() == Train.class)
        {
            // Train's can hold 2 cranes
            craneRequests = 2;
        }
        // When this vehicle is a Boat
        else if (vehicle.getClass() == Boat.class)
        {            
            //TODO Boat crane request
            //TODO 2 different types of boat's a SeaShip, Barge
        }
        // When this vehicle is a Truck
        else if (vehicle.getClass() == Truck.class)
        {
            // Truck's can hold 1 crane
            craneRequests = 1;
        }
        
        // While the vehicle can hold more cranes 
        while(craneRequests-- != 0)
        { 
            //Request's a crane for this vehicle to load or unload
            //messages.add(new Message(vehicle,Cranes.class));         
        }
    }
}
