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
    /**
     * Action Types for the the requestedObject
     */
    public enum ACTION { Load, Unload, Fetch, Deliver }        
    // Action of the message
    ACTION action;
    
    // Object that requested
    Object destinationObject;
    // Object that is requested
    Object requestedObject;
    
    /**
     * When the current action is to deliver a container
     * @return 
     */
    public boolean Deliver()
    {
        if(action != null)
            return action.equals(ACTION.Deliver);
        return false;
    }
    
    /**
     * When the current action is to fetch a container
     * @return 
     */
    public boolean Fetch()
    {
        if(action != null)
            return action.equals(ACTION.Fetch);
        return false;
    }
    
    /**
     * When the current action is to load a vehicle
     * @return 
     */
    public boolean Load()
    {
        if(action != null)
            return action.equals(ACTION.Load);
        return false;
    }
    
    /**
     * When the current action is to unload a vehicle
     * @return 
     */
    public boolean UnLoad()
    {
        if(action != null)
            return action.equals(ACTION.Unload);
        return false;
    }
    
    /**
     * Get's the object reference of the object that send the request
     * @return 
     */
    public Object DestinationObject()
    {
        return destinationObject;
    }
    
    /**
     * The destination where the requested object need's to go
     * @return 
     */
    public Node destinationNode()
    {
        //if(destinationObject.getClass() == (Crane))
        //    return ((Crane)destination).position;
        if(destinationObject.getClass() == Vehicle.class)
            return ((Vehicle)destinationObject).getDestination();
        
        return null;
    }
    
    /**
     * Object that is requested
     * @return 
     */
    public Object RequestedObject()
    {
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
