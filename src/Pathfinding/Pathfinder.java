package Pathfinding;

import Helpers.Vector3f;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author EightOneGulf
 */
public class Pathfinder {
    /**
     * 
     */
    public static Node[] Nodes; 
    /**
     * 
     */
    public static Path[] Paths;

    /**
     * 
     */
    public static void generateGrid(){
        int width = 10;
        int height = 10;
        int multiplier = -50;
        
        Nodes = new Node[width*height];
        Paths = new Path[351];
        
        int pathCounter = 0;
        for(int j = 0 ; j < height; j++){
            for(int i = 0 ; i < width; i++){
                Nodes[i + j*width] = new Node(i*multiplier,j*multiplier);
                
                //Connect with previous nodes
                if(i>0){
                    Paths[pathCounter]=new Path(Nodes[i + j*width], Nodes[i + j*width -1]);
                    pathCounter++;
                }
                if(j>0){
                    Paths[pathCounter]=new Path(Nodes[i + j*width], Nodes[i + (j-1)*width ]);
                    pathCounter++;
                }
                if(j>0 && i>0){
                    Paths[pathCounter]=new Path(Nodes[i + j*width], Nodes[i + (j-1)*width -1 ]);
                    pathCounter++;
                }
                if(j>0 && i<width){
                    Paths[pathCounter]=new Path(Nodes[i + j*width], Nodes[i + (j-1)*width +1 ]);
                    pathCounter++;
                }
            }
        }
    }
    
    public static void generateArea(){
        
    }
    
    /**
     * 
     */
    public static void generatePaths(){
        Nodes = new Node[12];
        Paths = new Path[13];
        
        Nodes[0] = new Node(0, 0);
        
        Nodes[1] = new Node(2,2);
        Nodes[2] = new Node(2,3);
        Nodes[3] = new Node(4,3);
        Nodes[4] = new Node(3,3.5f);
        Nodes[5] = new Node(1,4);
        Nodes[6] = new Node(0,5);
        
        Nodes[7] = new Node(0,1);
        Nodes[8] = new Node(1,2);
        Nodes[9] = new Node(0,3);
        Nodes[10] = new Node(1,4);
        
        Nodes[11] = new Node(-4,3);
        
        

        Paths[0] = new Path(Nodes[0], Nodes[1]);
        Paths[1] = new Path(Nodes[1], Nodes[2]);
        Paths[2] = new Path(Nodes[2], Nodes[3]);
        Paths[3] = new Path(Nodes[3], Nodes[4]);
        Paths[4] = new Path(Nodes[4], Nodes[5]);
        Paths[5] = new Path(Nodes[5], Nodes[6]);
        
        Paths[6] = new Path(Nodes[0], Nodes[7]);
        Paths[7] = new Path(Nodes[7], Nodes[8]);
        Paths[8] = new Path(Nodes[8], Nodes[9]);
        Paths[9] = new Path(Nodes[9], Nodes[10]);
        Paths[10] = new Path(Nodes[10], Nodes[6]);
        
        Paths[11] = new Path(Nodes[0], Nodes[11]);
        Paths[12] = new Path(Nodes[11], Nodes[6]);
    }
    

    public static Node findClosestNode(Vector3f position){
        NodeScore[] nscore = new NodeScore[Nodes.length];
        for(int i = 0 ; i < Nodes.length ; i++){
            nscore[i] = new NodeScore(Nodes[i], Vector3f.distance(Nodes[i].getPosition(), position) , null);
        }
        
        float lowestScore = Float.MAX_VALUE;
        int index = 0;
        
        for(int i = 0 ; i < nscore.length ; i++){
            if(nscore[i].score<lowestScore){
                lowestScore = nscore[i].score;
                index = i;
            }
        }

        return nscore[index].node;
    }

    /**
     * 
     * @param startNode     The node at the current position
     * @param endNode       The node at the destination
     * @return              An array of all nodes on the route found.
     */
    public static Node[] findShortest(Node startNode, Node endNode) throws Exception{
        return findShortest(startNode, endNode, false);
    }

