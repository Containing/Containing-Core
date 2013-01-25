/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crane;

import Helpers.Vector3f;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
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
    public Vector3f position;
    
    private Storage_Area _storageField;
    private HashMap _storageMap;
    
    public StorageCrane (Parkinglot<AGV> parkingA, Parkinglot<AGV> parkingB) throws Exception
    {
        super(1, Crane.CraneType.storage, parkingA, parkingB);

        position = Vector3f.GetCenter(parkingA.node.getPosition(), parkingB.node.getPosition());
        Storage_Area storage = new Storage_Area(98,6,6, position);   
        _storageField = storage;
        _storageMap = new HashMap(_storageField.getLength() * _storageField.getWidth());
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
        
    }
}
