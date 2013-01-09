package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import Storage.Storage_Area;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateDepartureVehicles {
        /**
     * Test the code
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Database.restoreDump();
        List<Boat> SeaBoats = GetSeaBoats();
        System.out.println("SeaBoats:    "+SeaBoats.size());
        List<Boat> InlandBoats = GetInlandBoats();
        System.out.println("InlandBoats: "+InlandBoats.size());
        List<Train> Trains = GetTrains();
        System.out.println("Trains:      "+Trains.size());
        List<Truck> Trucks = GetTrucks();
        System.out.println("Trucks:      "+Trucks.size());
    }
    
    public static List<Boat> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }

    public static List<Boat> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static List<Boat> GetBoats(String kindSchip) throws Exception{
        ArrayList<Boat> BoatList = new ArrayList<>();
        Vector3f storage = getWidthHeight(kindSchip);
        
        String query = "Select departureDateStart, departureDateEnd, count(*) as containers " +
                "from container " +
                "Where departureTransportType = '" + kindSchip + "' " +
                "Group by departureDateStart, departureDateEnd, departureTransportType " +
                "Order By departureDateStart, departureCompany ";
        
        ResultSet getBoats = Database.executeQuery(query);
        while(getBoats.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getBoats.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getBoats.getString("departureDateEnd"));
            storage.x = ((float)getBoats.getInt("containers") / storage.y*storage.z) + 1;
            BoatList.add(new Boat(departureDateStart, departureDateEnd, "departureShip", storage, new Node()));
        }
        
        return BoatList;
    }
    
    public static List<Train> GetTrains() throws Exception{
        ArrayList<Train> TrainList = new ArrayList<>();
        
        String query = "Select departureDateStart, departureDateEnd, count(*) as containers " +
                "from container " +
                "Where departureTransportType = 'trein' " +
                "Group by departureDateStart, departureDateEnd, departureTransportType " +
                "Order By departureDateStart, departureCompany ";
        
        ResultSet getTrains = Database.executeQuery(query);
        while(getTrains.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getTrains.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getTrains.getString("departureDateEnd"));
            int lenght = getTrains.getInt("containers");
            TrainList.add(new Train(departureDateStart, departureDateEnd, "departureShip", lenght, new Node()));
        }
        
        return TrainList;
    }

    public static List<Truck> GetTrucks() throws Exception {
        ArrayList<Truck> TruckList = new ArrayList<>();
        
        String query = "Select departureDateStart, departureDateEnd " +
                "from container " +
                "Where departureTransportType = 'vrachtauto' " +
                "Order By departureDateStart, departureCompany ";
        
        ResultSet getTrucks = Database.executeQuery(query);
        while(getTrucks.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getTrucks.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getTrucks.getString("departureDateEnd"));
            TruckList.add(new Truck(departureDateStart, departureDateEnd, "departureShip", new Node()));
        }
        
        return TruckList;
    }
    
        private static Vector3f getWidthHeight(String kindSchip) throws Exception{
            String query = "Select MAX(arrivalPositionY) as Y, MAX(arrivalPositionZ) as Z " +
                        "from container " +
                        "Where departureTransportType = '" + kindSchip + "' ";

            ResultSet getBoats = Database.executeQuery(query);

            return new Vector3f(1, getBoats.getInt("Y")+1, getBoats.getInt("Z")+1);
    }
}
