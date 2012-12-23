package Helpers;
/**
 *
 * @author EightOneGulf
 */

public class Vector2f {
    public float x, y;
    
    /**
     * 
     * @param x
     * @param yz
     */
    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * 
     * @param o Another vector
     * @return The distance between the 2 vectors
     */
    public float distance(Vector2f o){
        return distance(this, o);
    }
    
    /**
     * 
     * @param v1 Primary vector
     * @param v2 Secondary vector
     * @return Distance between the given vectors
     */
    public static float distance(Vector2f v1, Vector2f v2){
        float distX = v1.x - v2.x;
        float distY = v1.y - v2.y;
        if(distX<0)distX*=-1;
        if(distY<0)distY*=-1;
        return (float)Math.sqrt( Math.pow(distX, 2) + Math.pow(distY, 2)  );  
    }
}
