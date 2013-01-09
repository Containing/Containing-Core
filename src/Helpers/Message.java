/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Crane.Crane;
import Main.Container;
import Pathfinding.Node;
import Vehicles.*;

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
     * @param requestedType The object that's requested
     * @param action The action for the requested object
     */
    public Message(Object sourceSender, Object requestedType,ACTION action,Container container) throws Exception
    {
        System.out.println(sourceSender.getClass());
        System.out.println(Vehicles.Truck.class.equals(sourceSender));
        
        if(sourceSender == null){
            throw new Exception("Source Sender can't be null");
        }
        //if(!(sourceSender.getClass() == TransportVehicle.class || sourceSender.getClass() == Crane.class)){
            
        //    throw new Exception("Source Sender must be a crane or transportVehicle");
        //}
        if(sourceSender == requestedType){
            throw new Exception("Can't request the same type of object");
        }
        
        if(requestedType == null){
            throw new Exception("Requested type can't be null");
        }
        if(requestedType != AGV.class && requestedType != Crane.class){
            throw new Exception("Requested type must be a crane or an agv");
        }
        
        if(action == null){
            throw new Exception("action can't be null");
        }
        
        if(container != null && requestedType != AGV.class){
            throw new Exception("Only agvs can have a container in there message");
        }
        
        this.destinationObject = sourceSender;
        this.requestedObject = requestedType;
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
    public boolean Deliver(){
        return action.equals(ACTION.Deliver);
    }
    
    /**
     * When the current action is to fetch a container
     * @return True if Action is fetch, False otherwise
     */
    public boolean Fetch(){
        return action.equals(ACTION.Fetch);
    }
    
    /**
     * When the current action is to load a vehicle
     * @return True if Action is load, False otherwise
     */
    public boolean Load(){
        return action.equals(ACTION.Load);
    }
    
    /**
     * When the current action is to unload a vehicle
     * @return True if Action is unload, False otherwise
     */
    public boolean UnLoad(){
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
        
        if(destinationObject.getClass() == TransportVehicle.class){            
            if(((TransportVehicle)destinationObject).equals(null)){
                throw new Exception("destination node is null");
            }
            return ((TransportVehicle)destinationObject).getDestination();    
        }        
        throw new Exception("Message send from unknown source");
    }
    
    /**
     * Gets the container that needs to be fetched or deliverd
     * @return The container
     */
    public Container GetContainer()
    {
        return container;
    }
    
    /**
     * Gets the object that send the request
     * @return The destination object
     */
    public Object DestinationObject()
    {
        return destinationObject;
    }
    
    /**
     * Gets the object that's requested
     * @return The requested object
     */
    public Object RequestedObject()
    {
        return requestedObject.getClass();
    }
}