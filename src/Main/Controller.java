/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Helpers.*;
import Pathfinding.Node;
import Vehicles.*;
import Storage.Storage_Area;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import updateTimer.updateTimer;
import Crane.*;
import Vehicles.Vehicle.VehicleType;
import java.sql.ResultSet;


/**
 *
 * @author Martin_Notebook
 */
public class Controller {
    // The updateTimer class, Updates the update method
    updateTimer timer;
    
    // List with all the present transport vehicles
    List<TransportVehicle> presentVehicles;
    // List with all AGVs
    List<Vehicle> agvList;
    // List with all Cranes
    List<Crane> craneList;
    
    List<StorageCrane> storageCranes;
    // List with all the messages for the controller
    List<Message> messageQueue;
    
    // The storage area where all the containers are stored
    Storage_Area storageArea;
    
    // The simulation time of this application
    Date simulationTime;
    // The date when the next container needs to be send
    Date deliveryTime;
    // The date when the next shipment arrives
    Date shipmentTime;
    
    // List with all the Vehicles that will arrive
    List<TransportVehicle> seaShipsToArrive;
    List<TransportVehicle> bargesToArrive;
    List<TransportVehicle> trainsToArrive;
    List<TransportVehicle> trucksToArrive;
    
    /**
     * The amount of seconds the simulation time will increment after each update
     */
    public int secondsToIncrement;
    
    /**
     * Constructs a new controller
     * Then initializes the controller
     */
    public Controller() throws Exception
    {
        // Initializes the class variables
        Initialize();        
        // Walk's through all the method's in this class
        for(Method method : this.getClass().getMethods()){
            // When the method equals the update method
            if("Update".equals(method.getName())){
                // Construct a new update timer
                timer = new updateTimer(method, this);
                break;
            }
        }        
        // Start's and run's the timer for updating the application
        timer.start();
        timer.run();
    }
    
    /**
     * Initializes the class variables
     **/
    private void Initialize() throws Exception
    {   
        // Default seconds increment value
        secondsToIncrement = 6;
        
        // Initializes new ArrayLists
        messageQueue = new ArrayList();
        presentVehicles = new ArrayList();        
        agvList = new ArrayList();
        craneList = new ArrayList();      
        
        if(!Database.restoreDump()){
            // When it doesn't exists
            XML.XMLBinder.GenerateContainerDatabase("src/XML/xml7.xml");
            Database.dumpDatabase();
        }        
        // Loads all the vehicles that come to the harbor
        seaShipsToArrive = GenerateArrivalVehicles.GetSeaBoats();
        bargesToArrive = GenerateArrivalVehicles.GetInlandBoats();
        trainsToArrive = GenerateArrivalVehicles.GetTrains();
        trucksToArrive = GenerateArrivalVehicles.GetTrucks();
        
        // Initializes the dates
        deliveryTime = new Date(); 
        shipmentTime = new Date();
        simulationTime = new Date();
        
        // Gets the first shipment
        GetNextArrivalDate();        
        
        // Sets the simulationTime equal to the first shipment
        simulationTime.setTime(shipmentTime.getTime());
        // Sets the simulationTime 1 hour before the first shipment  
        simulationTime.setHours(simulationTime.getHours() -1);
        
        // Adds 100 AGVs
        for(int i = 0; i < 100; i++){
            agvList.add(new AGV(new Node(1,0)));
        }
        
        /**
         * TODO: Initialize the positions where the cranes need to stand
         * Initialize the map with all his nodes and parkinglots
         */
        
        //Er zijn in totaal  10 zeeschipkranen, 8 binnenvaartkranen, 4 treinkranen en 20 truckkranen 
        for(int i = 0; i < 10; i++){    
            // Add 10 seaShipCranes
            //craneList.add(Crane);
        }
        for(int i = 0 ; i < 8; i++){     
            // Add 8 BargeCranes
            //craneList.add(Crane);
        }        
        for(int i  =0 ; i < 4; i++){         
            // Add 4 trainCranes
            //craneList.add(Crane);
        }        
        for (int i = 0; i < 20; i++){          
            // Add 20 truckCranes
            //craneList.add(Crane);
        }
    }
    
    private List<Id_Position> GetDepartureContainers(Vehicle.VehicleType vehicleType, Date now) throws Exception{
        List<Id_Position> id_positionList = new ArrayList<>();
        String type = "";
        switch(vehicleType){
            case AGV:
            throw  new Exception("Agv's don't have departure containers");
            case inlandBoat:
            type = "binnenschip";
            break;
            case seaBoat:
            type = "zeeschip";
            break;
            case train:
            type = "trein";
            break;
            case truck:
            type = "vrachtauto";
            break;
        }
        Date after = new Date();
        after = now;
        after.setSeconds(after.getSeconds() - secondsToIncrement);
        String query = "Select locationId, storageLocation " +
                        "from container " +
                        "Where departureDateStart between '" + Container.df.format(after) + "' and '" + Container.df.format(Container.df.format(now)) + "' " +
                        "Where departureTransportType = '" + type + "' " +
                        "Order By departureDateStart ";
        System.out.println(now + " <-> " + after);
        ResultSet getLocationId_storageLocation = Database.executeQuery(query);
        while(getLocationId_storageLocation.next()){
            String Id = getLocationId_storageLocation.getString("locationId");
            String position = getLocationId_storageLocation.getString("storageLocation");
            id_positionList.add(new Id_Position(Id, StorageLocationToVector3f(position)));
        }
        return id_positionList;
    }
    
