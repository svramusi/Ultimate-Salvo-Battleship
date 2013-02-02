package ships;

public abstract class Ship {
	protected int damage;
	protected Point startPoint;
	protected Point endPoint;
	
	protected String shipName;
	
	abstract int getMoveDistance();
	abstract int getShootDistance();
	abstract int getSize();
	abstract boolean isSunk();
	
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	
	public Ship(String shipName)
	{
		damage = 0;
		setStartPoint(new Point(0,0), Direction.RIGHT);
		this.shipName = shipName;
	}
	
	public int damage()
	{
		return damage;
	}
	
	public void takeDamage()
	{
		damage++;
	}
	
	public void setStartPoint(Point startPoint, Direction direction)
	{
		this.startPoint = startPoint;
		if(direction == Direction.UP)
		{
			endPoint = new Point(startPoint.getX() - (getSize() - 1), startPoint.getY());
		}
		else if(direction == Direction.DOWN)
		{
			endPoint = new Point(startPoint.getX() + (getSize() - 1), startPoint.getY());
		}
		else if(direction == Direction.RIGHT)
		{
			endPoint = new Point(startPoint.getX(), startPoint.getY() + (getSize() - 1));
		}
		else if(direction == Direction.LEFT)
		{
			endPoint = new Point(startPoint.getX(), startPoint.getY() - (getSize() - 1));
		}
	}

	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
	
	public String getName()
	{
		return shipName;
	}
}
