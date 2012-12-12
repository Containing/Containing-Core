package vehicles;

import Main.Container;
import Main.Vector3f;

public abstract class Vehicle {
    
    public abstract void setDestination(Vector3f destination);
    
    public abstract Vector3f getDestination();
    
    public abstract Vector3f getPosition();
    
    public abstract void update(GameTime gameTime);
    
    public abstract Container GetContainer(Vector3f containerPos) throws Exception;
    
    public abstract void SetContainer(Container container, Vector3f containerPos) throws Exception;
}
