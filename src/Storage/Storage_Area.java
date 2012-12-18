package Storage;

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
    public Container_Stack[] stackField;
    public int Length;
    public int Width;
    public int Height;

    public Storage_Area (int length, int width, int height)
    {
        stackField = new Container_Stack[length * width];
        
        for (Container_Stack CS : stackField)
        {
            CS = new Container_Stack(height);
        }
        
        Length = length;
        Width = width;
        Height = height;
    }
    
    /**
     * This function returns the row in which the given stack is located.
     * @param currentStack The stack you wish to check given as an int.
     * @return The row in which the stack is located.
     */
    public int getRow (int currentStack)
    {
        for (int i = 1; i >= Length; i++)
        {
            if (currentStack >= Width*i && currentStack <= Width*i)
            {
                return i;
            }
        }
        
        return 0;
    }
}
