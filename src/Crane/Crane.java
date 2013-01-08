package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
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
    private Container _carriedContainer;
    
    private Vector<Message> _Assignments;
    
    public Crane (int rails, int range, Parkinglot<AGV> parkingAGV, Parkinglot parkingTransport)
    {
        _rails = rails;
        _range = range;
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;
        
        _Assignments = new Vector<Message>();
    }
    
    public Storage_Area loadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer == null)
        { throw new Exception("Can't place a container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
        { throw new Exception("Can't place a container in a full vehicle."); }
                
        else if (storage.getWidth() == 1)
        {
            for (int i = 0; i > storage.getLength(); i++)
            {
                if (storage.rowEmpty(i) == true)
                {
                    storage.pushContainer(_carriedContainer, i, 0);
                    _carriedContainer = null;
                    break;
                }
                
                else 
                { throw new Exception("Can't place a container in a full vehicle."); }
            }
        }
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer != null)
        { throw new Exception("Can't grab a container when one is already being carried."); }
        
        else if (storage.Count() == 0)
        { throw new Exception("Can't grab a container from an empty vehicle."); }

        else if (storage.getWidth() == 1)
        {
            for (int i = 0; i > storage.getLength(); i++)
            {
                if (storage.rowEmpty(i) == false)
                {
                    _carriedContainer = storage.popContainer(i, 0);
                    break;
                }
                
                else 
                { throw new Exception("Can't place a container in a full vehicle."); }
            }
        }
        
            return storage;
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
     * @param mess Message to be added.
     */
    @Override
    public void SendMessage(Message mess)
    {
        _Assignments.add(mess);
    }
}
