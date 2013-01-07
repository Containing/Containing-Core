/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crane;

import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;

/**
 *
 * @author Karel
 */
public class StorageCrane extends Crane
{
    private Storage_Area _storageField;
    
    public StorageCrane (int rails, int range, Parkinglot<AGV> parkingA, Parkinglot<AGV> parkingB, Storage_Area storage)
    {
        super(rails, range, parkingA, parkingB);
        
        _storageField = storage;
    }
    
    public boolean getContainer ()
    {
        return false;
    }

    public boolean storeContainer ()
    {
        return false;
    }
}
