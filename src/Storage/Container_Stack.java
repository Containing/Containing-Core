package Storage;

import Main.Container;

/**
 * @author Karel Gerbrands
 * @version 0.1
 * @since 12-12-2012
 * 
 * This class is used in simulating a stack of containers, which in essence is
 * a simple stack structure.
 */
public class Container_Stack 
{
    private int _currentHeight;
    private int _maxHeight;
    private Container[] _containerArray;
    
    public Container_Stack (int height)
    {
        _containerArray = new Container[6];
        _maxHeight = height;
    }
    
    /**
    * @return Returns the amount of containers on the stack.
     */
    public int getHeight ()
    {
        return _currentHeight;
    }
    
    /**
     * Removes and returns the top container.
     * 
     * @return Returns the top container of the stack. If a null is returned,
     * the stack is empty.
     */
    public Container pop ()
    {
        if (_currentHeight == 0)
        {
            return null;
        }
        
        else
        {
            _currentHeight--;
            return _containerArray[_currentHeight];
        }
    }

    /**
     * Shows the top container but doesn't remove it. Only to be used if data
     * has to be checked on the top container.
     * 
     * @return Returns the top container of the stack. If a null is returned,
     * the stack is empty.
     */
    public Container peak ()
    {
        if (_currentHeight == 0)
        {
            return null;
        }
        
        else
        {
            return _containerArray[(_currentHeight-1)];
        }
    }
    
    /**
     * Places the given container ontop of the stack. Unless the stack is full.
     * 
     * @param container The container to place ontop of the stack.
     * @return Returns false if the stack is full, else true.
     */
    public Boolean push (Container container)
    {
        if (_currentHeight == _maxHeight || container == null)
        {
            return false;
        }
        
        else
        {
            _containerArray[_currentHeight] = container;
            _currentHeight++;
            return true;
        }
    }
}

