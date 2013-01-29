
package Pathfinding;

/**
 *
 * @author EightOneGulf
 */
public class Path {
    private Node EndPointA;
    private Node EndPointB;
    private float Length;

    boolean OneWay = false;
    byte FilterCargo = 0;   //0 no filter
                            //1 block empty cargo
                            //2 block full cargo

    /**
     * 
     * @param EndPointA
     * @param EndPointB
     */    
    public Path(Node EndPointA, Node EndPointB){
        this.EndPointA = EndPointA;
        this.EndPointB = EndPointB;
        float difX = EndPointA.getPosition().x - EndPointB.getPosition().x;
        float difY = EndPointA.getPosition().y - EndPointB.getPosition().y;
        if(difX<0)difX*=-1;
        if(difY<0)difY*=-1;
        this.Length = (float)Math.sqrt( Math.pow(difX, 2) + Math.pow(difY, 2) );        //Calculate distance of path, distance between nodes
    }
    public Path(Node EndPointA, Node EndPointB, int Cargo){
        this.EndPointA = EndPointA;
        this.EndPointB = EndPointB;
        float difX = EndPointA.getPosition().x - EndPointB.getPosition().x;
        float difY = EndPointA.getPosition().y - EndPointB.getPosition().y;
        if(difX<0)difX*=-1;
        if(difY<0)difY*=-1;
        this.Length = (float)Math.sqrt( Math.pow(difX, 2) + Math.pow(difY, 2) );        //Calculate distance of path, distance between nodes
        this.FilterCargo = (byte)Cargo;
    }
    public Path(Node EndPointA, Node EndPointB, boolean OneWay){
        this.EndPointA = EndPointA;
        this.EndPointB = EndPointB;
        float difX = EndPointA.getPosition().x - EndPointB.getPosition().x;
        float difY = EndPointA.getPosition().y - EndPointB.getPosition().y;
        if(difX<0)difX*=-1;
        if(difY<0)difY*=-1;
        this.Length = (float)Math.sqrt( Math.pow(difX, 2) + Math.pow(difY, 2) );        //Calculate distance of path, distance between nodes
        this.OneWay = OneWay;
    }
    public Path(Node EndPointA, Node EndPointB, boolean OneWay, int Cargo){
        this.EndPointA = EndPointA;
        this.EndPointB = EndPointB;
        float difX = EndPointA.getPosition().x - EndPointB.getPosition().x;
        float difY = EndPointA.getPosition().y - EndPointB.getPosition().y;
        if(difX<0)difX*=-1;
        if(difY<0)difY*=-1;
        this.Length = (float)Math.sqrt( Math.pow(difX, 2) + Math.pow(difY, 2) );        //Calculate distance of path, distance between nodes
        this.OneWay = OneWay;
        this.FilterCargo = (byte)Cargo;
    }
    /**
     * 
     * @return Node A
     */
    public Node getPointA(){
        return EndPointA;
    }
    /**
     * 
     * @return Node B
     */
    public Node getPointB(){
        return EndPointB;
    }
    /**
     * 
     * @return Total length between Node A and Node B
     */
    public float getLength(){
        return Length;
    }
}
