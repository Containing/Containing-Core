package Vehicles;

import Main.Container;
import java.util.Date;

/**
 * An extension of the vehicle class.
 * @author Tonnie Boersma
 */
public abstract class TransportVehicle extends Vehicle {
    
    /**
     * Get the arrival date.
     * @return arrivalDate.
     */
    public abstract Date GetArrivalDate();

    /**
     * Get the departure date.
     * @return departureDate.
     */
    public abstract Date GetDepartureDate();
    
    /**
     * Get the Company.
     * @return Company.
     */
    public abstract String GetCompany();
    
    /**
     * Matches the container properties with the vehicle properties,
     * @param container The container to match.
     * @return True if the container matches the vehicle, false otherwise.
     */
    public abstract boolean MatchesContainer(Container container);
}
