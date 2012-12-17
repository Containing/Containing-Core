/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Helpers.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import updateTimer.updateTimer;
import vehicles.*;

/**
 *
 * @author Martin_Notebook
 */
public class Controller {
    
    updateTimer timer;
    
    List<Vehicle> dockedVehicles;
    // List with all AGV's
    List<Vehicle> agvList;
    // List with all Cranes
    List<Vehicle> craneList;    
    // List with all current messages
    List<Message> messages;
    
    // Simulation Time
    Date simulationTime;
    // Date when the controller needs to send containers
    Date deliveryTime;
    
    /**
     * Class Constructor,
     * Call's the initialize method
     */
    public Controller()
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
        timer.start();
        timer.run();
    }
    
    /**
     * Initializes the class variables
     **/
    private void Initialize()
    {
        // Initiallizes the List's variables
        messages = new ArrayList();
        dockedVehicles = new ArrayList();        
        agvList = new ArrayList();
        craneList = new ArrayList();        
        
        // Add's 100 AGV's to the class all waiting
        for(int i = 0; i < 100; i++)
        {
            agvList.add(new AGV(null));
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
    public void Update(float gameTime )
    {
        System.out.println(gameTime);
        
        // Updates the logic of each AGV
        for(Vehicle agv : agvList)
            agv.update(gameTime);
        // Updates the logic of each crane
        for(Vehicle crane : craneList)
            crane.update(gameTime);
        // Updates the logic of each docked vehicle
        for(Vehicle vehicle : dockedVehicles)
            vehicle.update(gameTime);        
        
        // When the simulation time is equal or greater than the deliveryTime
        if(simulationTime.getTime() >= deliveryTime.getTime())
        {
            /**
             * TODO:
             * Get all the contianers that need to be deliverd
             * Set them into delivery messages
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
                    if(agv.getDestination() == null)
                    {
                        //Set's the destination of the AGV
                        agv.setDestination(mess.destinationNode());
                        //Copy's the message to the message que
                        ((AGV)agv).messageQue.add(mess);
                        //Message was handeld so remove it
                        messages.remove(mess);
                    }
                }
            }
            //else if(mess.RequestedObject() == Crane.class)
                // Send a Crane
        }
        /**
         * TODO:
         *  Send messages naar AGV's wat en waar ze wat moeten doen
         *  Send messages to Crane's if the have to load or unload 
         */
        
                // When this agv has no task's.
                // Walk through all the messages for a job
//                while(++index < messages.size())
//                {
//                    // When the message requested an AGV 
//                    if(messages.get(index).RequestedObject() == agv.getClass())
//                    {  
//                        // Send's the agv to the node of the quest giver
//                        agv.setDestination(messages.get(index).destinationNode());
//                        // Removes the message
//                        messages.remove(index--); 
//                        // Stop looping because this agv now has an assingment
//                        break;
//                    }
//                }
        
        
        
//                // When this crane has no task's.
//                // Walk through all the messages for a job
//                while(++index < messages.size())
//                {
//                    // When the message request a crane of the same type of this crane
//                    if(messages.get(index).RequestedObject() == crane.getClass())
//                    {
//                        // Send's the crane to the node of the quest giver
//                        crane.setDestination(messages.get(index).destinationNode());
//                        // Request's an AGV for contianer pickup
//                        messages.add(new Message(
//                                crane,
//                                AGV.class,
//                                Message.ACTION.Unload));
//                        // Removes this message
//                        messages.remove(index--);
//                        // Stop looping because this crane now has an assingment
//                        break;
//                    }
//                }
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
