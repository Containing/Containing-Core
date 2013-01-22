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
    private ZMQ.Socket publisher;
    
    /**
     * Constructor
     */
    public StatsPublisher() {
        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:6000");
    }
    
    /**
     * Serializes a StatsMessage and publishes it
     * @param msg
     * @throws Exception 
     */
    public void SendStatsMessage(StatsMessage msg) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(msg);
        oos.close();
        publisher.send(baos.toByteArray(), 0);
    }
    
    /**
     * Test server that publishes dummy data. Use it to test the Android client.
     * @param args
     * @throws Exception 
     */
    public static void main(String args[]) throws Exception {
        
        StatsPublisher publisher = new StatsPublisher();
        
        Random rnd = new Random();
        for(;;) {
            StatsMessage msg = new StatsMessage();
            msg.containers_incoming = rnd.nextInt(50000);
            msg.containers_outgoing = rnd.nextInt(50000);
            String[] areas = {"Area 1", "Area 2", "Area 3", "Area 4", "Area 5",
                              "Area 6", "Area 7", "Area 8", "Area 9", "Area 10",
                              "Area 11", "Area 12", "Area 13", "Area 14", "Area 15",
                              "Area 16", "Area 17", "Area 18", "Area 19", "Area 20"};
            for(int k = 0; k < areas.length; k++) {
                msg.areas.put(areas[k], rnd.nextInt(50));
            }
            
            msg.vehicles.put("AGV", rnd.nextInt(20));
            msg.vehicles.put("SHIP", rnd.nextInt(20));
            msg.vehicles.put("TRUCK",rnd.nextInt(20));
            
            // Send message
            publisher.SendStatsMessage(msg);
            
            try {
                Thread.sleep(500);
            }
            catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
