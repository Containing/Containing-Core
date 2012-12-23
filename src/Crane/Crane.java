package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karel Gerbrands
 * @version 0.1
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane, which is used to move containers 
 * from vehicle to vehicle.
 */
public class Crane implements IMessageReceiver
{
    private int _rails;
    private int _range;
    public Parkinglot parkinglotAGV;
    public Parkinglot parkinglotTransport;
    private Storage_Area _Storage;
    
    private List<Message> assignments;
    
    public Crane (int rails, int range, Parkinglot<AGV> parkingAGV, Storage_Area storage)
    {
        _rails = rails;
        _range = range;
        parkinglotAGV = parkingAGV;
        _Storage = storage;
        
        assignments = new ArrayList();
    }
    
    public Crane (int rails, int range, Parkinglot<AGV> parkingAGV, Parkinglot parkingTransport)
    {
        _rails = rails;
        _range = range;
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;
    }
    
    public boolean loadContainer ()
    {
        return false;
    }
    
    public boolean unloadContainer ()
    {
        return false;
    }
    
    public boolean liftContainer ()
    {
        return false;
    }
    
    public void update(float gameTime)
    {
        
    }
    
    /**
     * When there are no assignments for the AGV
     * @return 
     */
    @Override
    public boolean Available()
    {
        return assignments.isEmpty();
    }
    
    /**
     * Add's an assignment for the agv
     * @param mess 
     */
    @Override
    public void SendMessage(Message mess)
    {
        assignments.add(mess);
    }
}
