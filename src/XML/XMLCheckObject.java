package XML;

import Main.Container;

public class XMLCheckObject {
    
    public XMLCheckObject(Container container){
        this.approved = true;
        this.reason = "No error Found";
        this.container = container;
    }
    
    public boolean approved;
    public String reason;
    public Container container;
 
    public void foundError(String reason){
        if (this.approved){
            this.approved = false;
            this.reason = reason;
        }
        else
        {
            this.reason += "\n" + reason;
        }
    }
    
    @Override
    public String toString()
    {
        return this.container.getContainNr() + " : " + approved + "\n" + reason;
    }
}
