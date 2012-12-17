/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Pathfinding.Node;
import vehicles.Vehicle;

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
     * Get's the requested action type 
     * @return 
     */
    public ACTION CurrentAction()
    {
        return action;
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
