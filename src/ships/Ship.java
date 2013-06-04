package ships;

import java.util.*;

import battleship.Shot;

public abstract class Ship {
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	public enum ShipType {CARRIER, BATTLESHIP, DESTROYER, PATROLBOAT, SUBMARINE};

	private Direction direction;
	private ShipType shipType;

	protected boolean[] damage;

	protected Point startPoint;
	protected Point endPoint;

	public abstract int getMoveDistance();
	public abstract int getShootDistance();
	public abstract int getSize();

	public Ship(ShipType shipType)
	{
		setStartPoint(new Point(0,0), Direction.RIGHT);
		damage = new boolean[getSize()];
		for(int i=0; i<getSize(); i++)
			damage[i] = false;

		this.shipType = shipType;
		this.direction = Direction.RIGHT;
	}

	public int convertToDamageIndex(Point p)
	{
		int index = 0;

		for(Point location : getShipLocation())
		{
			if(location.equals(p))
				return index;

			index++;
		}

		return -1;
	}

	public boolean isAHit(Shot shot, boolean takesDamage)
	{
		int hitIndex = convertToDamageIndex(shot.getPoint());

		if(hitIndex != -1)
		{
			if (shot.getShipType().equals(ShipType.SUBMARINE) && takesDamage) {
				for (int i=0; i<getSize(); i++)
					damage[i] = true;

				return true;
			}
			if(takesDamage)
				damage[hitIndex] = true;
			
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isSunk() {
		for(int i=0; i<getSize(); i++)
		{
			if(!damage[i])
				return false;
		}

		return true;
	}

	public int getMaxDamage()
	{
		return getSize();
	}

	public boolean isDamaged(Point point) {
		int index = convertToDamageIndex(point);

		if(index != -1)
			return damage[index];
		else
			return false;
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

	public boolean isMoveWithinRowOrCol(Point newStartingPoint, Direction newDirection)
	{
		Direction orig_direction = getDirection();
		if(orig_direction != newDirection)
			return false;

		if(orig_direction == Direction.UP || orig_direction == Direction.DOWN)
		{
			return getStartPoint().getY() == newStartingPoint.getY();
		}
		else
		{
			return getStartPoint().getX() == newStartingPoint.getX();
		}
	}
	
	public boolean isValidMove(Point newStartingPoint, Direction newDirection)
	{
		if(isValidRotation(newStartingPoint, newDirection))
			return true;

		if(!isMoveWithinRowOrCol(newStartingPoint, newDirection))
			return false;

		if(getStartPoint().getDistanceFrom(newStartingPoint) > getMoveDistance())
			return false;
		else
			return true;
	}

	public boolean isValidRotation(Point newStartingPoint, Direction direction)
	{
		if(is90DegRotation(getDirection(), direction))
		{
			if(getStartPoint().equals(newStartingPoint))
				return true;
			else if(getStartPoint().equals(calculateEndPoint(newStartingPoint, direction)))
				return true;
			else if(getEndPoint().equals(newStartingPoint))
				return true;
			else if(getEndPoint().equals(calculateEndPoint(newStartingPoint, direction)))
				return true;
			else
				return false;
		}
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

	public boolean isValidShot(Point shot) {
		if(getStartPoint().getDistanceFrom(shot) <= getShootDistance())
			return true;

		if(getEndPoint().getDistanceFrom(shot) <= getShootDistance())
			return true;

		return false;
	}
}
