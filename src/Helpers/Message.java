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
    Vehicle senderRef;
    Object requested;
    
    /**
     * Returns the position node of the sender
     * @return 
     */
    public Node DestinationNode()
    {
        return senderRef.getDestination();
    }
    
    public Vehicle SenderReference()
    {
        return senderRef;
    }
    
    public Object RequestedType()
    {
        return requested.getClass();
    }
    
    public Message(Vehicle messageSender, Object requested)
    {
        this.senderRef = messageSender;
        this.requested = requested;
    }
}
