package Vehicles;

/**
 *
 * @author Tonnie Boersma
 * @param <E>
 */
public class Ostack<E> {

    private int size;
    private int pointer;
    private E[] itemArray;

    /**
     *
     * @param s The stack size
     */
    public Ostack(int s) {
        size = s > 0 ? s : 10;
        pointer = -1;
        itemArray = (E[]) new Object[size];
    }

    /**
     *
     * @param pushValue The Object to push
     * @throws Exception If you try to push when the max size is reached
     */
    public void push(E pushValue) throws Exception   {
        if (pointer == size - 1) {
            throw new Exception("Stack is full, connot push");
        } else {
            itemArray[++pointer] = pushValue;
        }
    }

    /**
     *
     * @return The top object and removes it from the stack
     * @throws Exception If you try to pop when their are no objects on the stack.
     */
    public E pop() throws Exception  {
        if (pointer == -1) {
            throw new Exception("Stack is empty, cannot pop");
        } else {
            E returnValue = itemArray[pointer];
            itemArray[pointer--] = null;
            return returnValue;
        }
    }

    /**
     *
     * @return The top object from the stack
     * @throws Exception If you try to pop when their are no objects on the stack.
     */
    public E peek() throws Exception   {
        if (pointer == -1) {
            throw new Exception("Stack is empty, cannot peek");
        } else {
            return itemArray[pointer];
        }
    }

    /**
     *
     * @return The amount of objects on the stack.
     */
    public int count() {
        return pointer + 1;
    }

    /**
     * Clears the stack.
     */
    public void clear() {
        pointer = -1;
        itemArray = (E[]) new Object[size];
    }
    
    @Override
    public String toString() {
        String returnString = "";
        for (int i = 0; i < pointer + 1; i++) {
            returnString += ((itemArray[i] == null) ? "null" : itemArray[i].getClass().getSimpleName()) + ",";
        }
        if (returnString.length() == 0){
            return this.getClass().getSimpleName() + " " + (pointer + 1) + "/" + size + "\n[]" ;
        }
        else{
            return this.getClass().getSimpleName() + " " + (pointer + 1) + "/" + size + "\n[" + returnString.substring(0, returnString.length() - 1) + "]" ;
        }
    }
}