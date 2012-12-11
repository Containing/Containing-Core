package Pathfinding;

import java.util.Comparator;

/**
 *
 * @author EightOneGulf
 */
public class NodeScore implements Comparable<NodeScore>{
    /**
     * 
     */
    public Node node;           
    /**
     * 
     */
    public float score;         //Total score of node in path
    /**
     * 
     */
    public NodeScore parent;    //previous node, for backtracking
    
    /**
     * 
     * @param node      
     * @param score     
     * @param parent    
     */
    public NodeScore(Node node, float score, NodeScore parent){
        this.node = node;
        this.score = score;
        this.parent = parent;
    }

    @Override
    public int compareTo(NodeScore o) {
        if(this.score < ((NodeScore)o).score) return -1;
        if(this.score > ((NodeScore)o).score) return 1;
        return 0;
    }
}