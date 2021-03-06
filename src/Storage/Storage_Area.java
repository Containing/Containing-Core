package Storage;

import Helpers.Vector3f;
import Main.Container;

/**
 * @author Karel Gerbrands
 * @version 0.3
 * @since 18-12-2012
 * 
 * This class is used in simulating the field where the container stacks are
 * contained.
 */
public class Storage_Area 
{
    public Vector3f position;
    
    private Container_Stack[][] _stackField;
    private int _Length;
    private int _Width;
    private int _Height;
    private int _containerCount;
    private boolean[] _rowFullMap;
    private boolean[] _rowEmptyMap;
    
    public Storage_Area (int length, int width, int height, Vector3f pos) throws Exception
    {
        _stackField = new Container_Stack[length][width];
        
        if (length < 1 || width < 1)
        { throw new Exception("Width or length can't be smaller than 1."); }
        
        for (int l = 0; l < length; l++)
        {
            for (int w = 0; w < width; w++)
            {
                try
                {
                    _stackField[l][w] = new Container_Stack(height);
                }
                catch (Exception e)
                { throw new Exception("Height must be higher than 0."); }
            }
        }
        
        position = pos;
        _Length = length;
        _Width = width;
        _Height = height;
        _rowFullMap = new boolean[length];
        _rowEmptyMap = new boolean[length];
        _containerCount = 0;
        
        for (int i = 0; i < length; i++)
        {
            _rowFullMap[i] = false;
            _rowEmptyMap[i] = true;
        }
    }

    private void checkRow(int row) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }
        
        for (Container_Stack c : _stackField[row])
        {
            if (c.getHeight() > 0)
            {
                _rowEmptyMap[row] = false;
                break;
            }

            else
                { _rowEmptyMap[row] = true; }
        }
        
        for (Container_Stack c : _stackField[row])
        {
            if (c.getHeight() != _Height)
            {
                _rowFullMap[row] = false;
                break;
            }
            
            else
                { _rowFullMap[row] = true; }
        }
    }
    
    public boolean rowEmpty(int row) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }

        return _rowEmptyMap[row];
    }
    
    public boolean rowFull(int row) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }

        return _rowFullMap[row];
    }

    public Container peekContainer(int row, int column) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }
        
        if (column > _Width-1 || column < 0)
            { throw new Exception("Row doesn't exist."); }
        
        return _stackField[row][column].peek();
    }
    
    public Container popContainer(int row, int column) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }
        
        if (column > _Width-1 || column < 0)
            { throw new Exception("Row doesn't exist."); }
        
        _containerCount--;
        Container r = _stackField[row][column].pop();
        
        checkRow(row);
        
        return r;
    }
    
    public void pushContainer(Container container, int row, int column) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }
        
        if (column > _Width-1 || column < 0)
            { throw new Exception("Row doesn't exist."); }
        
        _containerCount++;
        _stackField[row][column].push(container);
        checkRow(row);
    }
    
    public int getLength() 
    {
        return _Length;
    }

    public int getWidth() 
    {
        return _Width;
    }

    public int getHeight() 
    {
        return _Height;
    }
 
    public int Count()
    {
        return _containerCount;
    }
    
    public int Count(int row, int column) throws Exception
    {
        if (row > _Length-1 || row < 0)
            { throw new Exception("Row doesn't exist."); }
        
        if (column > _Width-1 || column < 0)
            { throw new Exception("Row doesn't exist."); }
                
        return _stackField[row][column].getHeight();
    }
    
    public boolean isFilled ()
    {
        return _Length * _Height * _Width == _containerCount;
    }
    
    @Override
    public String toString ()
    {
        String returnString = "";
        for (int x = 0; x < _Length; x++) {
            returnString += (x == 0) ? "" : "\n";
            for (int z = 0; z < _Width; z++) {
                returnString += _stackField[x][z].getHeight();
            }
        }
        return returnString;
    }
}
