package Pathfinding;

import Helpers.Vector3f;

/**
 *
 * @author EightOneGulf
 */
public class Node {
    private Vector3f position;
    
    public Node(){
        position = new Vector3f(0, 0, 0);
    }
    
    /**
     * 
     * @param x
     * @param y
     */
    public Node(float x, float y, float z){
        position = new Vector3f(x, y, z);
    }
    
    public Node(float x, float z){
        position = new Vector3f(x, 0, z);
    }
    
    /**
     * 
     * @return
     */
    public Vector3f getPosition(){
        return position;
    }
}
