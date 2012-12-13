/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.List;
import vehicles.*;
import Helpers.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import updateTimer.updateTimer;

/**
 *
 * @author Martin_Notebook
 */
public class Controller {
    
    updateTimer timer;
    GameTime gameTime;
    
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
                timer = new updateTimer(method);
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
            agvList.add(new AGV());
        }
        
        //Er zijn in totaal  10 zeeschipkranen, 8 binnenvaartkranen, 4 treinkranen en 20 truckkranen 
        for(int i = 0; i < 10; i++)
        {    
        }
        for(int i = 0 ; i < 8; i++)
        {            
        }        
        for(int i  =0 ; i < 4; i++)
        {            
        }        
        for (int i = 0; i < 20; i++)
        {            
        }
    }
    
    /**
     * Updates class logic
     * @param gameTime 
     */
    public void Update(GameTime gameTime )
    {
        // Updates the logic of each AGV
        for(Vehicle agv : agvList)
        {    
            // Index for the message list 
            // so it don't start's the whole time at the beginning of the list
            int index = -1;
            
            // TEMP        
            if( true)
            // When the current agv is active
            //if(agv.jobType != Vehicle.TypeJob.None)
            {
                // Updates AGV's logic
                agv.update(gameTime);
            }
            else
            {
                // When the agv has no task's walk through all the request
                // To see if there is an assingment for an agv
                while(++index < messages.size())
                {
                    // When the message requested vehicle equals the agv 
                    if(messages.get(index).RequestedType() == agv.getClass())
                    {  
                        // Send's the agv to the node of the quest giver
                        agv.setDestination(messages.get(index).DestinationNode());
                        // Removes the request
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
            // Index for the message list so it doen
            int index =-1;
            // When the current crane is active
            //TEMP
            
            if(true)
            //if(crane.jobType != Vehicle.TypeJob.None)
            {
                // Updates Crane's logic
                crane.update(gameTime);
            }
            else
            {
                while(++index < messages.size())
                {
                    // When the message request equals the crane vehicletype
                    if(messages.get(index).RequestedType() == crane.getClass())
                    {
                        // Send's the crane to the node of the quest giver
                        crane.setDestination(messages.get(index).DestinationNode());
                        
                        messages.add(new Message(
                                crane,
                                AGV.class));
                        // Removes the request
                        messages.remove(index--);
                        // Stop looping because this agv now has an assingment
                        break;
                    }
                }
            }
            
            // When a crane need an AGV
                //requestAGV.add(crane.node);
        }
        
        // Updates the logic of each vehicle
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
        dockedVehicles.add(vehicle);
        
        int craneRequests = 0;
        
        //When this vehicle is a Train
        if(vehicle.getClass() == Train.class)
        {
            craneRequests = 2;
        }
        // When this vehicle is a Boat
        else if (vehicle.getClass() == Boat.class)
        {            
        }
        // When this vehicle is a Truck
        else if (vehicle.getClass() == Truck.class)
        {
            craneRequests = 1;
        }
        
        // While the vehicle can hold more cranes 
        while(craneRequests-- != 0)
        { 
            //messages.add(new Message(vehicle,Cranes.class)); 
        
        }
    }
}
