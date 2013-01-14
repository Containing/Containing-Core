package Network;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import org.zeromq.*;

/**
 *
 * @author EightOneGulf
 */
public class objPublisher {
    private ZMQ.Socket publisher;
    private ZMQ.Context context;
    public objPublisher(){
        //  Prepare our context and socket
        context = ZMQ.context(1);
        publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:6001");        
    }
    
    public void syncVehicle(Vehicles.TransportVehicle vehicle){
        //33Byte total
        byte[] b = new byte[29];
        
        Helpers.Vector3f pos = vehicle.getPosition();
        Helpers.Vector3f des = vehicle.getDestination().getPosition();
        int vehicleID = 0;
        
        
        b[0] = 1;       //Operation ID
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(vehicleID), b, 1);
        
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(pos.x), b, 5);
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(pos.y), b, 9);
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(pos.z), b, 13);

        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(des.x), b, 17);
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(des.y), b, 21);
        Helpers.byteHelper.addToArray(Helpers.byteHelper.toByta(des.z), b, 25);

        publisher.send(b, 0);
    }
}
