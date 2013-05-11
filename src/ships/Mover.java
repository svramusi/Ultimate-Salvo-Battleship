package ships;

public class Mover {
    private Point target;
    private Ship ship;
    
    public Mover(Ship ship)
    { 
        this.ship = ship;
    }
    
    public void setTarget(Point target)
    {
        this.target = target;
    }
    
    public Point getTarget()
    {
        return this.target;
    }
}
