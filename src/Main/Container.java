package Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tonnie Boersma
 */
public class Container 
{
    /**
     * The possible Transport types
     */
    public enum TransportType {
        trein,
        zeeschip,
        binnenschip,
        vrachtauto 
    };
    
    /**
     * The date format used
     */
    public DateFormat df = new SimpleDateFormat("dd MM yy HH:mm");

    /**
     * The constructor
     * @param id An unique id
     */
    public Container(String id)
    {
        this.id = id;
    }
    
    /**
     * Sets all arrival data
     * @param arrivalDateStart The begin arrival date
     * @param arrivalDateEnd The end arrival date
     * @param arrivalTransportType The arrival Transport type
     * @param arrivalCompany The arrival company
     * @param arrivalPosition The arrival position
     */
    public void setArrival(Date arrivalDateStart, Date arrivalDateEnd, 
            TransportType arrivalTransportType, String arrivalCompany,
            Vector3f arrivalPosition)
    {
        this.arrivalDateStart = arrivalDateStart;
        this.arrivalDateEnd = arrivalDateEnd;
        this.arrivalTransportType = arrivalTransportType;
        this.arrivalCompany = arrivalCompany;
        this.arrivalPosition = arrivalPosition;
    }

    /**
     * Sets all owner information
     * @param owner The container owner
     * @param containNr the container number
     */
    public void setOwnerInformation(String owner, int containNr)
    {
        this.owner = owner;
        this.containerNr = containNr;
    }

    /**
     * Sets all departure data
     * @param departureDateStart The begin departure date
     * @param departureDateEnd The end departure date
     * @param departureTransportType the departure Transport type
     * @param departureCompany the departure company
     */
    public void setDeparture(Date departureDateStart, Date departureDateEnd,
            TransportType departureTransportType, String departureCompany)
    {
        this.departureDateStart = departureDateStart;
        this.departureDateEnd = departureDateEnd;
        this.departureTransportType = departureTransportType;
        this.departureCompany = departureCompany;
    }

    /**
     * Sets the size of the container
     * @param dimension The l b h sizes.
     */
    public void setDimension(Vector3f dimension)
    {
        this.dimension = dimension;
    }
    
    /**
     * Sets the weight information
     * @param empty The container weight without content
     * @param weight The container weight with content
     */
    public void setWeightInformation(int empty, int weight)
    {
        this.empty = empty;
        this.weight = weight;
    }
    
    /**
     * Sets the content information
     * @param name The name of the content
     * @param kind The type of content
     * @param danger The danger of the content
     */
    public void setContentInformation(String name, String kind, String danger)
    {
        this.name = name;
        this.kind = kind;
        this.danger = danger;
    }
    
    // <editor-fold defaultstate="collapsed" desc="private variabelen">
    // id
    private String id;
    
    // arival
    private Date arrivalDateStart;
    private Date arrivalDateEnd;
    private TransportType arrivalTransportType;
    private String arrivalCompany;
    private Vector3f arrivalPosition;
    
    // owner
    private String owner;
    private int containerNr;
    
    // departure
    private Date departureDateStart;
    private Date departureDateEnd;
    private TransportType departureTransportType;
    private String departureCompany;

    // dimension
    private Vector3f dimension;
    
    // weight
    private int empty;
    private int weight;
    
    // content
    private String name;
    private String kind;
    private String danger;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Get methoden">

    /**
     * Get id
     * @return id
     */
    public String getId(){
        return this.id;
    }

    /**
     * Get the begin arrival date
     * @return begin arrival date
     */
    public Date getArrivalDateStart(){
        return this.arrivalDateStart;
    }

    /**
     * Get the end arrival date
     * @return end arrival date
     */
    public Date getArrivalDateEnd(){
        return this.arrivalDateEnd;
    }

    /**
     * Get arrival Transport type
     * @return arrival Transport type
     */
    public TransportType getArrivalTransportType(){
        return this.arrivalTransportType;
    }
    
    /**
     * Get arrival company
     * @return arrival company
     */
    public String getArrivalCompany(){
        return this.arrivalCompany;
    }

    /**
     * Get arrival position
     * @return arrival position
     */
    public Vector3f getArrivalPosition(){
        return this.arrivalPosition;
    }
    
    /**
     * Get owner
     * @return owner
     */
    public String getOwner(){
        return this.owner;
    }

    /**
     * Get container number
     * @return container number
     */
    public int getContainNr(){
        return this.containerNr;
    }

    /**
     * Get the start departure date
     * @return start departure date
     */
    public Date getDepartureDateStart(){
        return this.departureDateStart;
    }

    /**
     * Get the end departure date
     * @return end departure date 
     */
    public Date getDepartureDateEnd(){
        return this.departureDateEnd;
    }

    /**
     * Get departure Transport type
     * @return departure Transport type
     */
    public TransportType getDepartureTransportType(){
        return this.departureTransportType;
    }

    /**
     * Get departure company
     * @return departure company
     */
    public String getDepartureCompany(){
        return this.departureCompany;
    }

    /**
     * Get l b h sizes.
     * @return dimensions
     */
    public Vector3f getDimension(){
        return this.dimension;
    }

    /**
     * Get container weight without content
     * @return empty
     */
    public int getEmpty(){
        return this.empty;
    }

    /**
     * Get container weight with content
     * @return weight
     */
    public int getWeight(){
        return this.weight;
    }
    
    /**
     * Get content name
     * @return name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Get content kind
     * @return kind
     */
    public String getKind(){
        return this.kind;
    }

    /**
     * Get content danger
     * @return danger
     */
    public String getDanger(){
        return this.danger;
    }
    // </editor-fold>
    
    @Override
    public String toString()
    {
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm");
        return "[ContainerNr:" + containerNr + "] [" + id + "] [" + owner + "]\n" +
                "Arrival:\n"+
                "Arrival Date:        " + df.format(arrivalDateStart) + " <-> " + df.format(arrivalDateEnd) + "\n" +
                "Arrival Type:        " + arrivalTransportType + "\n" +
                "Arrival Company:     " + arrivalCompany + "\n" +
                "Arrival Posistion:   " + arrivalPosition + "\n" +
                "\n" +
                "Departure:\n" +
                "Departure Date:      " + df.format(departureDateStart) + " <-> " + df.format(departureDateEnd) + "\n" +
                "Departure Type:      " + departureTransportType + "\n" +
                "Departure Company:   " + departureCompany + "\n" +
                "\n" +
                "Content:\n" +
                "Content name:        " + name + "\n" +
                "Content kind:        " + kind + "\n" +
                "Content danger:      " + danger + "\n" +
                "\n" +
                "Weight:\n" +
                "Weight empty:        " + empty + "\n" +
                "Weight filled:       " + weight;
    }
}

