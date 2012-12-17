package vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import java.sql.Date;

public class Truck extends Vehicle  {
    
    private Date arrivalDate;
    private Date departureDate;
    private Container container;
    private Vector3f position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
    
    public Truck(Date arrivalDate, Date departureDate, Node startPosition)
    {
        this.position = startPosition.getPosition();
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
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
    public void update(float gameTime) {
        if (position == destination.getPosition()){
            // send message
            // wait for message depart
        }
        else{
            // follow route 
            // update position
        } 
    }

    public Container GetContainer() throws Exception {
        if (container != null){
           return container;
        }
        else{
            throw new Exception("Their is no container.");
        }
   }

    public void SetContainer(Container container) throws Exception{
        if (container != null){
            throw new Exception("This vehicle can't carry more then one container.");
        }
        else{
            this.container = container;
        }
    }
    
}
