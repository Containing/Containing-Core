package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public class AGV extends Vehicle {

    private Container container;
    private Node destination;
    private Node position;
    private Node[] route;
    private final float SpeedWithContainer = 72;
    private final float SpeedWithoutContainer = 144;
    
    @Override
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    @Override
    public Node getDestination() {
        if (route.length == 0 || route == null){
            return null;
        }
        else{
            return route[route.length-1];
        }
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameTime gameTime) {
        if (position == destination){
            // wait for message
        }
        else if (route.length == 0 || route == null)
        {
            try {
                route = Pathfinding.Pathfinder.findShortest(position, destination, container == null);
            } 
            catch (Exception ex) {
                
            }
        }
        else{
            // follow route
        }
            
    }

    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        if (container != null){
           return container;
        }
        else{
            throw new Exception("Their is no container.");
        }
   }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception{
        if (container != null){
            throw new Exception("This vehicle can't carry more then one container.");
        }
        else{
            this.container = container;
        }
    }
}
