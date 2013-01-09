package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import Vehicles.AGV;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Karel Gerbrands
 * @version 0.3
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane, which is used to move containers 
 * from vehicle to vehicle.
 */
public class Crane implements IMessageReceiver
{
    public Parkinglot parkinglotAGV;
    public Parkinglot parkinglotTransport;
    
    private final int _rails;
    private final int _range;
    private Container _carriedContainer;
    private int currentRow;
    private Date maxRowDate;
    private Date minRowDate;
    
    private ArrayList<Message> _Assignments;
    
    public Crane (int rails, int range, Parkinglot<AGV> parkingAGV, Parkinglot parkingTransport)
    {
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;
        
        _rails = rails;
        _range = range;
        _Assignments = new ArrayList<Message>();
    }
    
    public int getBestRowIndex(Storage_Area storage, int rowIndex, Date date) throws Exception 
    {        
        if (date == null)
            { throw new Exception("The date can't be null."); }
        
        if (0 > rowIndex || rowIndex > storage.getWidth())
            { throw new Exception("Row " + rowIndex + " doesn't exist on this storage."); }        

        else
        {
            for (int w = 0; w < storage.getWidth(); w++) 
            {
                if (storage.Count(rowIndex, w) > 0)
                {
                    if (date == storage.peakContainer(rowIndex, w).getDepartureDateStart())
                        { return w; }
                }
            }
        }
        
        return 0;
    }
    
    public void findLowestRowDate (Storage_Area storage, int rowIndex) throws Exception
    {
        if (0 > rowIndex || rowIndex > storage.getWidth())
        { throw new Exception("Row " + rowIndex + " doesn't exist on this storage."); }

        else
        {
            Date max = null;

            for (int w = 0; w < storage.getWidth(); w++) 
            {
                if (storage.Count(rowIndex, w) > 0)
                {
                    Date now = storage.peakContainer(rowIndex, w).getDepartureDateStart();

                    if (max == null)
                        { max = now; }
                    
                    else if (max.before(now))
                        { max = now; }
                }
            }
        }
    }
    
    public void findHighestRowDate (Storage_Area storage, int rowIndex) throws Exception
    {
        if (0 > rowIndex || rowIndex > storage.getWidth())
        { throw new Exception("Row " + rowIndex + " doesn't exist on this storage."); }

        else
        {
            Date max = null;

            for (int w = 0; w < storage.getWidth(); w++) 
            {
                if (storage.Count(rowIndex, w) > 0)
                {
                    Date now = storage.peakContainer(rowIndex, w).getDepartureDateStart();

                    if (max == null)
                        { max = now; }
                    
                    else if (max.after(now))
                        { max = now; }
                }
            }
        }
    }
    
    /**
     * 
     * @param storage
     * @return
     * @throws Exception When no container is being carried, when the storage is full,
     */
    public Storage_Area loadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer == null)
        { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
        { throw new Exception("Can't place an container in a full storage."); }
                
        else if (storage.getWidth() == 1)
        {
            for (int i = 0; i > storage.getLength(); i++)
            {
                if (storage.Count(i, 0) != storage.getHeight())
                {
                    storage.pushContainer(_carriedContainer, i, 0);
                    _carriedContainer = null;
                    break;
                }
                
                else 
                { throw new Exception("Can't place an container on a full stack."); }
            }
        }
        
        return storage;
    }
    
    public Storage_Area loadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer == null)
        { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
        { throw new Exception("Can't place an container in a full storage."); }
                
        else if (storage.Count(row, column) == storage.getHeight())
        {
            { throw new Exception("Can't stack containers higher than 6."); }
        }
        
        storage.pushContainer(_carriedContainer, row, column);
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer != null)
        { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
        { throw new Exception("Can't grab an container from an empty storage."); }

        else if (storage.getWidth() == 1)
        {
            for (int i = 0; i > storage.getLength(); i++)
            {
                if (storage.Count(i, 0) != 0)
                {
                    _carriedContainer = storage.popContainer(i, 0);
                    break;
                }
                
                else 
                { throw new Exception("Can't pick up an container from an empty stack."); }
            }
        }
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer != null)
        { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
        { throw new Exception("Can't grab an container from an empty storage."); }

        else if (storage.Count(row, column) == 0)
        { throw new Exception("Can't grab an container from an empty stack."); }
        
        return storage;
    }
    
    public void update(int updateTime)
    {
        
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