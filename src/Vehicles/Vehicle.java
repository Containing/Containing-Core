package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public abstract class Vehicle {
    
    public abstract void setDestination(Node destination);
    
    public abstract Node getDestination();
    
    public abstract Vector3f getPosition();
    
    public abstract void update(GameTime gameTime);
    
    public abstract Container GetContainer(Vector3f containerPos) throws Exception;
    
    public abstract void SetContainer(Container container, Vector3f containerPos) throws Exception;
}