    private Vector3f StorageLocationToVector3f(String input){
        char[] inputC = input.toCharArray();
        String a = Character.toString(inputC[0]);
        int x = Integer.parseInt(Character.toString(inputC[2]) + Character.toString(inputC[3]));
        int y = Integer.parseInt(Character.toString(inputC[0]) + Character.toString(inputC[1]));
        int z = Integer.parseInt(Character.toString(inputC[4]) + Character.toString(inputC[5]));
        return new Vector3f(x, y, z);
    }
    
    /**
     * Updates simulation logic
     * @param gameTime 
     */
    public void Update(float gameTime ) throws Exception
    {
        System.out.println(simulationTime);
        simulationTime.setSeconds(simulationTime.getSeconds() + secondsToIncrement);
        System.out.println(gameTime);
        
        // Updates the logic of each AGV
        for(Vehicle agv : agvList){
            agv.update(secondsToIncrement);
        }
        // Updates the logic of each crane
        for(Crane crane : craneList){
            crane.update(secondsToIncrement);
        }
        // Updates the logic of each docked vehicle
        for(Vehicle vehicle : presentVehicles){
            vehicle.update(secondsToIncrement);
        }     
        
        // When the next shipment arrives
        if(simulationTime.getTime() >= shipmentTime.getTime()){
            // Gets all the shipments that can arrive
            UpdateShipment();
            // Gets the next shipment time
            GetNextArrivalDate();
        }        
        // When the simulation time is equal or greater than the deliveryTime
        if(simulationTime.getTime() >= deliveryTime.getTime()){
            // Gets all the top containers that need to be transported
            FetchContainers();
            // Gets the next date when the next shipment needs to be transported
            GetNextDeliverDate();
            
            /**
             * TODO : 
             * When a container is fetch check if the container below if it needs to be fetched
             */
        }
        // Updates all the messageQueue
        UpdateMessages();
    }
    
    /**
     * Updates all the messageQueue 
     * @throws Exception 
     */
    private void UpdateMessages() throws Exception{
        // Checks every message
        for(Message message : messageQueue){
            // When the message requests an AGV 
            if(message.RequestedObject() == AGV.class){  
                // Checks every agv if it's available
                for(Vehicle agv : agvList){
                    // When the agv is available
                    if(((AGV)agv).Available()){
                        //Sets the destination of the AGV
                        agv.setDestination(message.DestinationNode());
                        //Copies the message to the message queue
                        ((AGV)agv).SendMessage(message);
                        // When it's a fetch message send a delivery message
                        if(message.Fetch()){
                            boolean found = false;
                            if(message.DestinationObject().getClass().equals(Crane.class)){
                                for(Crane crane : craneList){
                                    //if(crane.Available() && crane.)
                                }
                            }
                            else if (message.DestinationObject().getClass().equals(StorageCrane.class)){
                                for(StorageCrane crane : storageCranes){
                                    if(crane.Available()){
                                        crane.SendMessage(new Message(
                                                crane,
                                                agv,
                                                Message.ACTION.Unload,
                                                message.GetContainer()));
                                        ((AGV)agv).SendMessage(new Message(
                                                crane,
                                                agv,
                                                Message.ACTION.Unload,
                                                message.GetContainer()));
                                        found = true;
                                        break;
                                    }
                                }
                                if(!found){
                                    messageQueue.add(new Message(
                                            agv,
                                            StorageCrane.class,
                                            Message.ACTION.Unload,
                                            message.GetContainer()));
                                }
                            }
                        }
                        //Message was handeld so remove it
                        messageQueue.remove(message);
                    }
                }
            }
            // When the message requests a crane
            else if(message.RequestedObject() == Crane.class){
                // Check every crane in the list
                for(Crane crane : craneList){
                    // When the crane is assigned to this parkinglot
                    if(crane.parkinglotTransport.node == message.DestinationNode()){
                        // Makes a new message for the crane
                        crane.SendMessage(message);                            
                        // Request an AGV to fetch the first container
                        if(message.GetAction() == Message.ACTION.Unload){
                            messageQueue.add(new Message(
                                crane,
                                AGV.class,
                                Message.ACTION.Fetch,
                                null));
                        }                            
                        //Message was handeld so remove it
                        messageQueue.remove(message);
                    }                    
                }
            }
        }
    }
    
