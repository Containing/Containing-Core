package Pathfinding;

import Helpers.Vector2;

/**
 *
 * @author EightOneGulf
 */
public class Node {
    private Vector2 position;
    
    /**
     * 
     * @param x
     * @param y
     */
    public Node(float x, float y){
        position = new Vector2(x, y);
    }
    
    /**
     * 
     * @return
     */
    public Vector2 getPosition(){
        return position;
    }
}
