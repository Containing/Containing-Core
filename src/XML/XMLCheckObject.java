package XML;

import Main.Container;

public class XMLCheckObject {
    
    public XMLCheckObject(Container container) throws Exception{
        if (container == null){
            throw new Exception("Container can't be null");
        }
        else{
            this.container = container;
        }
        
    }
    
    public boolean approved = true;
    public String reason = "No error Found";
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
        return this.container.getId() + " : " + approved + "\n" + reason;
    }
}
