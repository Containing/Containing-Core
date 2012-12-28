package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import Storage.Storage_Area;
import java.util.Date;

public class Truck extends Vehicle  {
    
    private Date arrivalDate;
    private Date departureDate;
    private String arrivalCompany;
    public Storage_Area storage;
    private Vector3f position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
    
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
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
            this.arrivalCompany = arrivalCompany;
            storage = new Storage_Area(1, 1, 1, position);
        }
    }
    
    @Override
    public void setDestination(Node destination) {
        try {
            this.destination = destination;
            route = Pathfinding.Pathfinder.findShortest(Pathfinder.findClosestNode(position), destination);
        } 
        catch (Exception ex) {

        }
    }

    @Override
    public Node getDestination() {
        return (destination == null) ? Pathfinder.findClosestNode(position) : destination;
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float gameTime) throws Exception {
        if (position == destination.getPosition()){
            // send message
            // wait for message depart
        }
        else{
            // follow route 
            // update position
        } 
    }
    
    public Date GetArrivalDate(){
        return arrivalDate;
    }
    
    public Date GetDepartureDate(){
        return departureDate;
    }
    
    public String GetCompany(){
        return arrivalCompany;
    }
}
