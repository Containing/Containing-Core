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
        List<TransportVehicle> Trains = GetTrains();
        System.out.println("Trains:      "+Trains.size());
//        List<Truck> Trucks = GetTrucks();
//        System.out.println("Trucks:      "+Trucks.size());
    }
   
//    private static <T> List<T> Compare(List<T> BoatList, List<T> arrivalBoatList, List<T> departureBoatList){        
//        while(arrivalBoatList.size() > 0 || departureBoatList.size() > 0){    
//            if (arrivalBoatList.isEmpty()){
//                BoatList.add(departureBoatList.get(0));
//                departureBoatList.remove(0);
//            }
//            else if (departureBoatList.isEmpty()){
//                BoatList.add(arrivalBoatList.get(0));
//                arrivalBoatList.remove(0);
//            }
//            else{
//                
//                Date ss = ((TransportVehicle)arrivalBoatList.get(0)).GetArrivalDate();
//                Date se = ((TransportVehicle)arrivalBoatList.get(0)).GetDepartureDate();
//                Date es = ((TransportVehicle)departureBoatList.get(0)).GetArrivalDate();
//                Date ee = ((TransportVehicle)departureBoatList.get(0)).GetDepartureDate();
//                
//                if (DateEquel(ss, es)){
//                    BoatList.add(arrivalBoatList.get(0));
//                    arrivalBoatList.remove(0);
//                    departureBoatList.remove(0);
//                }
//                else
//                {
//                    if(DateBefore(ss, es)){
//                        BoatList.add(arrivalBoatList.get(0));
//                        arrivalBoatList.remove(0);
//                    }
//                    else{
//                        BoatList.add(departureBoatList.get(0));
//                        departureBoatList.remove(0);
//                    }
//                }
//            }
//        }
//        
//        for (T boat : BoatList) {
//            System.out.println(((TransportVehicle)boat).GetArrivalDate() + " <-> " + 
//                    ((TransportVehicle)boat).GetDepartureDate()+ " Count:" + 
//                    boat.equals(Boat.class) ?((Boat)boat).storage.Count() : ());
//        }
//        
//        return BoatList;
//    }
    
    private static List<TransportVehicle> MatchVehicles(List<TransportVehicle> arrivalList, List<TransportVehicle> departureList){
        ArrayList<TransportVehicle> transportVehicleList = new ArrayList<>();
        if (arrivalList.isEmpty() && departureList.isEmpty()){
            return transportVehicleList;
        }
        if (arrivalList.isEmpty()){
            return departureList;
        }
        if (departureList.isEmpty()){
            return arrivalList;
        }
        
        while(arrivalList.size() > 0 || departureList.size() > 0){    
            if (arrivalList.isEmpty()){
                transportVehicleList.add(departureList.get(0));
                departureList.remove(0);
            }
            else if (departureList.isEmpty()){
                transportVehicleList.add(arrivalList.get(0));
                arrivalList.remove(0);
            }
            else{
                
                Date ss = arrivalList.get(0).GetArrivalDate();
                Date es = departureList.get(0).GetArrivalDate();
                
                if (DateEqual(ss, es)){
                    transportVehicleList.add(arrivalList.get(0));
                    arrivalList.remove(0);
                    departureList.remove(0);
                }
                else
                {
                    if(DateBefore(ss, es)){
                        transportVehicleList.add(arrivalList.get(0));
                        arrivalList.remove(0);
                    }
                    else{
                        transportVehicleList.add(departureList.get(0));
                        departureList.remove(0);
                    }
                }
            }
        }
        for (TransportVehicle TranspottVehicle : transportVehicleList) {
            System.out.println(TranspottVehicle.GetArrivalDate() + " <-> " + TranspottVehicle.GetDepartureDate() + " Count:" + TranspottVehicle.storage.Count());
        }
        return transportVehicleList;
    }
    
    public static List<TransportVehicle> GetSeaBoats() throws Exception{
        return null;
    }

    public static List<TransportVehicle> GetInlandBoats() throws Exception{
        return null;
    }
    /*
    private static List<Boat> GetBoats(String kindShip) throws Exception{
        ArrayList<Boat> BoatList = new ArrayList<>();
        List<Boat> arrivalBoatList = kindShip.equals("zeeschip") ? GenerateArrivalVehicles.GetSeaBoats() :GenerateArrivalVehicles.GetInlandBoats();
        List<Boat> departureBoatList = kindShip.equals("zeeschip") ? GenerateDepartureVehicles.GetSeaBoats() :GenerateDepartureVehicles.GetInlandBoats();
        if (arrivalBoatList.isEmpty() && departureBoatList.isEmpty()){
            return BoatList;
        }
        if (arrivalBoatList.isEmpty()){
            return departureBoatList;
        }
        if (departureBoatList.isEmpty()){
            return arrivalBoatList;
        }
        //return Compare(BoatList, arrivalBoatList, departureBoatList);
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
                
                if (DateEqual(ss, es)){
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
    */
    public static List<TransportVehicle> GetTrains() throws Exception{
        ArrayList<TransportVehicle> TrainList = new ArrayList<>();
        return TrainList;
    }

    public static List<TransportVehicle> GetTrucks() throws Exception {
        ArrayList<TransportVehicle> TruckList = new ArrayList<>();
        return TruckList;
    }
    
    private static boolean DateBefore(Date Start, Date End){
        return Start.before(End);
    }
    private static boolean DateEqual(Date Start, Date End){
        return Start.equals(End);
    }
}
