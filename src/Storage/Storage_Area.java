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
    private int _containerCount = 0;
    
    public Storage_Area (int length, int width, int height, Vector3f pos) throws Exception
    {
        _stackField = new Container_Stack[length][width];
        
        if (length < 1 || width < 1)
        {
            throw new Exception("Width or length can't be smaller than 1.");
        }
        
        for (int l = 0; l < length; l++)
        {
            for (int w = 0; w < width; w++)
            {
                try
                {
                    _stackField[l][w] = new Container_Stack(height);
                }
                catch (Exception e)
                {
                    throw new Exception("Height must be higher than 0.");
                }
            }
        }
        
        position = pos;
        _Length = length;
        _Width = width;
        _Height = height;
    }

    public boolean rowEmpty(int row) throws Exception
    {
        if (row > _Length || row < 0)
        {
            System.out.println("Exception in Storage_Area : '" + this.toString() + "' Row doesn't exist.");
            throw new Exception("Row doesn't exist.");
        }
        boolean empty = true;
        
        for (Container_Stack c : _stackField[row])
        {
            if (c.getHeight() > 0)
            {
                empty = false;
            }
        }
        
        return empty;
    }   
    
    
    public Container PeekContainer(int x, int z) throws Exception
    {
        return _stackField[x][z].peak();
    }
    
    public Container PopContainer(int x, int z) throws Exception
    {
        _containerCount--;
        return _stackField[x][z].pop();
    }
    
    public void PushContainer(Container container, int x, int z) throws Exception
    {
        _containerCount++;
        _stackField[x][z].push(container);
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
    
    public int Count(int x, int z)
    {
        return _stackField[x][z].getHeight();
    }
    
    public boolean IsFilled ()
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
