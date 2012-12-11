package XML;

import Main.Container;
import Main.Vector3f;
import java.util.*;

public class XMLChecker {
    
    private XMLCheckObject[] approvedList;
    
    public XMLChecker (String xmlPath) throws Exception{
        
        List<Container> containerList;
        try{
            containerList = XMLBinder.GetContainerList(xmlPath);
        }
        catch(Exception ex){
            throw new Exception("Something went wrong while reading the XML file. \n" + ex);
        }   
        
        this.approvedList = new XMLCheckObject[containerList.size()];
        for (int i = 0; i < containerList.size(); i++) {
            this.approvedList[i] = new XMLCheckObject(containerList.get(i));
        }
        
        CheckDate();
        CheckWeight();
        CheckPosition();
    }
            
    private void CheckDate()
    {
        for (int i = 0; i < approvedList.length; i++) {
            Date ads = approvedList[i].container.getArrivalDateStart();
            Date ade = approvedList[i].container.getArrivalDateEnd();
            Date dds = approvedList[i].container.getDepartureDateStart();
            Date dde = approvedList[i].container.getDepartureDateEnd();
            
            if (!(ads.before(ade) && dds.before(dde) && ads.before(dde))){
                approvedList[i].foundError("Something is wrong with the arrival or Departure date");
            }
            
        }
    }
    private void CheckWeight()
    {
        for (int i = 0; i < approvedList.length; i++) {
            if (0 > this.approvedList[i].container.getWeight() || 0 > this.approvedList[i].container.getEmpty()){
                approvedList[i].foundError("Weight must be larger then zero");
            }
        }
    }
    private void CheckPosition()
    {
        for (int i = 0; i < approvedList.length; i++) {
            Vector3f dimension = this.approvedList[i].container.getDimension();
            if (!(dimension.x >= 0 && dimension.y >= 0 && dimension.z >= 0)){
                approvedList[i].foundError("Dimension can't be lower then 0");
            }
            
        }
    }
    
    
    public ArrayList<Container> GetApprovedContainers(){
        ArrayList<Container> returnList = new ArrayList<>();
        for (int i = 0; i < approvedList.length; i++) {
            if (approvedList[i].approved)
            {
                returnList.add(approvedList[i].container);
            }
            
        }
        return returnList;
    }

    public static void main(String[] args) {

        try {
            XMLChecker a = new XMLChecker("C:/one/xml7.xml");
            a.GetApprovedContainers();
        } 
        catch (Exception ex) {
            System.out.println("Error \n" + ex);
        }
        
    }
}
