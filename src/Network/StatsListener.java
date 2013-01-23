/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import org.zeromq.ZMQ;

/**
 * This is simply a subscriber for the StatsPublisher, used by the controller,
 * to monitor sent StatsMessages in case no Android tablet is available.
 * @author Christiaan
 */
public class StatsListener {
    
    /**
     * Runnable main
     * @param args 
     */
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://127.0.0.1:6000");
        subscriber.subscribe("".getBytes());

        String eol = System.getProperty("line.separator");
        // Receive and deserialize
        while(true) {
            byte[] data = subscriber.recv(0);
            StatsMessage msg = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                msg = (StatsMessage) ois.readObject();
                ois.close();
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }
            
            if(msg != null) {
                
                String areas = "";
                for(String key : msg.getAreaKeys())
                    areas += "\t" + key + ": " + msg.areas.get(key) + eol;
                
                String vehicles = "";
                for(String key : msg.getVehicleKeys())
                    vehicles += "\t" + key + ": " + msg.vehicles.get(key) + eol;
                
                System.out.println(
                    "Date: " + msg.date.toString() + eol +
                    "Containers Incoming: " + msg.containers_incoming + eol +
                    "Containers Outgoing: " + msg.containers_outgoing + eol +
                    "Areas: " + msg.areas.size() + eol + areas +
                    "Vehicles: " + msg.vehicles.size() + eol + vehicles +
                    "-------------------------------------------------------"
                );
            }
        }
    }
}
