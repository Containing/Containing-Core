package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import java.sql.Date;

public class Truck extends Vehicle  {
    
    private Date arrivalDate;
    private Date departureDate;
    private Container container;
    private Node position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
    
    public Truck(Date arrivalDate, Date departureDate, Node startPosition)
    {
        this.position = startPosition;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    
    @Override
    public void setDestination(Node destination) {
        try {
            this.destination = destination;
            route = Pathfinding.Pathfinder.findShortest(position, destination);
        } 
        catch (Exception ex) {

        }
    }

    @Override
    public Node getDestination() {
        return (destination == null) ? position : destination;
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameTime gameTime) {
        if (position == destination){
            // send message
            // wait for message depart
        }
        else{
            // follow route 
            // update position
        } 
    }

    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        if (container != null){
           return container;
        }
        else{
            throw new Exception("Their is no container.");
        }
   }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception{
        if (container != null){
            throw new Exception("This vehicle can't carry more then one container.");
        }
        else{
            this.container = container;
        }
    }
    
}
