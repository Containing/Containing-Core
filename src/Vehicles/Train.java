package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import java.sql.Date;

public class Train extends Vehicle{

    private Date arrivalDate;
    private Date departureDate;
    private Container[] containerList;
    private Node position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
   
    public Train(Date arrivalDate, Date departureDate, int trainLenght, Node startPosition)
    {
        this.position = startPosition;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        containerList = new Container[trainLenght];
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
        int x = (int)containerPos.x;

        if (containerList.length > x && x > 0){
            if (containerList[x] != null){
                return containerList[x];
            }
            else{
                throw new Exception("Their is no container.");
            }
        }
        else{
            throw new Exception("The index needs to be between 0 and " + containerList.length + 
                                ".\n Used index: " + x);
        }
    }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        int x = (int)containerPos.x;

        if (containerList.length > x && x > 0){
            if (containerList[x] == null){
                containerList[x] = container;
            }
            else{
                throw new Exception("Their is allready a container.");
            }
        }
        else{
            throw new Exception("The index needs to be between 0 and " + containerList.length + 
                                ".\n Used index: " + x);
        }
    }
}
