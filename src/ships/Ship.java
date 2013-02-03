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
	
	public abstract int getMoveDistance();
	public abstract int getShootDistance();
	public abstract int getSize();
	public abstract boolean isSunk();
	
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
		this.endPoint = calculateEndPoint(startPoint, direction);
	}
	
	public Point calculateEndPoint(Point startPoint, Direction direction)
	{
		if(direction == Direction.UP)
		{
			return new Point(startPoint.getX() - (getSize() - 1), startPoint.getY());
		}
		else if(direction == Direction.DOWN)
		{
			return new Point(startPoint.getX() + (getSize() - 1), startPoint.getY());
		}
		else if(direction == Direction.RIGHT)
		{
			return new Point(startPoint.getX(), startPoint.getY() + (getSize() - 1));
		}
		else
		{
			return new Point(startPoint.getX(), startPoint.getY() - (getSize() - 1));
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
	
	public boolean isValidMove(Point newStartingPoint, Direction direction)
	{
		if(isValidRotation(newStartingPoint, direction))
			return true;
		
		if(getStartPoint().getDistanceFrom(newStartingPoint) > getMoveDistance())
			return false;
		else
			return true;
	}
	
	public boolean isValidRotation(Point newStartingPoint, Direction direction)
	{
		if(getStartPoint().equals(newStartingPoint) && getDirection() != direction)
			return true;
		else if(getStartPoint().equals(calculateEndPoint(newStartingPoint, direction)) && is90DegRotation(getDirection(), direction))
			return true;
		else if(getEndPoint().equals(calculateEndPoint(newStartingPoint, direction)) && is90DegRotation(getDirection(), direction))
			return true;
		else
			return false;
	}

	public boolean is90DegRotation(Direction origDirection, Direction newDirection) 
	{
		if(origDirection == Direction.UP || origDirection == Direction.DOWN)
		{
			if(newDirection == Direction.RIGHT)
				return true;
			else if(newDirection == Direction.LEFT)
				return true;
			else
				return false;
		}
		else if(origDirection == Direction.LEFT || origDirection == Direction.RIGHT)
		{
			if(newDirection == Direction.UP)
				return true;
			else if(newDirection == Direction.DOWN)
				return true;
			else
				return false;
		}
		else
			return false;
	}
}
