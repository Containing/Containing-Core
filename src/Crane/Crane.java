package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Helpers.Vector3f;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
import Vehicles.TransportVehicle;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.text.Position;

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
    protected float _taskTimeLeft;
    protected ArrayList<Message> _Assignments;
    protected Vector3f _position;
    
    private enum _taskList { loadAGV, unloadAGV, loadTransport, unloadTransport, moveRowUp, moveRowBase }
    private ArrayList<_taskList> _tasks;
    private int _totalCranes;
    private CraneType _Type;
    private int _ID;
    private float _rotation;
        
    public Crane (int ID, int railsLocation, CraneType type, Parkinglot parkingAGV, Parkinglot parkingTransport) throws Exception
    {
        if (parkingAGV == null || parkingTransport == null)
            { throw new Exception("A parkinglot can't be null."); }
        
        if (railsLocation < 1)
            { throw new Exception("The range can't be smaller than zero."); }
        
        
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;

        _ID = ID;
        _Type = type;
        _position = parkingAGV.node.getPosition();
        _tasks = new ArrayList<_taskList>();
        _Assignments = new ArrayList<Message>();
        _railsLocation = railsLocation;
        
        switch (type)
        {
            case barge: _raise = 30f; _lower = 30f; _moveContainer = 1f; _moveLoaded = 2f; _moveEmpty = 1f; _totalCranes = 4; _rotation = 0;
                        break;
            case seaship: _raise = 0; _lower = 0; _moveContainer = 300f; _moveLoaded = 0; _moveEmpty = 1.5f; _totalCranes = 10; _rotation = 90;
                        break;
            case storage: _raise = 30f; _lower = 30f; _moveContainer = 0.5f; _moveLoaded = 3f; _moveEmpty = 2f; _totalCranes = 1; _rotation = 0;
                        break;
            case train: _raise = 60; _lower = 30; _moveContainer = 0.5f; _moveLoaded = 3f; _moveEmpty = 2f; _totalCranes = 2; _rotation = 180;
                        break;
            case truck: _raise = 60; _lower = 60; _moveContainer = 0; _moveLoaded = 1f; _moveEmpty = 1f; _totalCranes = 4; _rotation = 270;
                        break;
            default:    _raise = 0; _lower = 0; _moveContainer = 0; _moveLoaded = 0; _moveEmpty = 0; _totalCranes = 1; _rotation = 0;
                        break;
        }
    }

    public int getID ()
    {
        return _ID;
    }
    
    public CraneType getType ()
    {
        return _Type;
    }
    
    public float getRotation ()
    {
        return _rotation;
    }
    
    public Vector3f getPosition ()
    {
        return _position;
    }
    
    /**
     * Returns the best stack in the row determined by the date of the containers.
     * @param storage
     * @param columnIndex
     * @return
     * @throws Exception 
     */
    private int getBestColumnIndex(Storage_Area storage, int rowIndex) throws Exception 
    {
        if (0 > rowIndex || rowIndex > storage.getWidth())
            { throw new Exception("Row " + rowIndex + " doesn't exist on this storage."); }        

            Date date = null;

        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(rowIndex, w) > 0)
            {
                Date now = storage.peekContainer(rowIndex, w).getDepartureDateStart();

                if (date == null)
                    { date = now; }
                    
                else if (date.before(now))
                    { date = now; }
            }
        }
        
        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(rowIndex, w) > 0)
            {
                if (date == storage.peekContainer(rowIndex, w).getDepartureDateStart())
                    { return w; }
            }
        }

        return -1;
    }
    
    /**
     * Loads the AGV using the loadContainer method.
     * @param storage
     * @return Returns the storage area which houses the changes made.
     * @throws Exception when it's not possible to load the AGV.
     */
    private Storage_Area loadAGV (Storage_Area storage) throws Exception
    {
        return loadContainer(0,0, storage);
    }
    
    /**
     * Unloads the AGV using the unloadContainer method.
     * @param storage
     * @return Returns the storage area which houses the changes made.
     * @throws Exception when it's not possible to unload the AGV.
     */
    private Storage_Area unloadAGV (Storage_Area storage) throws Exception
    {
        return unloadContainer(0,0, storage);
    }
    
    /**
     * 
     * @param storage
     * @return Returns the storage area which houses the changes made.
     * @throws Exception When no container is being carried, when the storage is full,
     * when the current row is full.
     */
    private Storage_Area loadTransport (Storage_Area storage) throws Exception
    {
        for (int w = 0; w < storage.getWidth(); w++)
        {
            if (storage.Count(_currentRow, w) < storage.getHeight())
            {
                return loadContainer(_currentRow, w, storage);
            }             
        }
        
        throw new Exception("Couldn't load the container.");
    }
    
    private Storage_Area unloadTransport (Storage_Area storage) throws Exception
    {
        int columnIndex = getBestColumnIndex(storage, _currentRow);

        if (columnIndex == -1)
        { 
            for (int column = 0; column < storage.getWidth(); column++)
            {
                if (storage.Count(_currentRow, column) > 0)
                {
                    return unloadContainer(_currentRow, column, storage);
                }
            }
        }

        else if (storage.Count(_currentRow, columnIndex) > 0)
        {
            return unloadContainer(_currentRow, columnIndex, storage);
        }
        
        throw new Exception("Couldn't unload the transport");
    }
    
    private Storage_Area loadContainer(int rowIndex, int columnIndex, Storage_Area storage) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (storage.rowFull(_currentRow) == true)
            { throw new Exception("Can't place an container in a full row."); }
        
        storage.pushContainer(_carriedContainer, rowIndex, columnIndex);
        _carriedContainer = null;
        
        if (_carriedContainer != null)
            { throw new Exception("Couldn't place container."); }
        
        return storage;
    }
    
    private Storage_Area unloadContainer(int rowIndex, int columnIndex, Storage_Area storage) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
            { throw new Exception("Can't grab an container from an empty storage."); }
        
        else if (storage.rowEmpty(_currentRow) == true)
            { throw new Exception("Can't grab an container from an empty row."); }

        _carriedContainer = storage.popContainer(rowIndex, columnIndex);
        _carriedContainer.setDatabasePosition("", new Vector3f());
        
        return storage;
    }

    private float checkTimeLoad (Storage_Area storage) throws Exception
    {
        float time = 0;
        
        for (int w = 0; w < storage.getWidth(); w++)
        {
            if (storage.Count(_currentRow, w) < storage.getHeight())
            {
                //Move container over crane rail.
                time += _moveContainer * w;
                //Lower container.
                time += _lower * (storage.getHeight() - storage.Count(_currentRow, w));
            }             
        }
        
        return time;
    }
    
    private float checkTimeUnload (Storage_Area storage) throws Exception
    {
        float time = 0;
        int columnIndex = getBestColumnIndex(storage, _currentRow);

        if (columnIndex == -1)
        { 
            for (int column = 0; column < storage.getWidth(); column++)
            {
                if (storage.Count(_currentRow, column) > 0)
                {
                    //Securing the container.
                    time += _secureContainer;
                    //Raising the container.
                    time += _raise * (storage.getHeight() - storage.Count(_currentRow, column));
                    //Move container over crane rail.
                    time += _moveContainer * column;
                }
            }
        }

        else if (storage.Count(_currentRow, columnIndex) > 0)
        {
            //Securing the container.
            time += _secureContainer;
            //Raising the container.
            time += _raise * (storage.getHeight() - storage.Count(_currentRow, columnIndex));
            //Move container over crane rail.
            time += _moveContainer;
        }

        return time;
    }
    
    /**
     * Checks how long it takes to move towards the designated row.
     * @param row
     * @return
     */
    protected float checkTimeMove (int row)
    {
        float time = 0;
        int move = 0;
        
        if (row < _currentRow)
            { move = _currentRow - row; }
        
        else if (row > _currentRow)
            { move = row - _currentRow; }
        
        if (_carriedContainer != null)
            { time += _moveLoaded * move; }
        
        else
            { time += _moveEmpty * move; }
        
        return time;
    }
    
    /**
     * Moves the crane to the designated row.
     * @param row
     */
    protected void moveRow (int row)
    {
        int move = 0;
        
        if (row < _currentRow)
            { move = _currentRow - row; }
        
        else if (row > _currentRow)
            { move = row - _currentRow; }
        
        _currentRow = row;
    }
    
    /**
     * Handles all operations for the crane. Uses the given messages to define
     * what it's actions are. The updateTime is used to determine how much of 
     * the actions it's able to accomplish in the given time.
     * @param updateTime
     */
    public void update(float updateTime)
    {
        if (parkinglotAGV.isEmpty() == false && parkinglotTransport.isEmpty() == false 
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
            else if (_tasks.isEmpty() == false)
            {
                Storage_Area storage = null;
                boolean taskPossible = false;
                
                if (_tasks.get(0) == _taskList.loadAGV || _tasks.get(0) == _taskList.unloadAGV)
                {
                    try
                    {
                        AGV a = (AGV)parkinglotAGV.getVehicles().get(0);
                        storage = a.storage;
                        taskPossible = true;
                    }
                    catch (Exception e)
                    {
                        taskPossible = false;
                    }
                }
                
                else if (_tasks.get(0) == _taskList.loadTransport || _tasks.get(0) == _taskList.unloadTransport)
                {
                    try
                    {
                        TransportVehicle t = (TransportVehicle)parkinglotTransport.getVehicles().get(0);
                        storage = t.storage;
                        taskPossible = true;
                    }
                    catch (Exception e)
                    {
                        taskPossible = false;
                    }
                }
                
                if (taskPossible == true)
                {
                    if (_taskTimeLeft > 0)
                    {
                        _taskTimeLeft -= updateTime;
                        
                        if (_taskTimeLeft <= 0)
                        {
                            try
                            {
                                switch (_tasks.get(0))
                                {
                                    case moveRowBase: moveRow(0); break;
                                    case moveRowUp: moveRow(_currentRow + 1); break;
                                    case loadAGV: loadAGV(storage); break;
                                    case unloadAGV: unloadAGV(storage); break;
                                    case loadTransport: loadTransport(storage); break;
                                    case unloadTransport: unloadTransport(storage); break;            
                                }
                                
                                if (_tasks.get(0) == _taskList.loadAGV || _tasks.get(0) == _taskList.unloadAGV)
                                {
                                    AGV a = (AGV)parkinglotAGV.getVehicles().get(0);
                                    a.storage = storage;
                                    parkinglotAGV.setVehicle(a, 0);
                                }
                                
                                else if (_tasks.get(0) == _taskList.loadTransport || _tasks.get(0) == _taskList.unloadTransport)
                                {
                                    TransportVehicle t = (TransportVehicle)parkinglotTransport.getVehicles().get(0);
                                    t.storage = storage;
                                    parkinglotTransport.setVehicle(t, 0);
                                }
                                
                                _tasks.remove(0);
                                
                                if (_taskTimeLeft < 0)
                                {   
                                    float time = _taskTimeLeft * -1;
                                    _taskTimeLeft = 0;
                                    this.update(time);
                                }
                                
                                if (_tasks.isEmpty() == true)
                                    { _Assignments.remove(0); }
                            }
                            catch (Exception e)
                                { System.out.println(e.getMessage()); }
                        }
                    }

                    else
                    {
                        try
                        {
                            switch (_tasks.get(0))
                            {
                                case moveRowBase: _taskTimeLeft = checkTimeMove(0); break;
                                case moveRowUp: _taskTimeLeft = checkTimeMove(_currentRow + 1); break;
                                case loadAGV: _taskTimeLeft = _secureContainer + (_lower * 7); break;
                                case unloadAGV: _taskTimeLeft = _secureContainer + (_raise * 7); break;
                                case loadTransport: _taskTimeLeft = checkTimeLoad(storage); break;
                                case unloadTransport: _taskTimeLeft = checkTimeLoad(storage); break;            
                            }
                        }
                        catch (Exception e)
                            { System.out.println(e.getMessage()); }
                    }
                }
            }
            
            else if (_tasks.isEmpty() == true)
            {
                try
                {
                    if (message.DestinationObject().equals(parkinglotAGV.getVehicles().get(0)))
                    {
                        agv = true;
                    } 
                }
                catch (Exception e) {  }
                
                try
                {
                    if (message.DestinationObject().equals(parkinglotTransport.getVehicles().get(0)))
                    {
                        transport = true;
                    } 
                }
                catch (Exception e) {  }

                try
                {
                    Storage_Area storage = null;
                    int maxRow;
                    
                    if (agv == true)
                    {
                        AGV a = (AGV)parkinglotAGV.getVehicles().get(0);
                        storage = a.storage;
                    }
                    
                    else if (transport == true)
                    {
                        TransportVehicle t = (TransportVehicle)parkinglotTransport.getVehicles().get(0);
                        storage = t.storage;
                    }
                    
                    if (storage != null)
                    {
                        maxRow = _railsLocation * (storage.getLength() / _totalCranes);
                    
                        if (storage.rowEmpty(_currentRow) == true && _currentRow != maxRow)
                            { _tasks.add(_taskList.moveRowUp); }
                        else if (storage.rowFull(_currentRow) == true && _currentRow == maxRow)
                            { _tasks.add(_taskList.moveRowBase); }
                        else if (storage.rowEmpty(_currentRow) == false)
                        {
                            if (agv == true)
                            {
                                _tasks.add(_taskList.unloadAGV);
                                _tasks.add(_taskList.loadTransport);
                            }
                            
                            else
                            {
                                _tasks.add(_taskList.unloadTransport);
                                _tasks.add(_taskList.loadAGV);
                            }
                        }
                    }
                }
                catch (Exception e) { }

                if (_tasks.isEmpty() == false)
                {
                    this.update(updateTime);
                }
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