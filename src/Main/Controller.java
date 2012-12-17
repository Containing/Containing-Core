/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Helpers.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        // Updates the logic of each AGV
        for(Vehicle agv : agvList)
        {    
            // Index for the message list 
            // so it doesn't start walking through each message
            int index = -1;
            
            // TEMP        
            if( index == -1)
            // When the current agv is active
            //if(agv.jobType != Vehicle.TypeJob.None)
            {
                // Updates AGV's logic
                agv.update(gameTime);
            }
            else
            {
                // When this agv has no task's.
                // Walk through all the messages for a job
                while(++index < messages.size())
                {
                    // When the message requested an AGV 
                    if(messages.get(index).RequestedType() == agv.getClass())
                    {  
                        // Send's the agv to the node of the quest giver
                        agv.setDestination(messages.get(index).DestinationNode());
                        // Removes the message
                        messages.remove(index--); 
                        // Stop looping because this agv now has an assingment
                        break;
                    }
                }                     
            }
        }
        
        // Updates the logic of each crane
        for(Vehicle crane : craneList)
        {
            // Index for the message list 
            // so it doesn't start walking through each message
            int index =-1;
            
            //TEMP
            if(index == -1)
            // When the current crane is active
            //if(crane.jobType != Vehicle.TypeJob.None)
            {
                // Updates Crane's logic
                crane.update(gameTime);
            }
            else
            {
                // When this crane has no task's.
                // Walk through all the messages for a job
                while(++index < messages.size())
                {
                    // When the message request a crane of the same type of this crane
                    if(messages.get(index).RequestedType() == crane.getClass())
                    {
                        // Send's the crane to the node of the quest giver
                        crane.setDestination(messages.get(index).DestinationNode());
                        // Request's an AGV for contianer pickup
                        messages.add(new Message(
                                crane,
                                AGV.class));
                        // Removes this message
                        messages.remove(index--);
                        // Stop looping because this crane now has an assingment
                        break;
                    }
                }
            }            
        }
        
        // Updates the logic of each docked vehicle
        for(Vehicle vehicle : dockedVehicles)
        {
            // Updates Vehicle logic
            vehicle.update(gameTime);
        }
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
