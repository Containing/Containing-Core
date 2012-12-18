package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Pathfinding.Pathfinder;
import java.sql.Date;

public class Train extends Vehicle{

    private Date arrivalDate;
    private Date departureDate;
    private Container[] containerList;
    private Vector3f position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
   
    public Train(Date arrivalDate, Date departureDate, int trainLenght, Node startPosition)
    {
        this.position = startPosition.getPosition();
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        containerList = new Container[trainLenght];
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
    
    public Container GetContainer(int index) throws Exception {
        if (containerList.length > index && index > 0){
            if (containerList[index] != null){
                return containerList[index];
            }
            else{
                throw new Exception("Their is no container.");
            }
        }
        else{
            throw new Exception("The index needs to be between 0 and " + containerList.length + 
                                ".\n Used index: " + index);
        }
    }

    public void SetContainer(Container container, int index) throws Exception {
        if (containerList.length > index && index > 0){
            if (containerList[index] == null){
                containerList[index] = container;
            }
            else{
                throw new Exception("Their is allready a container.");
            }
        }
        else{
            throw new Exception("The index needs to be between 0 and " + containerList.length + 
                                ".\n Used index: " + index);
        }
    }
}
