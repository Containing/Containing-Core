package Network;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import org.zeromq.*;

/**
 * Example statspublisher
 * @author Christiaan
 */
public class StatsPublisher {
    public static void main(String args[]) throws Exception {
        
        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);

        System.out.println("Publisher");
        publisher.bind("tcp://*:6000");
        
        Random rnd = new Random();
        for(;;) {
            StatsMessage msg = new StatsMessage();
            msg.containers_incoming = rnd.nextInt(50000);
            msg.containers_outgoing = rnd.nextInt(50000);
            String[] areas = {"Area 1", "Area 2", "Area 3", "Area 4", "Area 5",
                              "Area 6", "Area 7", "Area 8", "Area 9", "Area 10"};
            for(int k = 0; k < areas.length; k++) {
                msg.areas.put(areas[k], rnd.nextInt(50));
            }
            
            // Serialize message
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msg);
            oos.close();
            
            // Send message
            publisher.send(baos.toByteArray(), 0);
            
            //publisher.send(msg.toString());
            //System.out.println(msg.toString());
            //System.out.println(new String(baos.toByteArray()));
            
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
