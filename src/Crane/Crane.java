package Crane;

import Helpers.IMessageReceiver;
import Helpers.Message;
import Main.Container;
import Parkinglot.Parkinglot;
import Storage.Storage_Area;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Karel Gerbrands
 * @version 0.5
 * @since 13-12-2012
 * 
 * This class is used in simulating a crane, which is used to move containers 
 * from vehicle to vehicle.
 */
public class Crane implements IMessageReceiver
{
    public Parkinglot parkinglotAGV;
    public Parkinglot parkinglotTransport;
    
    protected final int _rails;
    protected final int _range;
    protected Container _carriedContainer;
    protected int _currentRow;
    
    private ArrayList<Message> _Assignments;
    
    public Crane (int rails, int range, Parkinglot parkingAGV, Parkinglot parkingTransport) throws Exception
    {
        if (parkingAGV == null || parkingTransport == null)
        { throw new Exception("A parkinglot can't be null."); }
        
        if (rails < 0 || range < 0)
            { throw new Exception("The rails or range can't be smaller than zero."); }
        
        parkinglotAGV = parkingAGV;
        parkinglotTransport = parkingTransport;
        
        _rails = rails;
        _range = range;
        _Assignments = new ArrayList<Message>();
    }

    public int getBestRowIndex(Storage_Area storage, int columnIndex) throws Exception 
    {
        if (0 > columnIndex || columnIndex > storage.getWidth())
            { throw new Exception("Row " + columnIndex + " doesn't exist on this storage."); }        

            Date date = null;

        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(columnIndex, w) > 0)
            {
                Date now = storage.peekContainer(columnIndex, w).getDepartureDateStart();

                if (date == null)
                    { date = now; }
                    
                else if (date.before(now))
                    { date = now; }
            }
        }
        
        for (int w = 0; w < storage.getWidth(); w++) 
        {
            if (storage.Count(columnIndex, w) > 0)
            {
                if (date == storage.peekContainer(columnIndex, w).getDepartureDateStart())
                    { return w; }
            }
        }

        return -1;
    }
    
    /**
     * 
     * @param storage
     * @return
     * @throws Exception When no container is being carried, when the storage is full,
     * when the current row is full.
     */
    public Storage_Area loadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (storage.rowFull(_currentRow) == true)
            { throw new Exception("Can't place an container in a full row."); }

        else
        {
            for (int w = 0; w < storage.getWidth(); w++)
            {
                if (storage.Count(_currentRow, w) < storage.getHeight())
                {
                    storage.pushContainer(_carriedContainer, _currentRow, w);
                    _carriedContainer = null;
                     break;
                }             
            }
            
            if (_carriedContainer != null)
                { throw new Exception("Couldn't place container."); }
        }

        return storage;
    }
    
    public Storage_Area loadContainer (Storage_Area storage, int row, int column) throws Exception
    {
        if (_carriedContainer == null)
            { throw new Exception("Can't place an container when one isn't being carried."); }
        
        else if (storage.isFilled() == true) 
            { throw new Exception("Can't place an container in a full storage."); }
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }
                
        else if (storage.Count(row, column) == storage.getHeight())
            { throw new Exception("Can't stack containers higher than 6."); }
        
        _currentRow = row;
        storage.pushContainer(_carriedContainer, _currentRow, column);
        _carriedContainer = null;
        
        return storage;
    }
    
    public Storage_Area unloadContainer (Storage_Area storage) throws Exception
    {
        if (_carriedContainer != null)
            { throw new Exception("Can't grab an container when one is already being carried."); }
        
        else if (storage.Count() == 0)
            { throw new Exception("Can't grab an container from an empty storage."); }
        
        else if (storage.rowEmpty(_currentRow) == true)
            { throw new Exception("Can't grab an container from an empty row."); }

        else
        {
            int columnIndex = getBestRowIndex(storage, _currentRow);
            
            if (columnIndex == -1)
            { 
                for (int column = 0; column < storage.getWidth(); column++)
                {
                    if (storage.Count(_currentRow, column) > 0)
                    {
                        _carriedContainer = storage.popContainer(_currentRow, column);
                    }
                }
            }
            
            else if (storage.Count(_currentRow, columnIndex) > 0)
            {
                _carriedContainer = storage.popContainer(_currentRow, columnIndex);
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
        
        else if (row < 0 || row > storage.getLength() || column < 0 || column > storage.getWidth())
            { throw new Exception("Row or column don't exist."); }

        else if (storage.Count(row, column) == 0)
            { throw new Exception("Can't grab an container from an empty stack."); }
        
        _currentRow = row;
        _carriedContainer = storage.popContainer(_currentRow, column);
        
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
    
    public void setRow (int row)
    {
        _currentRow = row;
    }
}