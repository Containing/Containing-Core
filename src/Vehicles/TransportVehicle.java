package Vehicles;

import Main.Container;
import java.util.Date;

/**
 *
 * @author Tonnie Boersma
 */
public abstract class TransportVehicle extends Vehicle {
    
    public abstract Date GetArrivalDate();

    public abstract Date GetDepartureDate();
    
    public abstract String GetCompany();
    
    public abstract boolean MatchesContainer(Container container);
}
