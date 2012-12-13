package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public class Train extends Vehicle{

    private Container[] containerList;
    private Vector3f position;
   
    public Train(/*position,*/ int Containers)
    {
        containerList = new Container[Containers];
    }
    
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
