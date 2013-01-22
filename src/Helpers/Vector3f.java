package Helpers;

public class Vector3f {
    public Vector3f()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3f(Vector3f copy){
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    
    public float x;
    public float y;
    public float z;
    
    @Override
    public String toString()
    {
        return "[X:" + (int)x + ",Y:" + (int)y + ",Z:" + (int)z + "]";
    }
    
    public void normalize(){
        float length = length();
        x/=length;
        y/=length;
        z/=length;
    }
    
    public float length(){
        float tx = x;
        float ty = y;
        float tz = z;
        if(tx<0)tx=-tx;
        if(ty<0)ty=-ty;
        if(tz<0)tz=-tz;
        
        return (float)Math.sqrt( Math.pow(tx, 2) + Math.pow(ty, 2) + Math.pow(tz, 2) );
    }
    
    public static float distance(Vector3f v1, Vector3f v2){
        float distX = v1.x - v2.x;
        float distY = v1.y - v2.y;
        float distZ = v1.z - v2.z;
        if(distX<0)distX*=-1;
        if(distY<0)distY*=-1;
        if(distZ<0)distZ*=-1;
        
        return (float)Math.sqrt( Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2)  );  
    }
    
    public void CopyVector3f(Vector3f copy){
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }
    
    public Vector3f AddVector3f(Vector3f add){
        this.x += add.x;
        this.y += add.y;
        this.z += add.z;
        return new Vector3f(x, y, z);
    }
    
    public static Vector3f GetCenter(Vector3f pointA, Vector3f pointB){
        float x = (pointA.x - pointB.x) / 2;
        float y = (pointA.y - pointB.y) / 2;
        float z = (pointA.z - pointB.z) / 2;
        
        return new Vector3f(pointA.x-x, pointA.y-y, pointA.z-z);
    }
}
