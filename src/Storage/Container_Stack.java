package Storage;

import Main.Container;
import java.util.EmptyStackException;

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
    
    public Container_Stack (int height) throws Exception
    {
        if (height < 1)
        {
            throw new Exception("Container stack height must be higher than 0.");
        }
        _containerArray = new Container[height];
        _currentHeight = 0;
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
     * Shows the top container but doesn't remove it. Only to be used if data
     * has to be checked on the top container.
     * 
     * @return Returns the top container of the stack. If a null is returned,
     * the stack is empty.
     */
    public Container peak () throws EmptyStackException
    {
        if (_currentHeight == 0)
        {
            System.out.println("Exception in Container_Stack : '" + this.toString() + "' Can't peak from an empty stack.");
            throw new EmptyStackException();
        }
        
        else
        {
            return _containerArray[(_currentHeight-1)];
        }
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
            System.out.println("Exception in Container_Stack : '" + this.toString() + "' Can't pop a container from an empty stack.");
            throw new EmptyStackException();
        }
        
        else
        {
            _currentHeight--;
            return _containerArray[_currentHeight];
        }
    }

    
    /**
     * Places the given container ontop of the stack. Unless the stack is full.
     * 
     * @param container The container to place ontop of the stack.
     * @return Returns false if the stack is full, else true.
     */
    public Boolean push (Container container) throws Exception
    {
        if (_currentHeight == _maxHeight)
        {
            System.out.println("Exception in Container_Stack : " + this.toString() + " Can't push a container onto a full stack.");
            throw new Exception("Can't push a container onto a full stack.");
        }
        
        else if (container == null)
        {
            System.out.println("Exception in Container_Stack : '" + this.toString() + "' Container can't be null.");
            throw new Exception("Container can't be null.");
        }
        
        else
        {
            _containerArray[_currentHeight] = container;
            _currentHeight++;
            return true;
        }
    }
    @Override
    public String toString() {
        String returnString = "\n";
        for (int i = 0; i < _currentHeight; i++) {
            returnString += ((_containerArray[i] == null) ? "null" : _containerArray[i].getClass().getSimpleName()) + ",";
        }
        if (returnString.length() == 0){
            return this.getClass().getSimpleName() + " " + (_currentHeight) + "/" + _maxHeight + "\n[]\n" ;
        }
        else{
            return this.getClass().getSimpleName() + " " + (_currentHeight) + "/" + _maxHeight + "\n[" + returnString.substring(0, returnString.length() - 1) + "]\n" ;
        }
    }
}

