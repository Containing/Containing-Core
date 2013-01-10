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
    
    public StorageCrane (int rails, int range, Parkinglot<AGV> parkingA, Parkinglot<AGV> parkingB, Storage_Area storage, Vector3f pos)
    {
        super(rails, range, parkingA, parkingB);
        
        position = pos;
        _storageField = storage;
        _storageMap = new HashMap(_storageField.getLength() * _storageField.getWidth() * _storageField.getHeight());
    }
    
    public void getContainer (String containerID) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't get an container when one is being carried."); }
        
        if (containerID == null)
            { throw new Exception("The ID can't be null."); }
        
        if (_storageField.Count() == 0)
            { throw new Exception("Can't get an container from an empty storage."); }
        
        int[] coordinates =(int[])_storageMap.get(containerID);
        
        if (_storageField.Count(coordinates[0], coordinates[1]) != coordinates[2])
        { throw new Exception("Specified container isn't on top."); }
                
        else
        {
            _storageMap.remove(containerID);
            this.unloadContainer(_storageField, coordinates[0], coordinates[1]);
        }
    }

    public void storeContainer () throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't store an container when none is being carried."); }
        
        if (_storageField.isFilled() == true)
            { throw new Exception("Can't store a container in a full storage area."); }
        
        if (_storageField.rowFull(_currentRow) == true)
            { throw new Exception("Can't store a container in a full row."); }

        else
        {
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
                            }
                            
                            else if (_carriedContainer.getDepartureDateStart().after(
                            _storageField.peakContainer(row, column).getDepartureDateStart())
                            == true || _carriedContainer.getDepartureDateStart().equals(
                            _storageField.peakContainer(row, column).getDepartureDateStart()) == true)
                            {
                                _storageMap.remove(_storageField.peakContainer(row, column).getId());
                                _storageMap.put(_carriedContainer.getId(), new int[] {row, column, 1});
                                this.loadContainer(_storageField, row, column);
                            }
                        }
                    }
                }
            }
            
            if (_carriedContainer == null)
                { throw new Exception("Couldn't store container."); }
        }
    }
    
    @Override
    public void update(int updateTime)
    {
        
    }
}
