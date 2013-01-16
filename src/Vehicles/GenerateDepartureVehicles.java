package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import Storage.Storage_Area;
import java.sql.PreparedStatement;
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
        List<TransportVehicle> SeaBoats = GetSeaBoats();
        System.out.println("SeaBoats:    "+SeaBoats.size());
        List<TransportVehicle> InlandBoats = GetInlandBoats();
        System.out.println("InlandBoats: "+InlandBoats.size());
        List<TransportVehicle> Trains = GetTrains();
        System.out.println("Trains:      "+Trains.size());
        List<TransportVehicle> Trucks = GetTrucks();
        System.out.println("Trucks:      "+Trucks.size());
    }
    
    public static List<TransportVehicle> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }

    public static List<TransportVehicle> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static List<TransportVehicle> GetBoats(String kindSchip) throws Exception{
        ArrayList<TransportVehicle> BoatList = new ArrayList<>();
        Vector3f storage = getWidthHeight(kindSchip);
        
        String query = "SELECT departureDateStart, departureDateEnd, COUNT(*) as containers " +
                "FROM container " +
                "WHERE departureTransportType = ? " +
                "GROUP BY departureDateStart, departureDateEnd, departureTransportType " +
                "ORDER BY departureDateStart, departureCompany ";
        PreparedStatement stm = Database.createPreparedStatement(query);
        stm.setString(1, kindSchip);
        
        ResultSet getBoats = Database.executeQuery(stm);
        while(getBoats.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getBoats.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getBoats.getString("departureDateEnd"));
            int x = (int)((float)getBoats.getInt("containers") / storage.y*storage.z) + 1;
            //BoatList.add(new Boat(departureDateStart, departureDateEnd, "departureShip", storage, new Node()));
            BoatList.add(new TransportVehicle(departureDateStart, departureDateEnd, "departureShip", 
                    kindSchip.equals("zeeschip") ? Vehicle.VehicleType.seaBoat : Vehicle.VehicleType.inlandBoat, new Vector3f(x, storage.y, storage.z), new Node()));
        }
        
        return BoatList;
    }
    
    public static List<TransportVehicle> GetTrains() throws Exception{
        ArrayList<TransportVehicle> TrainList = new ArrayList<>();
        
        String query = "SELECT departureDateStart, departureDateEnd, count(*) as containers " +
                "FROM container " +
                "WHERE departureTransportType = 'trein' " +
                "GROUP BY departureDateStart, departureDateEnd, departureTransportType " +
                "ORDER BY departureDateStart, departureCompany ";
        
        ResultSet getTrains = Database.executeQuery(query);
        while(getTrains.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getTrains.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getTrains.getString("departureDateEnd"));
            int lenght = getTrains.getInt("containers");
            //TrainList.add(new Train(departureDateStart, departureDateEnd, "departureShip", lenght, new Node()));
            TrainList.add(new TransportVehicle(departureDateStart, departureDateEnd, "departureTrain", 
                    Vehicle.VehicleType.train,new Vector3f(lenght, 1, 1), new Node()));
        }
        
        return TrainList;
    }

    public static List<TransportVehicle> GetTrucks() throws Exception {
        ArrayList<TransportVehicle> TruckList = new ArrayList<>();
        
        String query = "SELECT departureDateStart, departureDateEnd " +
                "FROM container " +
                "WHERE departureTransportType = 'vrachtauto' " +
                "ORDER BY departureDateStart, departureCompany ";
        
        ResultSet getTrucks = Database.executeQuery(query);
        while(getTrucks.next()){
            // set variables
            Date departureDateStart = Container.df.parse(getTrucks.getString("departureDateStart"));
            Date departureDateEnd = Container.df.parse(getTrucks.getString("departureDateEnd"));
            //TruckList.add(new Truck(departureDateStart, departureDateEnd, "departureShip", new Node()));
            TruckList.add(new TransportVehicle(departureDateStart, departureDateEnd, "departureTrain", 
                    Vehicle.VehicleType.truck, new Vector3f(1, 1, 1), new Node()));
        }
        
        return TruckList;
    }
    
        private static Vector3f getWidthHeight(String kindSchip) throws Exception{
            String query = "SELECT MAX(arrivalPositionY) as Y, MAX(arrivalPositionZ) as Z " +
                        "FROM container " +
                        "WHERE departureTransportType = ?";
            PreparedStatement stm = Database.createPreparedStatement(query);
            stm.setString(1, kindSchip);

            ResultSet getBoats = Database.executeQuery(stm);

            return new Vector3f(1, getBoats.getInt("Y")+1, getBoats.getInt("Z")+1);
    }
}
