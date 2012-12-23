package Storage;

import Helpers.Vector3f;

/**
 * @author Karel Gerbrands
 * @version 0.1
 * @since 18-12-2012
 * 
 * This class is used in simulating the field where the container stacks are
 * contained.
 */
public class Storage_Area 
{
    public Container_Stack[][] stackField;
    public Vector3f position;
    private int _Length;
    private int _Width;
    private int _Height;

    public Storage_Area (int length, int width, int height, Vector3f pos)
    {
        stackField = new Container_Stack[length][width];
        
        for (int l = 0; l < length; l++)
        {
            for (int w = 0; w > width; w++)
            {
                stackField[l][w] = new Container_Stack(height);
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
        
        for (Container_Stack c : stackField[row])
        {
            if (c.getHeight() > 0)
            {
                empty = false;
            }
        }
        
        return empty;
    }
}
