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
import Crane.Crane;


/**
 *
 * @author Martin_Notebook
 */
public class Controller {
    // The updateTimer class, Updates the update method
    updateTimer timer;
    
    // List with all the present transport vehicles
    List<Vehicle> presentVehicles;
    // List with all AGVs
    List<Vehicle> agvList;
    // List with all Cranes
    List<Crane> craneList;
    // List with all the messages for the controller
    List<Message> messageQueue;
    
    // The storage area where all the containers are stored
    Storage_Area storageArea;
    
    // The simulation time of this application
    Date simulationTime;
    // The date when the next container needs to be send
    Date deliveryTime;
    // The date when the next shipment arrives
    Date nextShipment;
    
    // List with all the Vehicles that will arrive
    List<Boat> allSeaShips;
    List<Boat> allBarges;
    List<Train> allTrains;
    List<Truck> allTrucks;
    
    /**
     * The amount of seconds the simulation time will increment after each update
     */
    public int SecondsIncrement;
    
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
        // Default increment value
        SecondsIncrement = 1;
        
        // Initializes new ArrayLists
        messageQueue = new ArrayList();
        presentVehicles = new ArrayList();        
        agvList = new ArrayList();
        craneList = new ArrayList();      
        
        // Loads or creates a new database dump
        try{
            // Loads the database dump
            Database.restoreDump();
        }
        catch(Exception ex){
            //XML.XMLBinder.GenerateContainerDatabase();
            Database.dumpDatabase();         
        }
        
        // Loads all the vehicles that come to the harbor
        allSeaShips = GenerateVehicles.GetSeaBoats();
        allBarges = GenerateVehicles.GetInlandBoats();
        allTrains = GenerateVehicles.GetTrains();
        allTrucks = GenerateVehicles.GetTrucks();
        
        // Initializes the first shipment time
        GetNextShipmentTime();
            
        // Sets the simulationTime 1 hour before the first shipment
        simulationTime = nextShipment;
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
    
