package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import Storage.Storage_Area;
import java.util.Date;

public class Train extends TransportVehicle{
   
    public Train(Date arrivalDate, Date departureDate, String arrivalCompany, int trainLenght, Node startPosition) throws Exception
    {
        if (arrivalDate == null || departureDate == null || arrivalCompany == null || trainLenght == 0 || startPosition == null){
            throw new Exception("\nThe input variable can't be null:"+
                    "\narrivalDate: " + arrivalDate +
                    "\ndepartureDate: " + departureDate +
                    "\narrivalCompany: " + arrivalCompany +
                    "\ntrainLenght: " + trainLenght +
                    "\nstartPosition: " + startPosition);
        }
        else{
            this.position = startPosition.getPosition();
            this.destination = startPosition;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
            this.arrivalCompany = arrivalCompany;
            storage = new Storage_Area(trainLenght, 1, 1, position);
        }
    }   
}
