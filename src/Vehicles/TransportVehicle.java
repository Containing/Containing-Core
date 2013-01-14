package Vehicles;

import Helpers.Vector3f;
import Main.Container;
import Pathfinding.Node;
import Storage.Storage_Area;
import java.util.Date;

/**
 * An extension of the vehicle class.
 * @author Tonnie Boersma
 */
public class TransportVehicle extends Vehicle {
    
    protected Date arrivalDate;
    protected Date departureDate;
    protected String arrivalCompany;
    protected float speed = 5f;
    
    public TransportVehicle(Date arrivalDate, Date departureDate, String arrivalCompany, VehicleType vehicleType, Vector3f containerArraySize, Node startPosition) throws Exception
    {
        if (arrivalDate == null || departureDate == null || arrivalCompany == null || containerArraySize == null || startPosition == null || vehicleType == null){
            throw new Exception("\nThe input variable can't be null:"+
                    "\narrivalDate: " + arrivalDate +
                    "\ndepartureDate: " + departureDate +
                    "\narrivalCompany: " + arrivalCompany +
                    "\nvehicleType: " + vehicleType +
                    "\ncontainerArraySize: " + containerArraySize +
                    "\nstartPosition: " + startPosition);
        }
        else{
            this.position = startPosition.getPosition();
            this.destination = startPosition;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
            this.arrivalCompany = arrivalCompany;
            this.vehicleType = vehicleType;
            this.storage = new Storage_Area((int)containerArraySize.x, (int)containerArraySize.z, (int)containerArraySize.y, position);
        }
    }
    
    public void setPostion(Vector3f position){
        this.position = position;        
    
    }
    @Override
    public void update(float gameTime) throws Exception {
        if (position == destination.getPosition()){
            // send message
            // wait for message depart
        }
        else if(position == route[routeIndex].getPosition()){
            routeIndex++;
        }
        else{
            Vector3f NextNode = route[routeIndex].getPosition();
            Vector3f diff = new Vector3f(   NextNode.x - this.getPosition().x,
                                            NextNode.y - this.getPosition().y,
                                            NextNode.z - this.getPosition().z);
            diff.normalize();
            diff.x*=gameTime*speed;
            diff.y*=gameTime*speed;
            diff.z*=gameTime*speed;
            
            Vector3f temp = new Vector3f(position);
            temp.AddVector3f(diff);

            if (Vector3f.distance(getPosition(), temp) < Vector3f.distance(getPosition(), NextNode)){
                this.position.AddVector3f(diff);
            }
            else{
                this.position = NextNode;
            }
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
    
    @Override	
    public String toString(){
        return  "\n" + Container.df.format(arrivalDate) + " <-> " + Container.df.format(departureDate) +
                "\n" + "ArrivalCompany: " + arrivalCompany +
                "\n" + "ContainerfieldLenght: " + storage.getLength() + 
                "\n" + "ContainerfieldWidth: " + storage.getWidth() + 
                "\n" + "ContainerfieldHeight: " + storage.getHeight() + 
                "\n" + "_____________________________(" + storage.Count() + ")"+ 
                "\n" + storage;
    }
}
