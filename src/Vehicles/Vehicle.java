package Vehicles;

import Helpers.Vector3f;
import Pathfinding.Node;
import Storage.Storage_Area;

/**
 * The abstract class for Vehicles
 * @author Tonnie Boersma
 */
public abstract class Vehicle {
    
    /**
     * Set the destination.
     * @param destination The destination Node.
     */
    public abstract void setDestination(Node destination);
    
    /**
     * Get the Destination.
     * @return The destination node, returns position Node if their is no path.
     */
    public abstract Node getDestination();
    
    /**
     * Get the currentPosition.
     * @return currentPosition.
     */
    public abstract Vector3f getPosition();
    
    /**
     * Update the vehicle
     * @param gameTime The gameTime.
     */
    public abstract void update(int gameTime) throws Exception;
    
    public abstract Storage_Area GetStorage() throws Exception;
    
    public abstract void SetStorage(Storage_Area sa) throws Exception;
}

