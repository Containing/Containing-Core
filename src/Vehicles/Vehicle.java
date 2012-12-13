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
    
    /**
     *
     * @param containerPos
     * @return
     * @throws Exception
     */
    public abstract Container GetContainer(Vector3f containerPos) throws Exception;
    
    /**
     * Sets the container on a certain position.
     * @param container The container to place
     * @param containerPos The position to place the container
     * @throws Exception If their allready is a container on the spot, or if the spot doesn't exist.
     */
    public abstract void SetContainer(Container container, Vector3f containerPos) throws Exception;
}

