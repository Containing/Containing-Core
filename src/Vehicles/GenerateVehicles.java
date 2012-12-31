package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Main.Database;
import Pathfinding.Node;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Tonnie Boersma
 */
public class GenerateVehicles {
    public static void main(String[] args) throws Exception 
    {
        //XML.XMLBinder.GenerateContainerDatabase("C:/one/XML7.xml");
        Database.restoreDump();
        //HashMap<Date,Boat> SeaBoats = GetSeaBoats();
        //HashMap<Date,Boat> InlandBoats = GetInlandBoats(); //Doesn't work yet.
        //HashMap<Date,Train> Trains = GetTrains();
        //HashMap<Date,Truck> Trucks = GetTrucks();
        
        //List<Boat> SeaBoats = GetSeaBoats();
        //List<Boat> InlandBoats = GetInlandBoats(); //Doesn't work yet.
        List<Train> Trains = GetTrains();
        //List<Truck> Trucks = GetTrucks();
        System.out.println(Trains.size());
        
        for (Train train : Trains) {
            System.out.println(train);
        }
    }
    
    public static HashMap<Date,Vehicle> GenerateVehicles(){
        HashMap<Date,Vehicle> returnHashMap = new HashMap();

        return returnHashMap;
    }
    
    public static List<Boat> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }
    public static List<Boat> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    private static List<Boat> GetBoats(String kindSchip) throws Exception{
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
            
            Boat boat = new Boat(arrivalDateStart, arrivalDateEnd, arrivalCompany, new Vector3f(x, y, z), /*SpawnNode*/new Node(0, 0));
            
            if (getBoats.getInt("containers") > x*y*z){
                int amount = (getBoats.getInt("containers") / (x*y*z)) + 1;
                for (int i = 0; i < amount; i++) {
                    BoatList.add(boat);
                }
            }
            else{
                BoatList.add(boat);
            }
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
                System.out.println(boat);
                boat = BoatList.get(++counter);
            }
            
            int arrivalX = (int)container.getArrivalPosition().x;
            int arrivalZ = (int)container.getArrivalPosition().z;
            
            if (!boat.storage.IsFilled(arrivalX, arrivalZ)){
                boat.storage.PushContainer(container, (int)container.getArrivalPosition().x, (int)container.getArrivalPosition().z);
            }
            else if (BoatList.size() > counter){
                Boat nextBoat = BoatList.get(counter+1);
                if (nextBoat.GetArrivalDate().equals(boat.GetArrivalDate()) &&
                    nextBoat.GetDepartureDate().equals(boat.GetDepartureDate()) &&
                    nextBoat.GetCompany().equals(boat.GetCompany())){
                    try{
                        nextBoat.storage.PushContainer(container, (int)container.getArrivalPosition().x, (int)container.getArrivalPosition().z);
                    }
                    catch(Exception ex){
                        
                    }
                    //System.out.println("Next boat is the same");
                    
                }
            }
            else{
                System.out.println("DumpContainer");
            }

            
            if (boat.storage.Count(arrivalX, arrivalZ) <= container.getArrivalPosition().y){

            }
            else{

//                Boat nextBoat = BoatList.get(counter+1);
//                
//                if ((nextBoat.GetArrivalDate().equals(boat.GetArrivalDate()) && 
//                nextBoat.GetDepartureDate().equals(boat.GetDepartureDate()) &&
//                nextBoat.GetCompany().equals(boat.GetCompany()))){
//                    try{
//                    nextBoat.storage.PushContainer(container, (int)container.getArrivalPosition().x, (int)container.getArrivalPosition().z);
//                    }
//                    catch(Exception ex){
//                        System.out.println("Why :'( ");
//                    }
//                }
                
            }
        }
        //System.out.println("+"+BoatList.get(BoatList.size()-3).storage.PeekContainer(0, 0).getContainNr());
        //System.out.println("+"+BoatList.get(BoatList.size()-2).storage.PeekContainer(0, 0).getContainNr());
        //System.out.println("+"+BoatList.get(BoatList.size()-1).storage.PeekContainer(0, 0).getContainNr());
        
        return BoatList;
    }
    public static List<Train> GetTrains() throws Exception{
        ArrayList<Train> TrainList = new ArrayList<>();
        
        String query = "Select arrivalDateStart, arrivalDateEnd, arrivalCompany, count(*) as containers, MAX(arrivalPositionX) as SizeX " +
                "from container " +
                "Where arrivalTransportType = 'trein' " +
                "Group by arrivalDateStart, arrivalDateEnd, arrivalTransportType, arrivalCompany " +
                "Order By arrivalDateStart, arrivalCompany ";
        
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
                    TrainList.add(new Train(arrivalDateStart, arrivalDateEnd, arrivalCompany, x, /*SpawnNode*/new Node(0, 0)));
                }
            }
            else{
                // add the train
                TrainList.add(new Train(arrivalDateStart, arrivalDateEnd, arrivalCompany, x, /*SpawnNode*/new Node(0, 0)));
            }
        }
        
        query = "Select * " +
                "from container " +
                "Where arrivalTransportType = 'trein' " +
                "Order By  arrivalDateStart, arrivalCompany, arrivalPositionY ";
        
        // foreach container selected by the SQL query
        ResultSet fillTrains = Database.executeQuery(query);
        int counter = 0;
        while(fillTrains.next()){
            
            // get the train and generate the container
            Train train = TrainList.get(counter);
            Container container = ConvertToContainer(fillTrains);
            
            // if the container doesn't match the train, go to the next train.
            while (!train.MatchesContainer(container)) { 
                counter++;
                train = TrainList.get(counter);
            }
            
            // if the container can be pushed
            if (train.storage.Count((int)container.getArrivalPosition().x, 0) < train.storage.getHeight()){
                train.storage.PushContainer(container, (int)container.getArrivalPosition().x, 0);
            }
            else{
                // if their is get the next train, get it.
                Train nextTrain = (counter+1 < TrainList.size()) ? TrainList.get(counter+1) : null;
                do{
                    // if their is a next train
                    if (nextTrain != null){
                        // if the next train matches the container
                        if (nextTrain.MatchesContainer(container)){
                            // if the container can be pushed, push it and break out the while loop, else go to the next train if their is one.
                            if (nextTrain.storage.Count((int)container.getArrivalPosition().x, 0) < nextTrain.storage.getHeight()){
                                nextTrain.storage.PushContainer(container, (int)container.getArrivalPosition().x, 0);
                                break;
                            }
                            else{
                                nextTrain = (counter+1 < TrainList.size()) ? TrainList.get(counter+1) : null;
                                // if their is no train left break out the while loop
                                if (nextTrain == null){
                                    break;
                                }
                            }
                        }
                    }
                // if the next container still matches the container try again.
                }while(!nextTrain.MatchesContainer(container));
            }
        }
        return TrainList;
    }

    public static List<Truck> GetTrucks() throws Exception {
        ArrayList<Truck> TruckList = new ArrayList<>();
        
        String query = "Select * "+
                        "from container "+
                        "Where arrivalTransportType == 'vrachtauto' " +
                        "Order By arrivalDateStart, arrivalCompany";
        
        ResultSet rs = Database.executeQuery(query);
        while(rs.next()){
            Container container = ConvertToContainer(rs);
            Truck truck = new Truck(container.getArrivalDateStart(), container.getArrivalDateEnd(), container.getArrivalCompany(), /*SpawnNode*/new Node(0, 0));
            truck.storage.PushContainer(container, 0, 0);
            TruckList.add(truck);
        }
        return TruckList;
    }

    /**
     * Converts a DB row to a container.
     * @return The Container formed from a DB row.
     * @throws Exception If something goes wrong while reading from the DB.
     */
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
