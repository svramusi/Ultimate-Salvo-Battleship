package battleship;

public abstract class Ship {
	protected int damage;
	protected Point startPoint;
	protected Point endPoint;
	
	abstract int getMoveDistance();
	abstract int getShootDistance();
	abstract int getSize();
	abstract boolean isSunk();
	
	public Ship()
	{
		damage = 0;
		startPoint = new Point(0,0);
		endPoint = new Point(0,getSize());
	}
	
	public int damage()
	{
		return damage;
	}
	
	public void takeDamage()
	{
		damage++;
	}

	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
}
