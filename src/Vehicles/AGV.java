package Vehicles;

import Helpers.*;
import Main.Container;
import Pathfinding.Node;
import Storage.Storage_Area;
import java.util.ArrayList;
import java.util.List;

public class AGV extends Vehicle implements IMessageReceiver {

    private final float SpeedWithContainer = 72;
    private final float SpeedWithoutContainer = 144;    
    private List<Message> assignments;
    
    public AGV(Node startPosition) throws Exception{
        if (startPosition == null){
            throw new Exception("\nThe input variable can't be null:"+
                    "\nstartPosition: " + startPosition);
        }
        else{
            this.position = startPosition.getPosition();
            this.destination = startPosition;
            storage = new Storage_Area(1, 1, 1, position);
        }
        assignments = new ArrayList();
    }

    @Override
    public void update(int gameTime) throws Exception {
        if (position == destination.getPosition()){
            // send message arrived
        }
        else{
            if (storage.Count() == 0){
                // follow route SpeedWithoutContainer
                // update position
            }
            else{
                // follow route SpeedWithContainer
                // update position
            }
        }   
        // When the AGV has assignments
        if(!Available()){
            // When the AGV need's the fetch a container
            if(assignments.get(0).Fetch()){
                // When the AGV has a container on him
                if(storage.Count() > 0){
                    Container.TransportType transportType = storage.peakContainer(0, 0).getDepartureTransportType();
                    switch(transportType){
                        case trein:
                            //destination = trein parking node
                            break;
                        case zeeschip:
                            //destination = zeeschip parking node
                            break;
                        case binnenschip:
                            //destination = binnenship parking node
                            break;
                        case vrachtauto :
                            //destination = vrachtauto parking node
                            break;
                    }
                    // Remove assingment because the container is fetched
                    assignments.remove(0);    
                    if(!assignments.isEmpty()){
                        destination = assignments.get(0).DestinationNode();
                    }
                }
            }
            // When the AGV need's to deliver a container
            else if(assignments.get(0).Deliver())
            {
                // When the AGV doesn't has a contianer on him
                if(storage.Count() == 0)
                {
                    // Remove assingment because the contianer is deliverd
                    assignments.remove(0); 
                    if(!assignments.isEmpty()){
                        destination = assignments.get(0).DestinationNode();
                    }
                }
            }
            else
            {
                // When the assignment is not is Deliver, Fetch
                throw new Exception("Wrong assignment AGV Can't Load or Unload");
            }
            // When there are no assignments left
            if(Available())
            {
                /**
                 * 
                 * TODO Send the AGV to the nearest parking lot
                 * 
                 */
            }
        }
        
    }
    
    /**
     * When there are no assignments for the AGV
     * @return 
     */
    @Override
    public boolean Available(){
        return assignments.isEmpty();
    }
    
    /**
     * Add's an assignment for the agv
     * @param mess 
     */
    @Override
    public void SendMessage(Message mess)
    {
        assignments.add(mess);
    }
}

