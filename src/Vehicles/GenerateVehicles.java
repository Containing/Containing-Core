package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateVehicles {
    public static void main(String[] args) throws Exception 
    {
        HashMap<Date,Truck> a = new HashMap();
        XML.XMLBinder.GenerateContainerDatabase("C:/one/xml1.xml");
        //Database.dumpDatabase("test");
        GetTrucks();
    }
    
    public static HashMap<Date,Boat> GetSeaBoats(){
        HashMap<Date,Boat> returnHashMap = new HashMap();
        
        // logic
        
        return returnHashMap;
    }    
    public static HashMap<Date,Boat> GetInlandBoats(){
        HashMap<Date,Boat> returnHashMap = new HashMap();
        
        // logic
        
        return returnHashMap;
    }
    public static HashMap<Date,Train> GetTrains(){
        HashMap<Date,Train> returnHashMap = new HashMap();
        
        // logic
        
        return returnHashMap;
    }
    public static HashMap<Date,Truck> GetTrucks() throws Exception {
        HashMap<Date,Truck> returnHashMap = new HashMap();
        
        String query = "Select * "+
                        "from container "+
                        "Where arrivalTransportType == 'vrachtauto' " +
                        "Order By arrivalDateStart";
        
        ResultSet rs = Database.executeQuery(query);
        while(rs.next()){
            Container container = ConvertToContainer(rs);
            Truck truck = new Truck(container.getArrivalDateStart(), container.getArrivalDateEnd(), /*SpawnNode*/new Node(0, 0));
            // To do set container on truck.
            returnHashMap.put(container.getArrivalDateStart(), truck);
        }
        
        return returnHashMap;
    }
    
    private static Container ConvertToContainer(ResultSet rs) throws Exception{
        Container returnContainer = new Container(rs.getString("id"));
        
        returnContainer.setArrival( Container.df.parse(rs.getString("arrivalDateStart")), 
                                    Container.df.parse(rs.getString("arrivalDateEnd")),
                                    Container.TransportType.valueOf(rs.getString("arrivalTransportType")),
                                    rs.getString("arrivalCompany"), 
                                    ConvertToVector3f(rs.getString("arrivalPosition")));
        returnContainer.setOwnerInformation(rs.getString("owner"), rs.getInt("containerNr"));
        returnContainer.setDeparture(Container.df.parse(rs.getString("departureDateStart")), 
                                    Container.df.parse(rs.getString("departureDateEnd")), 
                                    Container.TransportType.valueOf(rs.getString("departureTransportType")), 
                                    rs.getString("departureCompany"));
        returnContainer.setWeightInformation(rs.getInt("empty"), rs.getInt("weight"));
        returnContainer.setContentInformation(rs.getString("name"), rs.getString("kind"), rs.getString("danger"));
        return returnContainer;
    }
    
    private static Vector3f ConvertToVector3f(String input){
        char[] charInput = input.toCharArray();
        int x = Integer.parseInt(Character.toString(charInput[2]) + Character.toString(charInput[3]));
        int y = Integer.parseInt(Character.toString(charInput[0]) + Character.toString(charInput[1]));
        int z = Integer.parseInt(Character.toString(charInput[4]) + Character.toString(charInput[5]));
        return new Vector3f(x, y, z);
    }
}
