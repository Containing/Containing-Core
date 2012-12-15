package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

/**
 * The abstract class for Vehicles
 * @author Tonnie Boersma
 */
public abstract class Vehicle {
    
    /**
     * Set the destination
     * @param destination The destination Node
     */
    public abstract void setDestination(Node destination);
    
    /**
     * Get the Destination
     * @return The destination node, returns position Node if their is no path.
     */
    public abstract Node getDestination();
    
    /**
     *
     * @return
     */
    public abstract Vector3f getPosition();
    
    /**
     *
     * @param gameTime
     */
    public abstract void update(GameTime gameTime);
}