    /**
     * Updates all the vehicles that still need to arrive
     * When it arrives messageQueue will be send so the cranes will go to work
     */
    private void UpdateShipment() throws Exception{     
        // When a schip Arrives send 10 cranes
        seaShipsToArrive = CheckArrival(seaShipsToArrive, 10);
        // When a barges Arrives send 4 cranes
        bargesToArrive = CheckArrival(bargesToArrive, 4);
        // When a train Arrives send 2 cranes
        trainsToArrive = CheckArrival(trainsToArrive, 2);
        // When a truck Arrives send a crane
        trucksToArrive = CheckArrival(trucksToArrive, 1);
    }
    
    /**
     * Checks if from the given list vehicles are arriving
     * @param toCheck The list to check
     * @return The list without the arrived vehicles
     * @throws Exception 
     */
    private List<TransportVehicle> CheckArrival(List<TransportVehicle> toCheck, int requests) throws Exception{
        if (toCheck == null){
            throw new Exception("toCheck isn't initialized");
        }
        // Checks if seaShips arrive
        if(toCheck.size() > 0){
            // When the simulation time is equal or greater than the arrivalDate
            while(simulationTime.getTime() >= toCheck.get(0).GetArrivalDate().getTime()){
                // Add the vehicle that arrived
                presentVehicles.add(toCheck.get(0));
                // Request cranes
                for(int i = 0 ; i < requests; i++){
                    messageQueue.add(new Message(
                        toCheck.get(0),
                        Crane.class,
                        Message.ACTION.Unload,
                        null));
                }
                // Removes the vehicle that arrived
                toCheck.remove(0);
                // When there are no vehicles left
                if(toCheck.isEmpty()){
                    break;
                }
            }
        }
        return toCheck;
    }
    
    /**
     * Checks all the containers that are on top of each stack
     * When it needs to be transported it will sends a message
     * @throws Exception 
     */
    private void FetchContainers() throws Exception{
        
        // Walks around the whole storage area and checks every contianer
//        for(int column = 0; column< storageArea.getWidth(); column++){
//            for(int row =0;row< storageArea.getLength(); row++){
//                if(!storageArea.rowEmpty(column)){
//                    // Checks if the cotainer needs to be transported
//                    if(storageArea.PeekContainer(column, row).getDepartureDateStart().getTime() <= simulationTime.getTime()){                    
//                        // Adds a fetch message for an AGV
//                        messageQueue.add(new Message(
//                                storageArea.getClass(),
//                                AGV.class,
//                                Message.ACTION.Fetch,
//                                storageArea.PeekContainer(column, row)));                        
//                    }
//                }
//            }
//        }
    }
        
    /**
     * Get's the next delivery time 
     */
    private void GetNextDeliverDate() throws Exception{
        // Krijg ik nog van tonnie
        
        
//        Date nextDate = new Date();
//        // Walks around the whole storage area and checks every contianer
//        for(int column = 0; column< storageArea.getWidth(); column++){
//            for(int row =0;row< storageArea.getLength(); row++){
//                if(!storageArea.rowEmpty(column)){
//                    if(nextDate.equals(new Date())){
//                        nextDate = storageArea.PeekContainer(column, row).getDepartureDateStart();
//                    }
//                    else if(nextDate.getTime() > storageArea.PeekContainer(column, row).getDepartureDateStart().getTime()){
//                        nextDate = storageArea.PeekContainer(column, row).getDepartureDateStart();
//                    }
//                }
//            }
//        }
    }
    
    /**
     * Gets the date when the next shipment arrives
     */
    private void GetNextArrivalDate(){
        // When there are still seaShips that need to arrive
        if(!seaShipsToArrive.isEmpty()){
            shipmentTime = seaShipsToArrive.get(0).GetArrivalDate();
        }
        // When there are still barges that need to arrive
        if(!bargesToArrive.isEmpty()){
            // When the first barge arrives earlier than the other shipment
            if(shipmentTime.getTime() > bargesToArrive.get(0).GetArrivalDate().getTime()){
                shipmentTime = bargesToArrive.get(0).GetArrivalDate();
            }
        }
        // When there are still trains that need to arrive
        if(!trainsToArrive.isEmpty()){
            // When the first train arrives earlier than the other shipment
            if(shipmentTime.getTime() > trainsToArrive.get(0).GetArrivalDate().getTime()){
                shipmentTime = trainsToArrive.get(0).GetArrivalDate();
            }
        }
        // When there are still trucks that need to arrive
        if(!trucksToArrive.isEmpty()){
            // When the first truck arrives earlier than the other shipment
            if(shipmentTime.getTime() > trucksToArrive.get(0).GetArrivalDate().getTime()){
                shipmentTime = trucksToArrive.get(0).GetArrivalDate();
            }
        }
    }
    
    /**
     * Sets the simulation seconds increment time
     * @param value Value from 0 till 100
     */
    public void SetSecondsIncrement(int value){
        // When the value is below 0
        if(value < 0){
            return;
        }        
        // When the value is above 100
        if(value > 100 ){
            secondsToIncrement = 100;
        }
        else{
            secondsToIncrement = value;
        }
    }
    
    /**
     * Gets the simulation seconds increment time
     * @return seconds increment
     */
    public int GetSecondsIncrement(){
        return secondsToIncrement;
    }
}
