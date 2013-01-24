package Vehicles;

import Crane.Crane;
import Crane.StorageCrane;
import Helpers.Vector3f;
import Parkinglot.Parkinglot;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import Storage.Storage_Area;

/**
 * The abstract class for Vehicles
 * @author Tonnie Boersma
 */
public abstract class Vehicle {
    /**
     * The types a vehicle can be
     */
    public enum VehicleType {truck, train, seaBoat, inlandBoat, AGV};
    /**
     * The storage of this vehicle
     */
    public Storage_Area storage;
    /**
     * The destination node for this vehicle
     */
    protected Parkinglot destination;
    /**
     * The position of this vehicle
     */
    protected Vector3f position;
    /**
     * The rotation of this vehicle
     */
    protected float rotation;
    /**
     * The route this vehcile needs to follow
     */
    protected Node[] route;
    /**
     * The index in the node array route
     */
    protected int routeIndex;
    /**
     * The type of the vehicle
     */
    protected VehicleType vehicleType;    
    /**
     * The Id of the vehicle in the harbor
     */
    public int Id;
    
    protected boolean parked = false;
    
    /**
     * Gets the destination node of this vehicle.
     * @return The destination node, returns position Node if their is no path.
     */
    public Node getDestination() {
        return destination.node;
    }
    
    /**
     * Gets the position of this vehicle
     * @return The currentPosition.
     */
    public Vector3f getPosition() {
        return position;
    }
   
    /**
     * Updates the movement of the vehicle
     * @param gameTime The elapsed gameTime.
     */
    public abstract void update(float gameTime) throws Exception;
    
    /**
     * Gets the current type of this vehcile
     * @return The vehicle type
     */
    public VehicleType GetVehicleType(){
        return vehicleType;
    }
}

