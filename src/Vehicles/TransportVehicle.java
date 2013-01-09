package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Storage.Storage_Area;
import java.util.Date;

/**
 * An extension of the vehicle class.
 * @author Tonnie Boersma
 */
public abstract class TransportVehicle extends Vehicle {
    
    protected Date arrivalDate;
    protected Date departureDate;
    protected String arrivalCompany;
    protected float speed/*= X*/;
    
    public void setPostion(Vector3f position){
        this.position = position;        
    
    }
    @Override
    public void update(int gameTime) throws Exception {
        if (position == destination.getPosition()){
            // send message
            // wait for message depart
        }
        else{
            // follow route 
            // update position
        } 
    }
    
    /**
     * Get the arrival date.
     * @return arrivalDate.
     */
    public Date GetArrivalDate(){
        return arrivalDate;
    }

    /**
     * Get the departure date.
     * @return departureDate.
     */
    public Date GetDepartureDate(){
        return departureDate;
    }
    
    /**
     * Get the Company.
     * @return Company.
     */
    public String GetCompany(){
        return arrivalCompany;
    }
    
    /**
     * Matches the container properties with the vehicle properties,
     * @param container The container to match.
     * @return True if the container matches the vehicle, false otherwise.
     */
    public boolean MatchesContainer(Container container){        
        return this.GetArrivalDate().equals(container.getArrivalDateStart()) && 
                this.GetDepartureDate().equals(container.getArrivalDateEnd()) &&
                this.GetCompany().equals(container.getArrivalCompany());
    }
}
