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
    private byte _size;
    private byte _parked;
    public Node node;
    
    public Parkinglot (byte size, Node n)
    {
        _parkingSpace = new Vector<T>();
        node = n;
        _size = size;
    }
    
    public boolean park (T vehicle)
    {
        if (_parked == _size)
        {
            return false;
        }
        
        else
        {
            _parkingSpace.add(vehicle);
            _parked++;
            return true;
        }
    }
    
    public T unPark (T vehicle)
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
        
        return null;
    }
}
