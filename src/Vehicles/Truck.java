package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public class Truck extends Vehicle  {
    
    private Container container;
    private Node destination;
    private Node position;
    
    public Truck(Node startPosition)
    {
        this.position = startPosition;
    }
    
    @Override
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    @Override
    public Node getDestination() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameTime gameTime) {
        
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
