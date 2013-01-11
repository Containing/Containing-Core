package Parkinglot;

import Pathfinding.Node;
import java.util.ArrayList;

/**
 * @author Karel Gerbrands
 * @version 0.3
 * @since 12-12-2012
 * 
 * This class is used in simulating a place where a vehicle can park.
 */
public class Parkinglot <T>
{
    private ArrayList<T> _parkingSpace;
    private int _size;
    private int _parked;
    public Node node;
    
    public Parkinglot (int size, Node n) throws Exception
    {
        if (size < 1)
            { throw new Exception("The size can't be smaller than one."); }
        
        if (n == null)
            { throw new Exception("The node can't be null."); }
        
        
        _parkingSpace = new ArrayList<T>();        
        _size = size;
        node = n;
    }
    
    /**
     *  
     * @return a reference to all the vehicles inside the parkinglot.
     * @throws Exception when there are no vehicles parked in the parkinglot.
     */
    public ArrayList<T> getVehicles () throws Exception
    {
        if (_parked > 0)
        {
            return _parkingSpace;
        }
        
        else
        {
            System.out.println("Exception in Parkinglot : '" + this.toString() + "' Can't request vehicles from an empty parkinglot.");
            throw new Exception("Can't request the vehicles from an empty parkinglot.");
        }
    }
    
    /**
     * 
     * @param vehicle The vehicle to be parked.
     * @return True if succesful.
     * @throws Exception Is thrown when the vehicle can't be parked.
     */
    public boolean park (T vehicle) throws Exception
    {
        if (_parked == _size)
        {
            System.out.println("Exception in Parkinglot : '" + this.toString() + "' Can't park the vehicle : '" + vehicle.toString() + "', parkinglot is full.");
            throw new Exception("Can't park the vehicle in a full parkinglot.");
        }
        
        else
        {
            _parkingSpace.add(vehicle);
            _parked++;
            return true;
        }
    }
    
    /**
     * 
     * @param vehicle The vehicle to unpark.
     * @return The vehicle that has been requested to be unparked.
     * @throws Exception Is thrown when the vehicle can't be unparked.
     */
    public T unPark (T vehicle) throws Exception
    {
        for (T parkedVehicle : _parkingSpace)
        {
            if (parkedVehicle == vehicle)
            {
                _parked--;
                _parkingSpace.remove(parkedVehicle);
                return parkedVehicle;
            }
        }
        
        System.out.println("Exception in Parkinglot : '" + this.toString() + "' Can't unpark the vehicle : '" + vehicle.toString() + "'");
        throw new Exception("Can't unpark the vehicle from the parkinglot.");
    }
    
    /**
     * 
     * @return Returns whether the parkinglot is full or not.
     */
    public boolean isFull ()
    {
        if (_parked == _size)
        {
            return true;
        }
        
        else
        {
            return false;
        }
    }
}
