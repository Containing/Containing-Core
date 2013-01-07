package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateVehicles2 {
        /**
     * Test the code
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Database.restoreDump();
    }
    
    
    public static List<Boat> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }

    public static List<Boat> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static List<Boat> GetBoats(String kindSchip) throws Exception{
        ArrayList<Boat> BoatList = new ArrayList<>();
        return BoatList;
    }

    public static List<Train> GetTrains() throws Exception{
        ArrayList<Train> TrainList = new ArrayList<>();
        return TrainList;
    }

    public static List<Truck> GetTrucks() throws Exception {
        ArrayList<Truck> TruckList = new ArrayList<>();
        return TruckList;
    }
}
