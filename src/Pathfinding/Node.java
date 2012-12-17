package Pathfinding;

import Helpers.Vector3f;

/**
 *
 * @author EightOneGulf
 */
public class Node {
    private Vector3f position;
    
    /**
     * 
     * @param x
     * @param y
     */
    public Node(float x, float y, float z){
        position = new Vector3f(x, y, z);
    }
    
    public Node(float x, float y){
        position = new Vector3f(x, y, 0);
    }
    
    /**
     * 
     * @return
     */
    public Vector3f getPosition(){
        return position;
    }
}
