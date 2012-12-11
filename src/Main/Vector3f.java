package Main;

public class Vector3f {
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
}
