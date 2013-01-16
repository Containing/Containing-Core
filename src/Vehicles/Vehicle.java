package Vehicles;

import Helpers.Vector3f;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import Storage.Storage_Area;

/**
 * The abstract class for Vehicles
 * @author Tonnie Boersma
 */
public abstract class Vehicle {
    public enum VehicleType {truck, train, seaBoat, inlandBoat, AGV};
    public Storage_Area storage;
    protected Node destination;
    protected Vector3f position;
    protected float rotation;
    protected Node[] route;
    protected int routeIndex;
    protected VehicleType vehicleType;
    public int Id;
    /**
     * Set the destination.
     * @param destination The destination Node.
     */
    public void setDestination(Node destination) throws Exception{
        this.destination = destination;
        this.route = Pathfinding.Pathfinder.findShortest(Pathfinder.findClosestNode(position), destination, storage.Count() == 0);
        this.routeIndex = 1;
    }
    
    /**
     * Get the Destination.
     * @return The destination node, returns position Node if their is no path.
     */
    public Node getDestination() {
        return destination;
    }
    
    /**
     * Get the currentPosition.
     * @return currentPosition.
     */
    public Vector3f getPosition() {
        return position;
    }
   
    /**
     * Update the vehicle
     * @param gameTime The gameTime.
     */
    public abstract void update(float gameTime) throws Exception;
    
    public Storage_Area GetStorage() {
        return storage;
    }
    
    public void SetStorage(Storage_Area sa) {
        storage = sa;
    }
    
    public VehicleType GetVehicleType(){
        return vehicleType;
    }
}

