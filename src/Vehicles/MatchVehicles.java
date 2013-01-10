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
        List<TransportVehicle> SeaBoats = GetSeaBoats();
        for (TransportVehicle TranspottVehicle : SeaBoats) {
            System.out.println(TranspottVehicle.GetArrivalDate() + " <-> " + TranspottVehicle.GetDepartureDate() + " Count:" + TranspottVehicle.storage.Count());
        }
        System.out.println("SeaBoats:    "+SeaBoats.size());
        List<TransportVehicle> InlandBoats = GetInlandBoats();
        for (TransportVehicle TranspottVehicle : InlandBoats) {
            System.out.println(TranspottVehicle.GetArrivalDate() + " <-> " + TranspottVehicle.GetDepartureDate() + " Count:" + TranspottVehicle.storage.Count());
        }
        System.out.println("InlandBoats: "+InlandBoats.size());
        List<TransportVehicle> Trains = GetTrains();
        for (TransportVehicle TranspottVehicle : Trains) {
            System.out.println(TranspottVehicle.GetArrivalDate() + " <-> " + TranspottVehicle.GetDepartureDate() + " Count:" + TranspottVehicle.storage.Count());
        }
        System.out.println("Trains:      "+Trains.size());
        List<TransportVehicle> Trucks = GetTrucks();
        for (TransportVehicle TranspottVehicle : Trucks) {
            System.out.println(TranspottVehicle.GetArrivalDate() + " <-> " + TranspottVehicle.GetDepartureDate() + " Count:" + TranspottVehicle.storage.Count());
        }
        System.out.println("Trucks:      "+Trucks.size());
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
        return transportVehicleList;
    }
    
    public static List<TransportVehicle> GetSeaBoats() throws Exception{
        return MatchVehicles(GenerateArrivalVehicles.GetSeaBoats(), GenerateDepartureVehicles.GetSeaBoats());
    }

    public static List<TransportVehicle> GetInlandBoats() throws Exception{
        return MatchVehicles(GenerateArrivalVehicles.GetInlandBoats(), GenerateDepartureVehicles.GetInlandBoats());
    }
    
    public static List<TransportVehicle> GetTrains() throws Exception{
        return MatchVehicles(GenerateArrivalVehicles.GetTrains(), GenerateDepartureVehicles.GetTrains());
    }

    public static List<TransportVehicle> GetTrucks() throws Exception{
        return MatchVehicles(GenerateArrivalVehicles.GetTrucks(), GenerateDepartureVehicles.GetTrucks());
    }
    
    private static boolean DateBefore(Date Start, Date End){
        return Start.before(End);
    }
    private static boolean DateEqual(Date Start, Date End){
        return Start.equals(End);
    }
}
