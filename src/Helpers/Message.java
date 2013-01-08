/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Main.Container;
import Pathfinding.Node;
import Vehicles.Vehicle;

/**
 * 
 * @author Martin
 */
public class Message {    
    // The actiontypes for what the requestedObject needs to do   
    public enum ACTION { Load, Unload, Fetch, Deliver }        
    // The action for the requested object
    ACTION action;
    
    // The object that's requested
    Object destinationObject;
    // The object that's requested
    Object requestedObject;
    // The container that needs to be fetched or deliverd
    Container container;
    
    /**
     * Constructs a new message
     * @param sourceSender The object that requests an object
     * @param requestType The object that's requested
     * @param action The action for the requested object
     */
    public Message(Object sourceSender, Object requestType,ACTION action)
    {
        this.destinationObject = sourceSender;
        this.requestedObject = requestType;
        this.action = action;
    }
    
    /**
     * Constructs a new message
     * @param sourceSender The object that requests an object
     * @param requestType The object that's requested
     * @param action The action for the requested object
     * @param container A container
     */
    public Message(Object sourceSender, Object requestType,ACTION action, Container container)
    {
        this.destinationObject = sourceSender;
        this.requestedObject = requestType;
        this.action = action;
        this.container = container;
    }
    
    /**
     * The current action of the message
     * @return The current action
     */
    public ACTION GetAction(){
        return action;
    }
    
    /**
     * When the current action is to deliver a container
     * @return True if Action is deliver, False otherwise
     */
    public boolean Deliver() throws Exception
    {
        if(action != null){
            throw new Exception("Action isn't initialized");      
        }
        return action.equals(ACTION.Deliver);
    }
    
    /**
     * When the current action is to fetch a container
     * @return True if Action is fetch, False otherwise
     */
    public boolean Fetch() throws Exception
    {
        if(action != null)
            throw new Exception("Action isn't initialized");  
        return action.equals(ACTION.Fetch);
    }
    
    /**
     * When the current action is to load a vehicle
     * @return True if Action is load, False otherwise
     */
    public boolean Load() throws Exception
    {
        if(action != null){
            throw new Exception("Action isn't initialized");  
        }
        return action.equals(ACTION.Load);
    }
    
    /**
     * When the current action is to unload a vehicle
     * @return True if Action is unload, False otherwise
     */
    public boolean UnLoad() throws Exception
    {
        if(action != null){
            throw new Exception("Action isn't initialized");  
        }
        return action.equals(ACTION.Unload);
    }
    
    /**
     * The destination node where the sender is stationd
     * @return The destination node
     */
    public Node DestinationNode() throws Exception
    {
        if(destinationObject == null){
             throw new Exception("No destinationNode initialized");
        }
        if(destinationObject.getClass() == Vehicle.class){
            return ((Vehicle)destinationObject).getDestination();    
        }        
        throw new Exception("Message send from unknown source");
    }
    
    /**
     * Gets the container that needs to be fetched or deliverd
     * @return The container
     */
    public Container GetContainer() throws Exception
    {
        if(container == null){
            throw new Exception("Container isn't initialized");
        }
        return container;
    }
    
    /**
     * Gets the object that send the request
     * @return The destination object
     */
    public Object DestinationObject() throws Exception
    {
        if(destinationObject == null)      {     
            throw new Exception("DestinationObject isn't initialized");
        }
        return destinationObject;
    }
    
    /**
     * Gets the object that's requested
     * @return The requested object
     */
    public Object RequestedObject() throws Exception
    {
        if(requestedObject == null){
            throw new Exception("RequestedObject isn't initialized");
        }
        return requestedObject.getClass();
    }
}
