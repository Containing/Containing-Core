package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Network.objPublisher;
import Pathfinding.Node;
import Vehicles.Vehicle.VehicleType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generates the TransportVehicles from a in memory database.
 * @author Tonnie Boersma
 */
public class GenerateArrivalVehicles {
    
    /**
     * Reference to objPublisher
     */
    public static objPublisher objpublisher;
    
    /**
     * Test the code
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //XML.XMLBinder.GenerateContainerDatabase("C:/Users/Martin_Notebook/Dropbox/containing/XML files/xml7.xml");
        //Database.dumpDatabase();
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
    
    /**
     * A list with disposed containers, if their are any.
     */
    public static List<Container> DisposedContainers = new ArrayList<>();
    
    /**
     * Generates all the seaBoats from the database
     * @return List with boats, ordered by arrivalDate
     * @throws Exception If something goes wrong while excecuting the query
     */
    public static List<TransportVehicle> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }
    /**
     * Generates all the inlandBoats from the database
     * @return List with boats, ordered by arrivalDate
     * @throws Exception If something goes wrong while excecuting the query
     */
    public static List<TransportVehicle> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static List<TransportVehicle> GetBoats(String kindSchip) throws Exception{
        ArrayList<TransportVehicle> BoatList = new ArrayList<>();
        
        String query = "SELECT arrivalDateStart, arrivalDateEnd, arrivalCompany, COUNT(*) AS containers, MAX(arrivalPositionX) as SizeX, MAX(arrivalPositionY) as SizeY, MAX(arrivalPositionZ) as SizeZ " +
                        "FROM container " +
                        "WHERE arrivalTransportType = ? " +
                        "GROUP BY arrivalDateStart, arrivalDateEnd, arrivalTransportType, arrivalCompany " +
                        "ORDER BY arrivalDateStart, arrivalCompany ";
        PreparedStatement stm = Database.createPreparedStatement(query);
        stm.setString(1, kindSchip);
        
        // foreach boat generated by the SQL query
        ResultSet getBoats = Database.executeQuery(stm);
        while(getBoats.next()){
            // set variables
            Date arrivalDateStart = Container.df.parse(getBoats.getString("arrivalDateStart"));
            Date arrivalDateEnd = Container.df.parse(getBoats.getString("arrivalDateEnd"));
            String arrivalCompany = getBoats.getString("arrivalCompany");
            int x = getBoats.getInt("SizeX")+1;
            int y = getBoats.getInt("SizeY")+1;
            int z = getBoats.getInt("SizeZ")+1;
            
            // if the amount of containers is larger then the boat capacity
            if (getBoats.getInt("containers") > x*y*z){
                // calculate and add the amount of boats
                int amount = (getBoats.getInt("containers") / (x*y*z)) + 1;
                for (int i = 0; i < amount; i++) {
                    //BoatList.add(new Boat(arrivalDateStart, arrivalDateEnd, arrivalCompany, new Vector3f(x, y, z), /*SpawnNode*/new Node(0, 0)));
                    BoatList.add(new TransportVehicle(arrivalDateStart, arrivalDateEnd, arrivalCompany, 
                            kindSchip.equals("zeeschip") ? VehicleType.seaBoat : VehicleType.inlandBoat, new Vector3f(x, y, z), new Node(), objpublisher));
                }
            }
            else{
                // add one boat
                BoatList.add(new TransportVehicle(arrivalDateStart, arrivalDateEnd, arrivalCompany,
                        kindSchip.equals("zeeschip") ? VehicleType.seaBoat : VehicleType.inlandBoat, new Vector3f(x, y, z), new Node(), objpublisher));
            }
        }
        
        query = "SELECT * " +
                "FROM container " +
                "WHERE arrivalTransportType = ? " +
                "ORDER BY arrivalDateStart, arrivalCompany, arrivalPositionY ";
        stm = Database.createPreparedStatement(query);
        stm.setString(1, kindSchip);
        
        // foreach container selected by the SQL query
        ResultSet fillBoats = Database.executeQuery(stm);
        int counter = 0;
        while(fillBoats.next()){
            
            // get the boat and generate the container
            TransportVehicle boat = BoatList.get(counter);
            Container container = ConvertToContainer(fillBoats);
            
            // if the container doesn't match the boat, go to the next boat.
            while (!boat.MatchesContainer(container)){
                counter++;
                boat = BoatList.get(counter);
            }
            
            int arrivalX = (int)container.getArrivalPosition().x;
            int arrivalZ = (int)container.getArrivalPosition().z;
            
            // if the container can be pushed
            if (boat.storage.Count(arrivalX, arrivalZ) < boat.storage.getHeight()){
                boat.storage.pushContainer(container, arrivalX, arrivalZ);
            }
            else{
                // if their is get the next boat.
                TransportVehicle nextBoat = BoatList.get(counter+1);
                do{
                    // if their is a next boat
                    if (nextBoat != null){
                        // if the next boat matches the container
                        if (nextBoat.MatchesContainer(container)){
                            // if the container can be pushed, push it and break, else go to the next boat if their is one.
                            if (nextBoat.storage.Count(arrivalX, arrivalZ) < nextBoat.storage.getHeight()){
                                nextBoat.storage.pushContainer(container, arrivalX, arrivalZ);
                                break;
                            }
                            else{
                                nextBoat = (counter+1 < BoatList.size()) ? BoatList.get(counter+1) : null;
                                // if their is no boat left, break.
                                if (nextBoat == null){
                                    break;
                                }
                            }
                        }
                        else{
                            // add the container to the disposed list and break.
                            DisposedContainers.add(container);
                            break;
                        }
                    }
                // if the next container still matches the container try again.
                }while(!nextBoat.MatchesContainer(container));
            }
        }
        
        return BoatList;
    }
    /**
     * Generates all the trains from the database
     * @return List with trains, ordered by arrivalDate
     * @throws Exception If something goes wrong while excecuting the query
     */
    public static List<TransportVehicle> GetTrains() throws Exception{
        ArrayList<TransportVehicle> TrainList = new ArrayList<>();
        
        String query = "SELECT arrivalDateStart, arrivalDateEnd, arrivalCompany, count(*) as containers, MAX(arrivalPositionX) as SizeX " +
                "FROM container " +
                "WHERE arrivalTransportType = 'trein' " +
                "GROUP BY arrivalDateStart, arrivalDateEnd, arrivalTransportType, arrivalCompany " +
                "ORDER BY arrivalDateStart, arrivalCompany ";
        
        // foreach train generated by the SQL query
        ResultSet getTrains = Database.executeQuery(query);
        while(getTrains.next()){
            // set variables
            Date arrivalDateStart = Container.df.parse(getTrains.getString("arrivalDateStart"));
            Date arrivalDateEnd = Container.df.parse(getTrains.getString("arrivalDateEnd"));
            String arrivalCompany = getTrains.getString("arrivalCompany");
            int x = getTrains.getInt("SizeX")+1;
            
            // if the amount of containers is larger then the train lenght
            if (getTrains.getInt("containers") > x){
                // calculate and add the amount of trains
                int amount = (getTrains.getInt("containers") / (x)) + 1;
                for (int i = 0; i < amount; i++) {
                    //TrainList.add(new Train(arrivalDateStart, arrivalDateEnd, arrivalCompany, x, /*SpawnNode*/new Node(0, 0)));
                    TrainList.add(new TransportVehicle(arrivalDateStart, arrivalDateEnd, arrivalCompany,
                            VehicleType.train ,new Vector3f(x, 1, 1), new Node(), objpublisher));
                }
            }
            else{
                // add one train
                    TrainList.add(new TransportVehicle(arrivalDateStart, arrivalDateEnd, arrivalCompany,
                            VehicleType.train ,new Vector3f(x, 1, 1), new Node(), objpublisher));
            }
        }
        
        query = "SELECT * " +
                "FROM container " +
                "WHERE arrivalTransportType = 'trein' " +
                "ORDER BY arrivalDateStart, arrivalCompany, arrivalPositionY";
        
        // foreach container selected by the SQL query
        ResultSet fillTrains = Database.executeQuery(query);
        int counter = 0;
        while(fillTrains.next()){
            
            // get the train and generate the container
            TransportVehicle train = TrainList.get(counter);
            Container container = ConvertToContainer(fillTrains);
            
            // if the container doesn't match the train, go to the next train.
            while (!train.MatchesContainer(container)) { 
                counter++;
                train = TrainList.get(counter);
            }
            
            int arrivalX = (int)container.getArrivalPosition().x;
            
            // if the container can be pushed
            if (train.storage.Count(arrivalX, 0) < train.storage.getHeight()){
                train.storage.pushContainer(container, arrivalX, 0);
            }
            else{
                // if their is get the next train.
                TransportVehicle nextTrain = (counter+1 < TrainList.size()) ? TrainList.get(counter+1) : null;
                do{
                    // if their is a next train
                    if (nextTrain != null){
                        // if the next train matches the container
                        if (nextTrain.MatchesContainer(container)){
                            // if the container can be pushed, push it and break, else go to the next train if their is one.
                            if (nextTrain.storage.Count(arrivalX, 0) < nextTrain.storage.getHeight()){
                                nextTrain.storage.pushContainer(container, arrivalX, 0);
                                break;
                            }
                            else{
                                nextTrain = (counter+1 < TrainList.size()) ? TrainList.get(counter+1) : null;
                                // if their is no train left, break.
                                if (nextTrain == null){
                                    break;
                                }
                            }
                        }
                        else{
                            // add the container to the disposed list and break.
                            DisposedContainers.add(container);
                            break;
                        }
                    }
                // if the next container still matches the container try again.
                }while(!nextTrain.MatchesContainer(container));
            }
        }
        return TrainList;
    }
    /**
     * Generates all the trucks from the database
     * @return List with trucks, ordered by arrivalDate
     * @throws Exception If something goes wrong while excecuting the query
     */
    public static List<TransportVehicle> GetTrucks() throws Exception {
        ArrayList<TransportVehicle> TruckList = new ArrayList<>();
        
        String query = "SELECT * "+
                        "FROM container "+
                        "WHERE arrivalTransportType = 'vrachtauto' " +
                        "ORDER BY arrivalDateStart, arrivalCompany";
        
        // foreach container selected by the SQL query
        ResultSet rs = Database.executeQuery(query);
        while(rs.next()){
            // generate a new truck, and generate a container 
            Container container = ConvertToContainer(rs);
            //Truck truck = new Truck(container.getArrivalDateStart(), container.getArrivalDateEnd(), container.getArrivalCompany(), /*SpawnNode*/new Node(0, 0));
            TransportVehicle truck = new TransportVehicle(container.getArrivalDateStart(), container.getArrivalDateEnd(), container.getArrivalCompany(), 
                    VehicleType.truck ,new Vector3f(1, 1, 1), new Node(), objpublisher);
            // push the container on the truck.
            truck.storage.pushContainer(container, 0, 0);
            TruckList.add(truck);
        }
        return TruckList;
    }
    /**
     * Converts a DB row to a container.
     * @return The Container formed from a DB row.
     * @throws Exception If something goes wrong while excecuting the query
     */
    public static Container ConvertToContainer(ResultSet rs) throws Exception{
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
