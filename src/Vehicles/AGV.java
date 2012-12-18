package vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;

public class AGV extends Vehicle {

    private Container container;
    private Node destination;
    private Vector3f position;
    private Node[] route;
    private final float SpeedWithContainer = 72;
    private final float SpeedWithoutContainer = 144;
    
    public AGV(Node startPosition){
        this.position = startPosition.getPosition();
    }
    
    @Override
    public void setDestination(Node destination) {
        try {
            route = Pathfinding.Pathfinder.findShortest(Pathfinder.findClosestNode(position), destination, container == null);
            this.destination = route[route.length-1];
        } 
        catch (Exception ex) {

        }
    }

    @Override
    public Node getDestination() {
        return (destination == null) ? Pathfinder.findClosestNode(position) : destination;
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float gameTime) {
        if (position == destination.getPosition()){
            // send message arrived
        }
        else{
            if (container == null){
                // follow route SpeedWithoutContainer
                // update position
            }
            else{
                // follow route SpeedWithContainer
                // update position
            }
        }   
    }

    public Container GetContainer() throws Exception {
        if (container != null){
           return container;
        }
        else{
            throw new Exception("Their is no container.");
        }
   }

    public void SetContainer(Container container) throws Exception{
        if (container != null){
            throw new Exception("This vehicle can't carry more then one container.");
        }
        else{
            this.container = container;
        }
    }
}

