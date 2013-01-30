/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crane;

import Helpers.Message;
import Helpers.Vector3f;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
import Vehicles.TransportVehicle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Karel Gerbrands
 * @version 0.5
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane on the storage area, which is used 
 * to move containers from AGV to the storage area.
 * 
 * Extends the normal Crane class.
 */
public class StorageCrane extends Crane
{
    private Storage_Area _storageField;
    private HashMap _storageMap;
    private enum _taskList { unloadA, unloadB, loadA, loadB, getContainer, storeContainer, moveBase };
    private ArrayList<_taskList> _tasks;
    
    public StorageCrane (int id, Parkinglot parkingA, Parkinglot parkingB) throws Exception
    {
        super(id, 1, Crane.CraneType.storage, parkingA, parkingB);
        this._position = Vector3f.GetCenter(parkingA.node.getPosition(), parkingB.node.getPosition());
        Storage_Area storage = new Storage_Area(98,6,6, Vector3f.GetCenter(parkingA.node.getPosition(), parkingB.node.getPosition()));   
        _storageField = storage;
        _storageMap = new HashMap(_storageField.getLength() * _storageField.getWidth());
        _tasks = new ArrayList<_taskList>();
    }
    
    private void loadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }
                
        else if (storage.Count(row, column) == storage.getHeight())
            { throw new Exception("Can't stack containers higher than 6."); }
        
        _taskTimeLeft += checkTimeMove(row);
        moveRow(row);
        //Move container over crane rail.
        _taskTimeLeft += _moveContainer * column;
        //Lower container.
        _taskTimeLeft += _lower * (storage.getHeight() - storage.Count(_currentRow, column));
        storage.pushContainer(_carriedContainer, _currentRow, column);
        _carriedContainer = null;
    }
    
    private Container unloadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
            { throw new Exception("Can't grab an container from an empty storage."); }
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }

        else if (storage.Count(row, column) == 0)
            { throw new Exception("Can't grab an container from an empty stack."); }
        
        Container cont = null;
        moveRow(row);
        //Securing the container.
        _taskTimeLeft += _secureContainer;
        //Raising the container.
        _taskTimeLeft += _raise * (storage.getHeight() - storage.Count(_currentRow, column));
        //Move container over crane rail.
        _taskTimeLeft += _moveContainer * column;
        cont = storage.popContainer(_currentRow, column);
        
        return cont;
    }
    
    private void getContainer (String containerID) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't get an container when one is being carried."); }
        
        if (containerID == null)
            { throw new Exception("The ID can't be null."); }
        
        if (_storageField.Count() == 0)
            { throw new Exception("Can't get an container from an empty storage."); }

        int[] coordinates =(int[])_storageMap.get(containerID);

        if (coordinates == null)
            { throw new Exception("Specified ID doesn't exist within this storage."); }

        if (_storageField.Count(coordinates[0], coordinates[1]) != coordinates[2])
            { throw new Exception("Specified container isn't on top."); }
                
        else
        {
            _storageMap.remove(containerID);
            this.unloadContainer(_storageField, _currentRow, _currentRow);
            _carriedContainer.setDatabasePosition("", new Vector3f());

            if (_storageField.Count(coordinates[0], coordinates[1]) > 0)
            {                
                _storageMap.put(_storageField.peekContainer(coordinates[0], coordinates[1]).getId(),
                                new int[] { coordinates[0], coordinates[1], 
                                _storageField.Count(coordinates[0], coordinates[1]) }
                                );
            }
        }
    }

    private void storeContainer () throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't store an container when none is being carried."); }
        
        if (_storageField.isFilled() == true)
            { throw new Exception("Can't store a container in a full storage area."); }
        
        if (_storageField.rowFull(_currentRow) == true)
            { throw new Exception("Can't store a container in a full row."); }

        else
        {
            outerloop:
            for (int row = 0; row < _storageField.getLength(); row++)
            {
                if(_storageField.rowFull(row) == false)
                {
                    for (int column = 0; column < _storageField.getWidth(); column++)
                    {
                        if (_storageField.Count(row, column) < _storageField.getHeight())                            
                        {
                            if (_storageField.Count(row, column) == 0)
                            {
                                _storageMap.put(_carriedContainer.getId(), new int[] {row, column, 1});
                                this.loadContainer(_storageField, row, column);
                                break outerloop;
                            }
                            
                            else if (_carriedContainer.getDepartureDateStart().after(
                            _storageField.peekContainer(row, column).getDepartureDateStart())
                            == true || _carriedContainer.getDepartureDateStart().equals(
                            _storageField.peekContainer(row, column).getDepartureDateStart()) == true)
                            {
                                _storageMap.remove(_storageField.peekContainer(row, column).getId());
                                _storageMap.put(_carriedContainer.getId(), new int[] {row, column, _storageField.Count(row, column)+1});
                                _carriedContainer.setDatabasePosition(Integer.toString(this.getID()), new Vector3f(column, _storageField.Count(row, column)+1, row));
                                this.loadContainer(_storageField, row, column);
                                break outerloop;
                            }
                        }
                    }
                }
            }
            
            if (_carriedContainer != null)
                { throw new Exception("Couldn't store container."); }
        }
    }
    
    @Override
    public void update(float updateTime)
    {
        if (parkinglotAGV.isEmpty() == false && parkinglotTransport.isEmpty() == false 
            && Available() == false && updateTime > 0)
        {
            Message message = _Assignments.get(0);
            
            if (message.Load() == false && message.UnLoad() == false)
            {
                _Assignments.remove(0);
                this.update(updateTime);
            }
            
            else if (_tasks.isEmpty() == false)
            {
                Storage_Area storage = null;
                boolean taskPossible = false;
                
                if (_tasks.get(0) == _taskList.loadA || _tasks.get(0) == _taskList.unloadA)
                {
                    try
                    {
                        ArrayList<AGV> agvList = parkinglotAGV.getVehicles();
                        for (AGV agv : agvList)
                        {
                            if (message.DestinationObject().equals(agv))
                            {
                                storage = agv.storage;
                                taskPossible = true;
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        taskPossible = false;
                    }
                }
                
                else if (_tasks.get(0) == _taskList.loadB || _tasks.get(0) == _taskList.unloadB)
                {
                    try
                    {
                        ArrayList<AGV> agvList = parkinglotTransport.getVehicles();
                        for (AGV agv : agvList)
                        {
                            if (message.DestinationObject().equals(agv))
                            {
                                storage = agv.storage;
                                taskPossible = true;
                            }
                        }
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
                                if (_tasks.get(0) == _taskList.loadA || _tasks.get(0) == _taskList.unloadA)
                                {
                                    AGV a = (AGV)parkinglotAGV.getVehicles().get(0);
                                    a.storage = storage;
                                    parkinglotAGV.setVehicle(a, 0);
                                }
                                
                                else if (_tasks.get(0) == _taskList.loadA || _tasks.get(0) == _taskList.unloadB)
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
                                case moveBase: this.moveRow(_storageField.getLength() / 2); break;
                                case loadA: this.loadContainer(storage, 0, 0); break;
                                case unloadA: this.unloadContainer(storage, 0, 0); break;
                                case loadB: this.loadContainer(storage, 0, 0); break;
                                case unloadB: this.unloadContainer(storage, 0, 0); break;
                                case getContainer: Container cont = (Container)message.RequestedObject(); this.getContainer(Integer.toString(cont.getId())); break;  
                                case storeContainer: this.storeContainer(); break;
                            }
                        }
                        catch (Exception e)
                            { System.out.println(e.getMessage()); }
                    }
                }
            }
            
            else
            {
                if (message.Load() == true)
                {
                    try 
                    {
                        if (parkinglotAGV.isEmpty() == false)
                        {
                            ArrayList<AGV> agvList = parkinglotAGV.getVehicles();
                            for (AGV agv : agvList)
                            {
                                if (message.DestinationObject().equals(agv))
                                {
                                    _tasks.add(_taskList.getContainer);
                                    _tasks.add(_taskList.loadA);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e)
                        {  }
                    
                    try 
                    {
                        if (parkinglotAGV.isEmpty() == false)
                        {
                            ArrayList<AGV> agvList = parkinglotTransport.getVehicles();
                            for (AGV agv : agvList)
                            {
                                if (message.DestinationObject().equals(agv))
                                {
                                    _tasks.add(_taskList.getContainer);
                                    _tasks.add(_taskList.loadB);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e)
                        {  }
                }
                
                else if (message.UnLoad() == true)
                {
                    try 
                    {
                        if (parkinglotAGV.isEmpty() == false)
                        {
                            ArrayList<AGV> agvList = parkinglotAGV.getVehicles();
                            for (AGV agv : agvList)
                            {
                                if (message.DestinationObject().equals(agv))
                                {
                                    _tasks.add(_taskList.unloadA);
                                    _tasks.add(_taskList.storeContainer);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e)
                        {  }
                    
                    try 
                    {
                        if (parkinglotAGV.isEmpty() == false)
                        {
                            ArrayList<AGV> agvList = parkinglotTransport.getVehicles();
                            for (AGV agv : agvList)
                            {
                                if (message.DestinationObject().equals(agv))
                                {
                                    _tasks.add(_taskList.unloadB);
                                    _tasks.add(_taskList.storeContainer);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e)
                        {  }
                }
            }
        }
    }
    
    /**
     * Retrieve storage area
     * @return Storage_Area
     */
    public final Storage_Area GetStorageArea() {
        return _storageField;
    }
}
