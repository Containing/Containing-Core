/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.zeromq.ZMQ;

/**
 *
 * @author Christiaan
 */
public class StatsPublisherTest {
    private final StatsMessage msg = new StatsMessage();
    
    public StatsPublisherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        msg.containers_incoming = 100;
        msg.containers_outgoing = 200;
        msg.areas.put("Area 1", 40);
        msg.areas.put("Area 2", 60);
        msg.vehicles.put("AGV", 100);
        msg.vehicles.put("TRUCK", 10);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of SendStatsMessage method, of class StatsPublisher.
     */
    @Test
    public void testSendStatsMessage() throws Exception {
        System.out.println("SendStatsMessage");

        


        // Run subscriber inside a thread
        Runnable subscriberRunnable = new Runnable() {
            @Override
            public void run() {
                ZMQ.Context context = ZMQ.context(1);
                ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
                subscriber.connect("tcp://127.0.0.1:6000");
                subscriber.subscribe("".getBytes());
                
                // Receive and deserialize
                byte[] data = subscriber.recv(0);
                StatsMessage recvMsg = null;
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                    recvMsg = (StatsMessage) ois.readObject();
                    ois.close();
                }
                catch(Exception e) {
                    fail(e.getMessage());
                    return;
                }

                // Make sure message data matches
                assertEquals(msg.containers_incoming, recvMsg.containers_incoming);
                assertEquals(msg.containers_outgoing, recvMsg.containers_outgoing);
                assertEquals(msg.date.toString(), recvMsg.date.toString());                
                assertArrayEquals(msg.getAreaKeys(), recvMsg.getAreaKeys());
                assertArrayEquals(msg.getAreaValues(), recvMsg.getAreaValues());
                assertArrayEquals(msg.getVehicleKeys(), recvMsg.getVehicleKeys());
                assertArrayEquals(msg.getVehicleValues(), recvMsg.getVehicleValues());
            }
        };
        
        Thread subscriberThread = new Thread(subscriberRunnable);
        subscriberThread.start();
        
        // Setup StatsPublisher and send a message
        StatsPublisher instance = new StatsPublisher();
        instance.SendStatsMessage(msg);
        subscriberThread.join();
        System.out.println("all done");
    }
}
