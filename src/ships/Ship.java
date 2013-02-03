package ships;

import java.util.*;

public abstract class Ship {
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	public enum ShipType {CARRIER, BATTLESHIP, DESTROYER, PATROLBOAT, SUBMARINE};

	private Direction direction;
	private ShipType shipType;
	
	protected int damage;
	protected Point startPoint;
	protected Point endPoint;
	
	abstract int getMoveDistance();
	abstract int getShootDistance();
	abstract int getSize();
	abstract boolean isSunk();
	
	public Ship(ShipType shipType)
	{
		damage = 0;
		setStartPoint(new Point(0,0), Direction.RIGHT);
		this.shipType = shipType;
		this.direction = Direction.RIGHT;
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
		this.direction = direction;
		
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
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public String getName()
	{
		return shipType.toString();
	}
	
	public List<Point> getShipLocation()
	{
		List<Point> location = new ArrayList<Point>();

		int startingPointX = getStartPoint().getX();
		int startingPointY = getStartPoint().getY();

		if(this.direction == Direction.UP)
		{
			for(int i=0; i<getSize(); i++)
			{
				location.add(new Point(startingPointX - i, startingPointY));
			}
		}
		else if(this.direction == Direction.DOWN)
		{
			for(int i=0; i<getSize(); i++)
			{
				location.add(new Point(startingPointX + i, startingPointY));
			}
		}
		else if(this.direction == Direction.LEFT)
		{
			for(int i=0; i<getSize(); i++)
			{
				location.add(new Point(startingPointX, startingPointY - i));
			}
		}
		else if(this.direction == Direction.RIGHT)
		{
			for(int i=0; i<getSize(); i++)
			{
				location.add(new Point(startingPointX, startingPointY + i));
			}
		}
		
		return location;
	}
	
	public ShipType getShipType()
	{
		return this.shipType;
	}
	
	public boolean isSubmerged()
	{
		if(this.shipType == ShipType.SUBMARINE)
			return true;
		else
			return false;
	}
}
