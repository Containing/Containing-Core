package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Vehicles.Ostack;
import java.util.Date;

public class Boat extends Vehicle {
    
    private Date arrivalDate;
    private Date departureDate;
    public Ostack[][] containerField;
    private Vector3f size;
    private Node position;
    private Node destination;
    private Node[] route;
    private float speed/*= X*/;
    

    
    public Boat(Date arrivalDate, Date departureDate, Vector3f containerArraySize, Node startPosition)
    {
        this.position = startPosition;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.size = containerArraySize;
        
        
        this.containerField = new Ostack[(int)size.z][(int)size.x];
        
        for (int z = 0; z < size.z; z++) {
            for (int x = 0; x < size.x; x++) {
                this.containerField[z][x] = new Ostack<>((int)size.y);
            }
        }
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
    
    public Container GetBestContainer(int rowIndex) throws Exception {
        if (0 > rowIndex || rowIndex > size.x){
            throw new Exception("Row " + rowIndex + " doesn't exist on this ship.");
        }
        else{
            Date max = null;
            
            for (int z = 0; z < size.x; z++) {
                if (containerField[rowIndex][z].count() > 0){
                    Date now = ((Container)containerField[rowIndex][z].peek()).getDepartureDateStart();
                    
                    if (z == 0){
                        max = now;
                    }
                    else if (max.before(now)){
                        max = now;
                    }
                }
            }
            for (int z = 0; z < size.x; z++) {
                if (containerField[rowIndex][z].count() > 0){
                    if(max == ((Container)containerField[rowIndex][z].peek()).getDepartureDateStart()){
                        return (Container)containerField[rowIndex][z].pop();
                    }
                }
            }
            if (max == null){
                throw new Exception("Their are no containers in the row");
            }
        }
        return null;
    }

    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        int x = (int)containerPos.x;
        int y = (int)containerPos.y;
        int z = (int)containerPos.z;
        
        // <editor-fold defaultstate="collapsed" desc="generate Exception">
        String ExceptionString = "";
        if (0 > x || x > size.x){
            ExceptionString += ("\nThe X index needs to be between 0 and " + size.x + 
                                ".\nUsed index: " + x);
        }
        if (0 > y || y > size.y){
            ExceptionString += ("\nThe Y index needs to be between 0 and " + size.y + 
                                ".\nUsed index: " + y);
        }
        if (0 > z || z > size.z){
            ExceptionString += ("\nThe Z index needs to be between 0 and " + size.z + 
                                ".\nUsed index: " + z);
        }
        // </editor-fold>
        
        if (ExceptionString.length() == 0){
            if (containerField[z][x].HasHole(y)){
                containerField[z][x].SetStackItem(container, y);
            }
            else{
                throw new Exception("Their is allready a container.");
            }
        }
        else{
            throw new Exception(ExceptionString);
        }
    }
}
