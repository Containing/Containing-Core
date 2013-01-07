package Parkinglot;

import Pathfinding.Node;
import java.util.Vector;

/**
 * @author Karel Gerbrands
 * @version 0.1
 * @since 12-12-2012
 * 
 * This class is used in simulating a place where a vehicle can park.
 */
public class Parkinglot <T>
{
    private Vector<T> _parkingSpace;
    private int _size;
    private int _parked;
    public Node node;
    
    public Parkinglot (int size, Node n)
    {
        _parkingSpace = new Vector<T>();        
        _size = size;
        node = n;
    }
    
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
    
    public T unPark (T vehicle) throws Exception
    {
        for (T parkedVehicle : _parkingSpace)
        {
            if (parkedVehicle == vehicle)
            {
                _parked--;
                _parkingSpace.removeElement(parkedVehicle);
                return parkedVehicle;
            }
        }
        
        System.out.println("Exception in Parkinglot : '" + this.toString() + "' Can't unpark the vehicle : '" + vehicle.toString() + "'");
        throw new Exception("Can't unpark the vehicle from the parkinglot.");
    }
    
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
