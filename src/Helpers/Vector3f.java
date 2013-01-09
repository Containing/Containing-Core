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
    
    public float x;
    public float y;
    public float z;
    
    @Override
    public String toString()
    {
        return "[X:" + (int)x + ",Y:" + (int)y + ",Z:" + (int)z + "]";
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
}
