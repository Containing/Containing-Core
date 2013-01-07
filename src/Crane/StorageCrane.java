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

/**
 * @author Karel Gerbrands
 * @version 0.3
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
    
    public StorageCrane (int rails, int range, Parkinglot<AGV> parkingA, Parkinglot<AGV> parkingB, Storage_Area storage, Vector3f pos)
    {
        super(rails, range, parkingA, parkingB);
        
        position = pos;
        _storageField = storage;
    }
    
    public Container getContainer ()
    {
        return null;
    }

    public boolean storeContainer (Container container) throws Exception
    {
        if (_storageField.IsFilled() == true)
        {
            //System.out.println("Exception in StorageCrane : '" + this.toString() + "' Can't store a container in a full storage area.");
            throw new Exception("Can't store a container in a full storage area.");
        }
        
        else { return true; }
    }
}
