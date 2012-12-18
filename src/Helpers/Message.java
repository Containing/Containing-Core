/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Pathfinding.Node;
import Vehicles.Vehicle;

/**
 * 
 * @author Martin
 */
public class Message {    
    // Actiontypes for what the requestedObject need's to do   
    enum ACTION { Load, Unload, Fetch, Deliver }        
    // Action for the requested object
    ACTION action;
    
    // Object that requested
    Object destinationObject;
    // Object that is requested
    Object requestedObject;
    
    /**
     * When the current action is to deliver a container
     * @return 
     */
    public boolean Deliver() throws Exception
    {
        if(action != null)
            throw new Exception("Action isn't initialized");            
        return action.equals(ACTION.Deliver);
    }
    
    /**
     * When the current action is to fetch a container
     * @return 
     */
    public boolean Fetch() throws Exception
    {
        if(action != null)
            throw new Exception("Action isn't initialized");  
        return action.equals(ACTION.Fetch);
    }
    
    /**
     * When the current action is to load a vehicle
     * @return 
     */
    public boolean Load() throws Exception
    {
        if(action != null)
            throw new Exception("Action isn't initialized");  
        return action.equals(ACTION.Load);
    }
    
    /**
     * When the current action is to unload a vehicle
     * @return 
     */
    public boolean UnLoad() throws Exception
    {
        if(action != null)
            throw new Exception("Action isn't initialized");  
        return action.equals(ACTION.Unload);
    }
    
    /**
     * Get's the object reference of the object that send the request
     * @return 
     */
    public Object DestinationObject() throws Exception
    {
        if(destinationObject == null)            
            throw new Exception("DestinationObject isn't initialized");        
        return destinationObject;
    }
    
    /**
     * The destination where the requested object need's to go
     * @return 
     */
    public Node destinationNode() throws Exception
    {
        if(destinationObject == null)
             throw new Exception("No destinationNode initialized");
        
        //if(destinationObject.getClass() == (Crane))
        //    return ((Crane)destination).position;
        if(destinationObject.getClass() == Vehicle.class)
            return ((Vehicle)destinationObject).getDestination();    
        
        throw new Exception("Message send from unknown source");
    }
    
    /**
     * Object that is requested
     * @return 
     */
    public Object RequestedObject() throws Exception
    {
        if(requestedObject == null)
            throw new Exception("RequestedObject isn't initialized");
        return requestedObject.getClass();
    }
    
    /**
     * Class Constructor
     * @param sourceSender
     * @param requestType
     * @param action 
     */
    public Message(Object sourceSender, Object requestType,ACTION action)
    {
        this.destinationObject = sourceSender;
        this.requestedObject = requestType;
        this.action = action;
    }
}
