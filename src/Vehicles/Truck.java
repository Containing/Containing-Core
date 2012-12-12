package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public class Truck extends Vehicle  {
    
    Container container;

    @Override
    public void setDestination(Node destination) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
