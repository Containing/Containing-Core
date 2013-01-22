package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
import Vehicles.TransportVehicle;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Karel Gerbrands
 * @version 0.5
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane, which is used to move containers 
 * from vehicle to vehicle.
 */
public class Crane implements IMessageReceiver
{
    public Parkinglot parkinglotAGV;
    public Parkinglot parkinglotTransport;
    public enum CraneType { storage, seaship, barge, train, truck };

    protected final int _railsLocation;
    protected final float _secureContainer = 30f;
    protected final float _raise;
    protected final float _lower;
    protected final float _moveContainer;
    protected final float _moveLoaded;
    protected final float _moveEmpty;
    protected Container _carriedContainer;
    protected int _currentRow;
    protected enum Action { unloading, loading, moving, raising, lowering, securing, nothing }
    protected ArrayList<Action> _currentActions;
    protected float _taskTimeLeft;
    
    private ArrayList<Message> _Assignments;
        
    public Crane (int railsLocation, CraneType type, Parkinglot parkingAGV, Parkinglot parkingTransport) throws Exception
    {
        if (parkingAGV == null || parkingTransport == null)
            { throw new Exception("A parkinglot can't be null."); }
        
        if (railsLocation < 1)
            { throw new Exception("The range can't be smaller than zero."); }
        
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;

        _currentActions = new ArrayList<Action>();
        _Assignments = new ArrayList<Message>();
        _railsLocation = railsLocation;
        
        switch (type)
        {
            case barge: _raise = 30f; _lower = 30f; _moveContainer = 1f; _moveLoaded = 2f; _moveEmpty = 1f;
                        break;
            case seaship: _raise = 0; _lower = 0; _moveContainer = 300f; _moveLoaded = 0; _moveEmpty = 1.5f;
                        break;
            case storage: _raise = 30f; _lower = 30f; _moveContainer = 0.5f; _moveLoaded = 3f; _moveEmpty = 2f;
                        break;
            case train: _raise = 60; _lower = 30; _moveContainer = 0.5f; _moveLoaded = 3f; _moveEmpty = 2f;
                        break;
            case truck: _raise = 60; _lower = 60; _moveContainer = 0; _moveLoaded = 1f; _moveEmpty = 1f;
                        break;
            default:    _raise = 0; _lower = 0; _moveContainer = 0; _moveLoaded = 0; _moveEmpty = 0;
                        break;
        }
    }

    public int getBestRowIndex(Storage_Area storage, int columnIndex) throws Exception 
    {
        if (0 > columnIndex || columnIndex > storage.getWidth())
            { throw new Exception("Row " + columnIndex + " doesn't exist on this storage."); }        

            Date date = null;

        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(columnIndex, w) > 0)
            {
                Date now = storage.peekContainer(columnIndex, w).getDepartureDateStart();

                if (date == null)
                    { date = now; }
                    
                else if (date.before(now))
                    { date = now; }
            }
        }
        
        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(columnIndex, w) > 0)
            {
                if (date == storage.peekContainer(columnIndex, w).getDepartureDateStart())
                    { return w; }
            }
        }

        return -1;
    }
    
    /**
     * 
     * @param storage
     * @return
     * @throws Exception When no container is being carried, when the storage is full,
     * when the current row is full.
     */
    public Storage_Area loadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (storage.rowFull(_currentRow) == true)
            { throw new Exception("Can't place an container in a full row."); }

        else
        {
            for (int w = 0; w < storage.getWidth(); w++)
            {
                if (storage.Count(_currentRow, w) < storage.getHeight())
                {
                    storage.pushContainer(_carriedContainer, _currentRow, w);
                    _carriedContainer = null;
                     break;
                }             
            }
            
            if (_carriedContainer != null)
                { throw new Exception("Couldn't place container."); }
        }

        return storage;
    }
    
    public Storage_Area loadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }
                
        else if (storage.Count(row, column) == storage.getHeight())
            { throw new Exception("Can't stack containers higher than 6."); }
        
        _currentRow = row;
        storage.pushContainer(_carriedContainer, _currentRow, column);
        _carriedContainer = null;
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
            { throw new Exception("Can't grab an container from an empty storage."); }
        
        else if (storage.rowEmpty(_currentRow) == true)
            { throw new Exception("Can't grab an container from an empty row."); }

        else
        {
            int columnIndex = getBestRowIndex(storage, _currentRow);
            
            if (columnIndex == -1)
            { 
                for (int column = 0; column < storage.getWidth(); column++)
                {
                    if (storage.Count(_currentRow, column) > 0)
                    {
                        _carriedContainer = storage.popContainer(_currentRow, column);
                    }
                }
            }
            
            else if (storage.Count(_currentRow, columnIndex) > 0)
            {
                _carriedContainer = storage.popContainer(_currentRow, columnIndex);
            }
        }
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
            { throw new Exception("Can't grab an container from an empty storage."); }
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }

        else if (storage.Count(row, column) == 0)
            { throw new Exception("Can't grab an container from an empty stack."); }
        
        _currentRow = row;
        _carriedContainer = storage.popContainer(_currentRow, column);
        
        return storage;
    }
    
    public void moveRow (int row)
    {
        _currentRow = row;
    }
    
    public void update(float updateTime)
    {
        if (parkinglotAGV.isEmpty() == false || parkinglotTransport.isEmpty() == false 
            && Available() == false && updateTime > 0)
        {
            Message message = _Assignments.get(0);
            boolean agv = false;
            boolean transport = false;
            
            if (message.UnLoad() != true)
            {
                _Assignments.remove(0);
                this.update(updateTime);
            }
            else
            {
                try
                {
                    if (message.DestinationObject().equals(parkinglotAGV.getVehicles().get(0)))
                    {
                        agv = true;
                    } 
                }
                catch (Exception e) { System.out.println("The parkinglot for the AGV is empty."); }
                
                try
                {
                    if (message.DestinationObject().equals(parkinglotTransport.getVehicles().get(0)))
                    {
                        transport = true;
                    } 
                }
                catch (Exception e) { System.out.println("The parkinglot for the transport is empty."); }

                try
                {
                    Storage_Area storage;
                    
                    if (agv = true)
                    {
                        AGV a = (AGV)parkinglotAGV.getVehicles().get(0);
                        storage = a.storage;
                    }
                    
                    else if (transport = true)
                    {
                        TransportVehicle t = (TransportVehicle)parkinglotTransport.getVehicles().get(0);
                        storage = t.storage;
                    }
                    
                    
                        
                }
                catch (Exception e) { }
            }
        }
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