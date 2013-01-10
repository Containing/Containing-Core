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

    public void storeContainer (Container container) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't store an container when none is being carried."); }
        
        if (container == null)
            { throw new Exception("The container can't be null."); }
        
        if (_storageField.isFilled() == true)
            { throw new Exception("Can't store a container in a full storage area."); }
        
        else
        {
            for (int row = 0; row < _storageField.getLength(); row++)
            {
                if(_storageField.rowFull(row) == true)
                {
                    
                }
            }
            
            _storageMap.put(container.getId(), new int[] {1, 2, 3});
        }
    }
    
    @Override
    public void update(int updateTime)
    {
        
    }
}
