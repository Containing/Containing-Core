package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import java.util.ArrayList;

public class Train extends Vehicle{

    private Container[] containerList;
    private Vector3f position;
   
    @Override
    public void setDestination(Node destination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Node getDestination() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector3f getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameTime gameTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        if (containerList[(int)containerPos.x] != null){
            return containerList[(int)containerPos.x];
        }
        else{
            throw new Exception("Their is no container.");
        }
    }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        if (containerList[(int)containerPos.x] != null){
            containerList[(int)containerPos.x] = container;
        }
        else{
            throw new Exception("Their is no container.");
        }
    }
}