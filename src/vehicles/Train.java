package vehicles;

import Main.Container;
import Main.Vector3f;
import java.util.ArrayList;

public class Train extends Vehicle{

    private ArrayList<Container> containerList;
    //private path
    private Vector3f position;
    private final int speedWithContainer = 20;
    private final int speedWithoutContainer = 40;
    
    
    @Override
    public void setDestination(Vector3f destination) {
        // request getPath(position, destionation);
    }

    @Override
    public Vector3f getDestination() {
        //return path.end();
        return null;
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public void update(GameTime gameTime) {
        // move to next node
    }

    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        if (containerList.get((int)containerPos.x) != null){
            return containerList.get((int)containerPos.x);
        }
        else{
            throw new Exception("Their is no container.");
        }
    }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        if (containerList.get((int)containerPos.x) != null){
            containerList.set((int)containerPos.x, container);
        }
        else{
            throw new Exception("Their is no container.");
        }
    }

    
}