    /**
     * Updates simulation logic
     * @param gameTime 
     */
    public void Update(float gameTime ) throws Exception
    {
        System.out.println(simulationTime);
        simulationTime.setSeconds(simulationTime.getSeconds() + SecondsIncrement);
        System.out.println(gameTime);
        
        // Updates the logic of each AGV
        for(Vehicle agv : agvList){
            agv.update(gameTime);
        }
        // Updates the logic of each crane
        for(Crane crane : craneList){
            crane.update(gameTime);
        }
        // Updates the logic of each docked vehicle
        for(Vehicle vehicle : presentVehicles){
            vehicle.update(gameTime);
        }        
        
        // When the next shipment arrives
        if(simulationTime.getTime() >= nextShipment.getTime()){
            UpdateShipment();
            GetNextShipmentTime();
        }        
        // When the simulation time is equal or greater than the deliveryTime
        if(simulationTime.getTime() >= deliveryTime.getTime()){
            // Gets all the top containers that need to be transported
            FetchContainers();
            // Gets the next date when the next shipment needs to be transported
            GetNextDeliveryTime();
            
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
    private void UpdateMessages() throws Exception
    {
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
                        //Message was handeld so remove it
                        messageQueue.remove(message);
                        
                        /**
                         * 
                         * 
                         * TODO: When it's a fetch assignment add also a delivery assignment 
                         * 
                         * 
                         */
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
    private void UpdateShipment() throws Exception
    {
        // Checks if trucks arrive
        if(allTrucks.size() > 0){
            // When the simulation time is equal or greater than the arrivalDate
            while(simulationTime.getTime() >= allTrucks.get(0).GetArrivalDate().getTime()){
                // Add the truck that arrived
                presentVehicles.add(allTrucks.get(0));
                // Request 1 crane
                messageQueue.add(new Message(
                    allTrucks.get(0),
                    Crane.class,
                    Message.ACTION.Unload,
                    null));
                // Remove the truck that arrived
                allTrucks.remove(0);
                // When there are no trucks left 
                if(allTrucks.isEmpty()){
                    break;
                }
            }
        }
        // Checks if trains arrive
        if(allTrains.size() > 0){
            // When the simulation time is equal or greater than the arrivalDate
            while(simulationTime.getTime() >= allTrains.get(0).GetArrivalDate().getTime()){
                // Add the train that arrived
                presentVehicles.add(allTrains.get(0)); 
                // Request 2 cranes
                for(int i = 0; i < 2; i++){
                    messageQueue.add(new Message(
                        allTrains.get(0),
                        Crane.class,
                        Message.ACTION.Unload,
                        null));
                }
                // Removes the train that arrived
                allTrains.remove(0);
                // When there are no trains left
                if(allTrains.isEmpty()){
                    break;
                }
            }
        }
        // Checks if barges arrive
        if(allBarges.size() > 0){
            // When the simulation time is equal or greater than the arrivalDate
            while(simulationTime.getTime() >= allBarges.get(0).GetArrivalDate().getTime()){
                // Add the barge that arrived
                presentVehicles.add(allBarges.get(0)); 
                // Request 4 cranes
                for(int i = 0; i < 4; i++){
                    messageQueue.add(new Message(
                        allBarges.get(0),
                        Crane.class,
                        Message.ACTION.Unload,
                        null));
                }
                // Removes the barge that arrived
                allBarges.remove(0);
                // When there are no barges left
                if(allBarges.isEmpty()){
                    break;
                }
            }
        }
        // Checks if seaShips arrive
        if(allSeaShips.size() > 0){
            // When the simulation time is equal or greater than the arrivalDate
            while(simulationTime.getTime() >= allSeaShips.get(0).GetArrivalDate().getTime()){
                // Add the seaShip that arrived
                presentVehicles.add(allSeaShips.get(0));
                // Request 10 cranes
                for(int i = 0 ; i < 10; i++){
                    messageQueue.add(new Message(
                        allSeaShips.get(0),
                        Crane.class,
                        Message.ACTION.Unload,
                        null));
                }
                // Removes the seaShip that arrived
                allSeaShips.remove(0);
                // When there are no seaShips left
                if(allSeaShips.isEmpty()){
                    break;
                }
            }
        }
    }
    
    
    /**
     * Checks all the containers that are on top of each stack
     * When it needs to be transported it will sends a message
     * @throws Exception 
     */
    private void FetchContainers() throws Exception
    {
        // Walks around the whole storage area and checks every contianer
        for(int column = 0; column< storageArea.getWidth(); column++){
            for(int row =0;row< storageArea.getLength(); row++){
                if(!storageArea.rowEmpty(column)){
                    // Checks if the cotainer needs to be transported
                    if(storageArea.PeekContainer(column, row).getDepartureDateStart().getTime() <= simulationTime.getTime()){                    
                        // Adds a fetch message for an AGV
                        messageQueue.add(new Message(
                                storageArea.getClass(),
                                AGV.class,
                                Message.ACTION.Fetch,
                                storageArea.PeekContainer(column, row)));                        
                    }
                }
            }
        }
    }
       
    
    /**
     * When a vehicle arrives in the harbor
     * @param vehicle The vehicle that arrives in the border
     */
    public void VehicleArrives(Vehicle vehicle) throws Exception
    {      
        // Add the arrived vehicle to the present Vehicles 
        presentVehicles.add(vehicle);
        // Holds the amount of cranes the arrived vehicle can hold for loading and unloading
        int cranesRequested = 0;
        
        //When the vehicle is a train
        if(vehicle.getClass() == Train.class){
            // A train can hold 2 cranes
            cranesRequested = 2;
        }
        // When the vehicle is a boat
        else if (vehicle.getClass() == Boat.class){            
            
            //TODO Boat crane request
            //TODO 2 different types of boat's a SeaShip, Barge
        }
        // When this vehicle is a Truck
        else if (vehicle.getClass() == Truck.class)
        {
            // Truck's can hold 1 crane
            cranesRequested = 1;
        }
        
        // While the vehicle can hold more cranes            
        while(cranesRequested-- > 0){
            messageQueue.add(new Message(
                 vehicle,
                 Crane.class,
                 Message.ACTION.Unload,
                 null));
        }
    }
    
    /**
     * Get's the next delivery time 
     */
    private void GetNextDeliveryTime() throws Exception
    {
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
    private void GetNextShipmentTime()
    {
        // When there are still seaShips that need to arrive
        if(!allSeaShips.isEmpty()){
            nextShipment = allSeaShips.get(0).GetArrivalDate();
        }
        // When there are still barges that need to arrive
        if(!allBarges.isEmpty()){
            // When the first barge arrives earlier than the other shipment
            if(nextShipment.getTime() > allBarges.get(0).GetArrivalDate().getTime()){
                nextShipment = allBarges.get(0).GetArrivalDate();
            }
        }
        // When there are still trains that need to arrive
        if(!allTrains.isEmpty()){
            // When the first train arrives earlier than the other shipment
            if(nextShipment.getTime() > allTrains.get(0).GetArrivalDate().getTime()){
                nextShipment = allTrains.get(0).GetArrivalDate();
            }
        }
        // When there are still trucks that need to arrive
        if(!allTrucks.isEmpty()){
            // When the first truck arrives earlier than the other shipment
            if(nextShipment.getTime() > allTrucks.get(0).GetArrivalDate().getTime()){
                nextShipment = allTrucks.get(0).GetArrivalDate();
            }
        }
    }
}
