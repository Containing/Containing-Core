package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateVehicles {
    public static void main(String[] args) throws Exception 
    {
        //XML.XMLBinder.GenerateContainerDatabase("C:/one/XML7.xml");
        Database.restoreDump();
        HashMap<Date,Boat> SeaBoats = GetSeaBoats();
        //HashMap<Date,Boat> InlandBoats = GetInlandBoats(); Doesn't work yet.
        //HashMap<Date,Train> Trains = GetTrains();
        HashMap<Date,Truck> Trucks = GetTrucks();
    }
    
    public static HashMap<Date,Vehicle> GenerateVehicles(){
        HashMap<Date,Vehicle> returnHashMap = new HashMap();

        return returnHashMap;
    }
    
    public static HashMap<Date,Boat> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }
    public static HashMap<Date,Boat> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static HashMap<Date,Boat> GetBoats(String kindSchip) throws Exception{
        ArrayList<Boat> BoatList = new ArrayList<>();
        
        String query = "Select arrivalDateStart, arrivalDateEnd, arrivalCompany, count(*) as containers, MAX(arrivalPositionX) as SizeX, MAX(arrivalPositionY) as SizeY, MAX(arrivalPositionZ) as SizeZ " +
                        "from container " +
                        "Where arrivalTransportType = '" + kindSchip + "' " +
                        "Group by arrivalDateStart, arrivalDateEnd, arrivalTransportType, arrivalCompany " +
                        "Order By arrivalDateStart, arrivalCompany ";
        
        ResultSet getBoats = Database.executeQuery(query);
        while(getBoats.next()){
            Date arrivalDateStart = Container.df.parse(getBoats.getString("arrivalDateStart"));
            Date arrivalDateEnd = Container.df.parse(getBoats.getString("arrivalDateEnd"));
            String arrivalCompany = getBoats.getString("arrivalCompany");
            int x = getBoats.getInt("SizeX")+1;
            int y = getBoats.getInt("SizeY")+1;
            int z = getBoats.getInt("SizeZ")+1;
            if (getBoats.getInt("containers") > x*y*z){
                throw new Exception("To many containers for this boat");
            }
            Boat boat = new Boat(arrivalDateStart, arrivalDateEnd, arrivalCompany, new Vector3f(x, y, z), /*SpawnNode*/new Node(0, 0));

            BoatList.add(boat);
        }
        
        
        query = "Select * " +
                "from container " +
                "Where arrivalTransportType = '" + kindSchip + "' " +
                "Order By  arrivalDateStart, arrivalCompany, arrivalPositionY ";
        
        ResultSet fillBoats = Database.executeQuery(query);
        int counter = 0;
        while(fillBoats.next()){
            Boat boat = BoatList.get(counter);
            Container container = ConvertToContainer(fillBoats);
            
            if (!(boat.GetArrivalDate().equals(container.getArrivalDateStart()) && 
                boat.GetDepartureDate().equals(container.getArrivalDateEnd()) &&
                boat.GetCompany().equals(container.getArrivalCompany()))){
                boat = BoatList.get(++counter);
            }
            
            
            boat.storage.PushContainer(container, (int)container.getArrivalPosition().x, (int)container.getArrivalPosition().z);
        }
        
        HashMap<Date,Boat> returnHashMap = new HashMap();
        for (Boat boat : BoatList) {
            returnHashMap.put(boat.GetArrivalDate(), boat);
        }
        return returnHashMap;
    }
    
    
    public static HashMap<Date,Train> GetTrains() throws Exception{
        ArrayList<Train> TrainList = new ArrayList<>();
        
        String query = "Select arrivalDateStart, arrivalDateEnd, arrivalCompany, count(*) as containers, MAX(arrivalPositionX) as SizeX " +
                "from container " +
                "Where arrivalTransportType = 'trein' " +
                "Group by arrivalDateStart, arrivalDateEnd, arrivalTransportType, arrivalCompany " +
                "Order By arrivalDateStart, arrivalCompany ";
        
        ResultSet getTrains = Database.executeQuery(query);
        while(getTrains.next()){
            Date arrivalDateStart = Container.df.parse(getTrains.getString("arrivalDateStart"));
            Date arrivalDateEnd = Container.df.parse(getTrains.getString("arrivalDateEnd"));
            String arrivalCompany = getTrains.getString("arrivalCompany");
            int x = getTrains.getInt("SizeX")+1;
            if (getTrains.getInt("containers") > x){
                throw new Exception("To many containers for this train");
            }
            Train train = new Train(arrivalDateStart, arrivalDateEnd, arrivalCompany, x, /*SpawnNode*/new Node(0, 0));

            TrainList.add(train);
        }
        
        query = "Select * " +
                "from container " +
                "Where arrivalTransportType = 'trein' " +
                "Order By  arrivalDateStart, arrivalCompany, arrivalPositionY ";
        
        ResultSet fillTrains = Database.executeQuery(query);
        int counter = 0;
        while(fillTrains.next()){
            Train train = TrainList.get(counter);
            Container container = ConvertToContainer(fillTrains);
            
            if (!(train.GetArrivalDate().equals(container.getArrivalDateStart()) && 
                train.GetDepartureDate().equals(container.getArrivalDateEnd()) &&
                train.GetCompany().equals(container.getArrivalCompany()))){
                train = TrainList.get(++counter);
            }
            train.storage.PushContainer(container, (int)container.getArrivalPosition().x, 0);
        }
        
        
        
        HashMap<Date,Train> returnHashMap = new HashMap();
        for (Train train : TrainList) {
            returnHashMap.put(train.GetArrivalDate(), train);
        }
        return returnHashMap;
    }
    public static HashMap<Date,Truck> GetTrucks() throws Exception {
        HashMap<Date,Truck> returnHashMap = new HashMap();
        
        String query = "Select * "+
                        "from container "+
                        "Where arrivalTransportType == 'vrachtauto' " +
                        "Order By arrivalDateStart, arrivalCompany";
        
        ResultSet rs = Database.executeQuery(query);
        while(rs.next()){
            Container container = ConvertToContainer(rs);
            Truck truck = new Truck(container.getArrivalDateStart(), container.getArrivalDateEnd(), container.getArrivalCompany(), /*SpawnNode*/new Node(0, 0));
            truck.storage.PushContainer(container, 0, 0);
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
                                    new Vector3f(rs.getInt("arrivalPositionX"), rs.getInt("arrivalPositionY"), rs.getInt("arrivalPositionZ")));
        returnContainer.setOwnerInformation(rs.getString("owner"), rs.getInt("containerNr"));
        returnContainer.setDeparture(Container.df.parse(rs.getString("departureDateStart")), 
                                    Container.df.parse(rs.getString("departureDateEnd")), 
                                    Container.TransportType.valueOf(rs.getString("departureTransportType")), 
                                    rs.getString("departureCompany"));
        returnContainer.setWeightInformation(rs.getInt("empty"), rs.getInt("weight"));
        returnContainer.setContentInformation(rs.getString("name"), rs.getString("kind"), rs.getString("danger"));
        return returnContainer;
    }
}
