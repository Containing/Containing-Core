package Helpers;

import Helpers.Vector3f;

/**
 *
 * @author Tonnie Boersma
 */
public class Id_Position {
    public String ID;
    public Vector3f position;
    
    public Id_Position(){
        this.ID = "dummieId";
        this.position = new Vector3f();
    }
    
    public Id_Position(String ID,Vector3f position){
        this.ID = ID;
        this.position = position;
    }
}
