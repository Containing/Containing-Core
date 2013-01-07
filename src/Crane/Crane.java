package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Parkinglot.Parkinglot;
import Vehicles.AGV;
import java.util.Vector;

/**
 * @author Karel Gerbrands
 * @version 0.3
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane, which is used to move containers 
 * from vehicle to vehicle.
 */
public class Crane implements IMessageReceiver
{
    public Parkinglot parkinglotAGV;
    public Parkinglot parkinglotTransport;
    
    private final int _rails;
    private final int _range;
    
    private Vector<Message> _Assignments;
    
    public Crane (int rails, int range, Parkinglot<AGV> parkingAGV, Parkinglot parkingTransport)
    {
        _rails = rails;
        _range = range;
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;
        
        _Assignments = new Vector<Message>();
    }
    
    public boolean loadContainer ()
    {
        return false;
    }
    
    public boolean unloadContainer ()
    {
        return false;
    }
    
    public void update(float updateTime)
    {
        
    }
    
    /**
     * 
     * @return Returns whether there are assignments for the crane.
     */
    @Override
    public boolean Available()
    {
        return _Assignments.isEmpty();
    }
    
    /**
     * Adds an assignment for the crane.
     * @param mess 
     */
    @Override
    public void SendMessage(Message mess)
    {
        _Assignments.add(mess);
    }
}
