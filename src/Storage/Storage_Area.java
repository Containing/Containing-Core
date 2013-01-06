package Storage;

import Helpers.Vector3f;
import Main.Container;

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

    private int containerCount = 0;
    
    public Storage_Area (int length, int width, int height, Vector3f pos)
    {
        stackField = new Container_Stack[length][width];
        
        for (int l = 0; l < length; l++)
        {
            for (int w = 0; w < width; w++)
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
    
    /**
     * Checks the container stack on the current row and column,
     * @param row The row to check
     * @param column The column to check
     * @return True if its empty, False otherwise
     * @throws Exception 
     */
    public int stackHeight(int row, int column) throws Exception
    {
        if ((row > _Length || row < 0) ||
            (column > _Width || column < 0))
        {
            System.out.println("Exception in Storage_Area : '" + this.toString() + "' Row || Column doesn't exist.");
            throw new Exception("Row || Column doesn't exist.");
        }
        return stackField[row][column].getHeight(); 
    }
    
    /**
     * Gets a Container reference
     * @param row The current row
     * @param column The current column
     * @param height The current height
     * @return The container on the given position
     */
    public Container CheckContainer(int row, int column, int height)throws Exception
    {
        if ((row > _Length || row < 0) ||
            (column > _Width || column < 0))
        {
            System.out.println("Exception in Storage_Area : '" + this.toString() + "' Row || Column doesn't exist.");
            throw new Exception("Row || Column doesn't exist.");
        }
        if(height <0 || height>=  stackField[row][column].getHeight())
        {    
            System.out.println("Exception in Storage_Area : '" + this.toString() + "' Height is wrong.");
            throw new Exception("Height is wrong.");
        }
        return stackField[row][column].peak(height);
    }
    
    public Container PeekContainer(int x, int z){
        return stackField[x][z].peak();
    }
    
    public Container PopContainer(int x, int z){
        containerCount--;
        return stackField[x][z].pop();
    }
    
    public void PushContainer(Container container, int x, int z) throws Exception{
        containerCount++;
        stackField[x][z].push(container);
    }
    
    public int getLength() {
        return _Length;
    }

    public int getWidth() {
        return _Width;
    }

    public int getHeight() {
        return _Height;
    }
 
    public int Count(){
        return containerCount;
    }
    
    public int Count(int x, int z){
        return stackField[x][z].getHeight();
    }
    
    public boolean IsFilled(){
        return _Length * _Height * _Width == containerCount;
    }
    
    @Override
    public String toString(){
        String returnString = "";
        for (int x = 0; x < _Length; x++) {
            returnString += (x == 0) ? "" : "\n";
            for (int z = 0; z < _Width; z++) {
                returnString += stackField[x][z].getHeight();
            }
        }
        return returnString;
    }
}
