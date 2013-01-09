package Vehicles;

import Main.Database;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tonnie Boersma
 */
public class MatchVehicles {
        /**
     * Test the code
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Database.restoreDump();
//        List<Boat> SeaBoats = GetSeaBoats();
//        System.out.println("SeaBoats:    "+SeaBoats.size());
//        List<Boat> InlandBoats = GetInlandBoats();
//        System.out.println("InlandBoats: "+InlandBoats.size());
        List<Train> Trains = GetTrains();
        System.out.println("Trains:      "+Trains.size());
//        List<Truck> Trucks = GetTrucks();
//        System.out.println("Trucks:      "+Trucks.size());
    }
    
    public static List<Boat> GetSeaBoats() throws Exception{
        return GetBoats("zeeschip");
    }

    public static List<Boat> GetInlandBoats() throws Exception{
        return GetBoats("binnenschip");
    }
    
    private static List<Boat> GetBoats(String kindSchip) throws Exception{
        ArrayList<Boat> BoatList = new ArrayList<>();
        List<Boat> arrivalBoatList = kindSchip.equals("zeeschip") ? GenerateArrivalVehicles.GetSeaBoats() :GenerateArrivalVehicles.GetInlandBoats();
        List<Boat> departureBoatList = kindSchip.equals("zeeschip") ? GenerateDepartureVehicles.GetSeaBoats() :GenerateDepartureVehicles.GetInlandBoats();
        if (arrivalBoatList.isEmpty() && departureBoatList.isEmpty()){
            return BoatList;
        }
        if (arrivalBoatList.isEmpty()){
            return departureBoatList;
        }
        if (departureBoatList.isEmpty()){
            return arrivalBoatList;
        }
        
        while(arrivalBoatList.size() > 0 || departureBoatList.size() > 0){    
            if (arrivalBoatList.isEmpty()){
                BoatList.add(departureBoatList.get(0));
                departureBoatList.remove(0);
            }
            else if (departureBoatList.isEmpty()){
                BoatList.add(arrivalBoatList.get(0));
                arrivalBoatList.remove(0);
            }
            else{
                
                Date ss = arrivalBoatList.get(0).GetArrivalDate();
                Date se = arrivalBoatList.get(0).GetDepartureDate();
                Date es = departureBoatList.get(0).GetArrivalDate();
                Date ee = departureBoatList.get(0).GetDepartureDate();
                
                if (DateEquel(ss, es)){
                    BoatList.add(arrivalBoatList.get(0));
                    arrivalBoatList.remove(0);
                    departureBoatList.remove(0);
                }
                else
                {
                    if(DateBefore(ss, es)){
                        BoatList.add(arrivalBoatList.get(0));
                        arrivalBoatList.remove(0);
                    }
                    else{
                        BoatList.add(departureBoatList.get(0));
                        departureBoatList.remove(0);
                    }
                }
            }
        }
        for (Boat boat : BoatList) {
            System.out.println(boat.GetArrivalDate() + " <-> " + boat.GetDepartureDate() + " Count:" + boat.storage.Count());
        }
        return BoatList;
    }

    public static List<Train> GetTrains() throws Exception{
        ArrayList<Train> TrainList = new ArrayList<>();

        for (Train train : TrainList) {
            System.out.println(train.GetArrivalDate() + " <-> " + train.GetDepartureDate() + " Count:" + train.storage.Count());
        }
        return TrainList;
    }

    public static List<Truck> GetTrucks() throws Exception {
        ArrayList<Truck> TruckList = new ArrayList<>();
        return TruckList;
    }
    private static boolean DateBefore(Date Start, Date End){
        return Start.before(End);
    }
    private static boolean DateEquel(Date Start, Date End){
        return Start.equals(End);
    }
}