    /** 
     * 
     * @param startNode     The node at the current position
     * @param endNode       The node at the destination
     * @param cargoFilled   If true, the vehicle carries a container. When false, the vehicle is empty. Certain routes only allow full or empty.
     * @return              An array of all nodes on the route found.
     */
    public static Node[] findShortest(Node startNode, Node endNode, boolean cargoFilled) throws Exception{
        if(startNode==null||endNode==null)throw new Exception("Can't use empty nodes");
        if(!nodesContains(startNode))throw new Exception("Given startNode does not exist within pathfinder");
        if(!nodesContains(endNode))throw new Exception("Given endNode does not exist within pathfinder");
        
        
        ArrayList<Node> closedSet = new ArrayList();          //All nodes wich have already been processed. Don't process again
        ArrayList<NodeScore> openSet = new ArrayList();  //All nodes present for further process.

        openSet.add( new NodeScore(startNode,0, null) );            //Begin at startNode
        
        while(openSet.size() > 0){                  // Keep on processing till no more nodes are available
            Collections.sort(openSet);              //Sort on score, lowest score first
            
            NodeScore current = openSet.get(0);     //Fetch and remove first from openset..
            openSet.remove(current);
            closedSet.add(current.node);            //..and add to closed set, to prevent further processing

            if(current.node.equals(endNode)){      //Current node is endnode, route found 
                //Found him                
                
                //////Build route
                ArrayList<Node> route = new ArrayList();
                route.add(current.node);
                NodeScore parent = current.parent;
                while(parent!=null){
                    route.add(parent.node);
                    parent = parent.parent;
                }
                
                //////Convert ArrayList to array
                Node[] finalroute = new Node[route.size()];
                for(int i = 0 ; i<route.size() ; i++){
                    finalroute[i] = route.get(route.size()-1-i);
                }
                return finalroute;
            }
            
            
            for(NodeScore n : findNeighbourNodes(current, cargoFilled)){ //Loop trough all neighbours of current node
                if(!closedSet.contains(n.node) && !nodeScoreContains(openSet, n.node)){    //if neighbour hasent been processed yet
                    n.score+=current.score; 
                    n.parent = current;     //Set parent for backtracking
                    openSet.add(n);         //Set neighbour for further processing
                }
            }
        }

        return null;
    }

    private static boolean nodeScoreContains(ArrayList<NodeScore> openSet, Node node){
        for(NodeScore ns : openSet){
            if(ns.node.equals(node))return true;
        }
        return false;
    }

    private static ArrayList<NodeScore> findNeighbourNodes(NodeScore node, boolean cargoFilled){
        return findNeighbourNodes(node.node, cargoFilled);
    }

    private static ArrayList<NodeScore> findNeighbourNodes(Node node, boolean cargoFilled){  //Find all nodes connected to given node (via paths)
        ArrayList<Path> paths = findNeighbourPaths(node, cargoFilled);
        ArrayList<NodeScore> nodes = new ArrayList();

        for(Path p : paths){
            NodeScore n = new NodeScore( (p.getPointA().equals(node)?p.getPointB():p.getPointA()), p.getLength(), null);
            nodes.add(n);
        }
        return nodes;
    }

    private static ArrayList<Path> findNeighbourPaths(Node node, boolean cargoFilled){   //Find all paths connected to given node
        ArrayList<Path> neighbourPaths = new ArrayList();
        for(Path p : Paths){
            if(p.getPointA().equals(node) || p.getPointB().equals(node)){
                if((cargoFilled && p.FilterCargo==2) || (!cargoFilled && p.FilterCargo==1)){
                    //Filter on cargo
                }else
                if(p.OneWay && p.getPointA().equals(node)){
                    //One way path, don't add
                }else{
                    neighbourPaths.add(p);
                }
            }
        }
        return neighbourPaths;
    }
 
    private static boolean nodesContains(Node i){
        for(Node n : Nodes){
            if(n.equals(i))return true;
        }
        return false;
    }
}