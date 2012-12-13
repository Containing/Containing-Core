package vehicles;

import Helpers.GameTime;
import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;

public class Boat extends Vehicle {
    private Container[][][] containerList;

    public Boat(Vector3f containerArraySize)
    {
        containerList = new Container[(int)containerArraySize.x][(int)containerArraySize.y][(int)containerArraySize.z];
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Container GetContainer(Vector3f containerPos) throws Exception {
        int x = (int)containerPos.x;
        int y = (int)containerPos.y;
        int z = (int)containerPos.z;
        
        int clx = containerList.length;
        int cly = containerList[0].length;
        int clz = containerList[0][0].length;
        
        
        if (0 > x || x > clx){
            throw new Exception("The X index needs to be between 0 and " + clx + 
                                ".\n Used index: " + x);
        }
        else if (0 > y || y > cly){
            throw new Exception("The Y index needs to be between 0 and " + cly + 
                                ".\n Used index: " + y);
        }
        else if (0 > y || y > cly){
            throw new Exception("The Z index needs to be between 0 and " + clz + 
                                ".\n Used index: " + z);
        }
        else{
            
            if (containerList[x][y][z] != null){
                return containerList[x][y][z];
            }
            else{
                throw new Exception("Their is no container.");
            }
        }
    }

    @Override
    public void SetContainer(Container container, Vector3f containerPos) throws Exception {
        int x = (int)containerPos.x;
        int y = (int)containerPos.y;
        int z = (int)containerPos.z;
        
        int clx = containerList.length;
        int cly = containerList[0].length;
        int clz = containerList[0][0].length;
        
        
        if (0 > x || x > clx){
            throw new Exception("The X index needs to be between 0 and " + clx + 
                                ".\n Used index: " + x);
        }
        else if (0 > y || y > cly){
            throw new Exception("The Y index needs to be between 0 and " + cly + 
                                ".\n Used index: " + y);
        }
        else if (0 > y || y > cly){
            throw new Exception("The Z index needs to be between 0 and " + clz + 
                                ".\n Used index: " + z);
        }
        else{
            if (containerList[x][y][z] == null){
                containerList[x][y][z] = container;
            }
            else{
                throw new Exception("Their is allready a container.");
            }
        }
    }
}
