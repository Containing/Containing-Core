package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import Storage.Storage_Area;
import java.util.Date;

public class Truck extends TransportVehicle  {
    
    public Truck(Date arrivalDate, Date departureDate, String arrivalCompany, Node startPosition) throws Exception
    {
        if (arrivalDate == null || departureDate == null || arrivalCompany == null || startPosition == null){
            throw new Exception("\nThe input variable can't be null:"+
                    "\narrivalDate: " + arrivalDate +
                    "\ndepartureDate: " + departureDate +
                    "\narrivalCompany: " + arrivalCompany +
                    "\nstartPosition: " + startPosition);
        }
        else{
            this.position = startPosition.getPosition();
            this.destination = startPosition;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
            this.arrivalCompany = arrivalCompany;
            storage = new Storage_Area(1, 1, 1, position);
        }
    }
}
