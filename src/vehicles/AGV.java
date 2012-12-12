package vehicles;

import Main.Container;
import Main.Vector3f;

public class AGV extends Vehicle {

    private Container container;
    private Vector3f destination;
    private Vector3f position;
    //private Texture texture;

    @Override
    public void setDestination(Vector3f destination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector3f getDestination() {
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
