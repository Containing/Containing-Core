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
    // Updates the update method
    updateTimer timer;
    
    List<Vehicle> dockedVehicles;
    // List with all AGV's
    List<Vehicle> agvList;
    // List with all Cranes
    List<Crane> craneList;
    // List with all current messages
    List<Message> messages;
    
    // The storage area where all the containers are stored
    Storage_Area storageArea;
    
    // Simulation Time
    Date simulationTime;
    // Date when the controller needs to send containers
    Date deliveryTime;
    
    // List with all the Vehicles that can arrive
    List<Boat> allSeaShips;
    List<Boat> allBarges;
    List<Train> allTrains;
    List<Truck> allTrucks;
        
    public int SecondsIncrement;
    
    /**
     * Class Constructor,
     * Call's the initialize method
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
        simulationTime = Container.df.parse("04-12-01 00:00");        
        deliveryTime = new Date();
        
        SecondsIncrement = 1;
        
        // Initiallizes the List's variables
        messages = new ArrayList();
        dockedVehicles = new ArrayList();        
        agvList = new ArrayList();
        craneList = new ArrayList();      
        
        // Loads the database dump
        Database.restoreDump();
        // Loads all the vehicles that come to the harbor
        allSeaShips = GenerateVehicles.GetSeaBoats();
        allBarges = GenerateVehicles.GetInlandBoats();
        allTrains = GenerateVehicles.GetTrains();
        allTrucks = GenerateVehicles.GetTrucks();
        
        // Add's 100 AGV's to the class all waiting
        for(int i = 0; i < 100; i++){
            agvList.add(new AGV(new Node(1,0)));
        }
        
        /**
         * TODO: Initialize the positions where the cranes need to stand
         * Initialize the map with all his nodes and parkinglots
         */
        
        //Er zijn in totaal  10 zeeschipkranen, 8 binnenvaartkranen, 4 treinkranen en 20 truckkranen 
        for(int i = 0; i < 10; i++){    
            // Add's 10 seaShipCranes
            //craneList.add(Crane);
        }
        for(int i = 0 ; i < 8; i++){     
            // Add's 8 BargeCranes
            //craneList.add(Crane);
        }        
        for(int i  =0 ; i < 4; i++){         
            // Add's 10 trianCranes
            //craneList.add(Crane);
        }        
        for (int i = 0; i < 20; i++){          
            // Add's 20 truckCranes
            //craneList.add(Crane);
        }
        
        // To do Generate all vehicles with their containers
        
    }
    
    /**
     * Updates class logic
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
        for(Vehicle vehicle : dockedVehicles){
            vehicle.update(gameTime);
        }        
        
        UpdateAllVehicles();
        
        // When the simulation time is equal or greater than the deliveryTime
        if(simulationTime.getTime() >= deliveryTime.getTime())
        {
            // Gets all the top containers that need to be transported
            FetchContainers();
            // Gets the next date when the next shipment needs to be transported
            NextDeliveryDate();
            
            /**
             * TODO : 
             * When a container is fetch check if the container below if it needs to be fetched
             */
        }
        
        // Check's every message
        for(Message mess : messages)
        {
            // When this message is an agv request 
            if(mess.RequestedObject() == AGV.class)
            {  
                for(Vehicle agv : agvList)
                {
                    // When the agv has nothing to do
                    if(((AGV)agv).Available())
                    {
                        //Set's the destination of the AGV
                        agv.setDestination(mess.destinationNode());
                        //Copy's the message to the message que
                        ((AGV)agv).SendMessage(mess);
                        //Message was handeld so remove it
                        messages.remove(mess);
                        
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
            // When a crane is requested
            else if(mess.RequestedObject() == Crane.class)
            {
                // Check every crane in the list
                for(Crane crane : craneList){
                    // When the crane is available
                    if(crane.Available()){
                        // When the crane is assigned to this parkinglot
                        if(crane.parkinglotTransport.node == mess.destinationNode()){
                            // Makes a new message for the crane
                            crane.SendMessage(new Message(
                                    mess.DestinationObject(),
                                    mess.Action()));
                            
                            // Request an AGV to fetch the first container
                            if(mess.Action() == Message.ACTION.Unload){
                                messages.add(new Message(
                                    crane,
                                    AGV.class,
                                    Message.ACTION.Fetch));
                            }
                            
                            //Message was handeld so remove it
                            messages.remove(mess);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Updates all the vehicles that still need to arrive
     */
    private void UpdateAllVehicles()
    {
        // Checks the trucks
        if(allTrucks.size() > 0){
            while(simulationTime.getTime() >= allTrucks.get(0).GetArrivalDate().getTime())
            {
                VehicleDocking(allTrucks.get(0));
                allTrucks.remove(0);
                if(allTrucks.isEmpty())
                    break;
            }
        }
        // Checks the trains
        if(allTrains.size() > 0){
            while(simulationTime.getTime() >= allTrains.get(0).GetArrivalDate().getTime())
            {
                VehicleDocking(allTrains.get(0));
                allTrains.remove(0);
                if(allTrains.isEmpty())
                    break;
            }
        }
        // Checks the barges
        if(allBarges.size() > 0){
            while(simulationTime.getTime() >= allBarges.get(0).GetArrivalDate().getTime())
            {
                VehicleDocking(allBarges.get(0));
                allBarges.remove(0);
                if(allBarges.isEmpty())
                    break;
            }
        }
        // Checks the seaShips
        if(allSeaShips.size() > 0){
            while(simulationTime.getTime() >= allSeaShips.get(0).GetArrivalDate().getTime())
            {
                VehicleDocking(allSeaShips.get(0));
                allSeaShips.remove(0);
                if(allSeaShips.isEmpty())
                    break;
            }
        }
    }
    
    
    /**
     * Checks all the containers that are on top of each stack
     * When it needs to be transported it sends a message
     * @throws Exception 
     */
    private void FetchContainers() throws Exception
    {
        // Walks around the whole storage area and checks every contianer
        for(int column = 0; column< storageArea.getWidth(); column++){
            for(int row =0;row< storageArea.getLength(); row++){
                if(!storageArea.rowEmpty(column)){
                    // Checks if the cotainer needs to be transported
                    if(storageArea.peakContainer(column, row).getDepartureDateStart().getTime() <= simulationTime.getTime()){                    
                        // Adds a fetch message for an AGV
                        messages.add(new Message(
                                storageArea.getClass(),
                                AGV.class,
                                Message.ACTION.Fetch,
                                storageArea.peakContainer(column, row)));                        
                    }
                }
            }
        }
    }
    
    /**
     * Get's the next delivery date 
     */
    private void NextDeliveryDate() throws Exception
    {
        Date nextDate = new Date();
        // Walks around the whole storage area and checks every contianer
        for(int column = 0; column< storageArea.getWidth(); column++){
            for(int row =0;row< storageArea.getLength(); row++){
                if(!storageArea.rowEmpty(column)){
                    if(nextDate.equals(new Date())){
                        nextDate = storageArea.peakContainer(column, row).getDepartureDateStart();
                    }
                    else if(nextDate.getTime() > storageArea.peakContainer(column, row).getDepartureDateStart().getTime()){
                        nextDate = storageArea.peakContainer(column, row).getDepartureDateStart();
                    }
                }
            }
        }
    }
    
    /**
     * When a vehicle dock's
     * @param vehicle 
     */
    public void VehicleDocking(Vehicle vehicle)
    {      
        // Add the docked Vehicle
        dockedVehicles.add(vehicle);
        // Interger that holds the amount of cranes this vehicle can hold for loading and unloading
        int craneRequests = 0;
        
        //When this vehicle is a Train
        if(vehicle.getClass() == Train.class)
        {
            // Train's can hold 2 cranes
            craneRequests = 2;
        }
        // When this vehicle is a Boat
        else if (vehicle.getClass() == Boat.class)
        {            
            //TODO Boat crane request
            //TODO 2 different types of boat's a SeaShip, Barge
        }
        // When this vehicle is a Truck
        else if (vehicle.getClass() == Truck.class)
        {
            // Truck's can hold 1 crane
            craneRequests = 1;
        }
        
        // While the vehicle can hold more cranes            
        while(craneRequests-- > 0){
            messages.add(new Message(
                 vehicle,
                 Crane.class,
                 Message.ACTION.Unload));
        }
    }
}
