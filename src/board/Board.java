package board;

import ships.*;
import battleshipExceptions.*;

import java.util.*;

public class Board {

	private static Board theBoard = null;
	private int width;
	private int height;
	private List<Ship> ships;
	
	private Board()
	{
		width = 10;
		height = 10;
		ships = new ArrayList<Ship>();
	}
	
	public static Board getBoard()
	{
		if(theBoard == null)
			theBoard = new Board();
		
		return theBoard;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void addShip(Ship newShip) throws InvalidShipPositionException
	{
		validateShip(newShip); // throws InvalidShipPositionException
		ships.add(newShip);
	}
	
	public void moveShip(Ship.ShipType shipType, Point startingPoint, Ship.Direction direction) throws InvalidShipPositionException
	{
		Ship movingShip = findShip(shipType);
		Point origStartingPoint = movingShip.getStartPoint();
		Ship.Direction origDirection = movingShip.getDirection();
		
		movingShip.setStartPoint(startingPoint, direction);
		
		try
		{
			validateShip(movingShip);
		}
		catch(InvalidShipPositionException e)
		{
			movingShip.setStartPoint(origStartingPoint, origDirection);
			throw e;
		}
	}

	private Ship findShip(Ship.ShipType shipType) 
	{
		Ship movingShip = null;
		
		for(Ship s : ships)
		{
			if(s.getShipType() == shipType)
			{
				movingShip = s;
			}
		}
		return movingShip;
	}
	
	private void validateShip(Ship ship) throws InvalidShipPositionException
	{
		if(!isInBounds(ship))
			throw new InvalidShipPositionException(ship, null);

		Ship collidingShip = findCollision(ship);
		if(collidingShip != null)
			throw new InvalidShipPositionException(ship, collidingShip);
	}
	
	private boolean isInBounds(Ship s)
	{
		int startPointX = s.getStartPoint().getX();
		int startPointY = s.getStartPoint().getY();
		int endPointX = s.getEndPoint().getX();
		int endPointY = s.getEndPoint().getY();
		
		if(startPointX < 0)
			return false;
		else if(startPointY < 0)
			return false;
		else if(endPointX < 0)
			return false;
		else if(endPointY < 0)
			return false;
		else if(startPointY >= getWidth())
			return false;
		else if(startPointX >= getHeight())
			return false;
		else if(endPointY >= getWidth())
			return false;
		else if(endPointX >= getHeight())
			return false;
		else
			return true;
	}
	
	private Ship findCollision(Ship checkShip)
	{
		for(Ship s : ships)
		{
			if(s.getShipType() != checkShip.getShipType())
			{
				if(shipCollision(checkShip, s))
					return s;
			}
		}
		
		return null;
	}
	
	public void clearBoard()
	{
		ships.clear();
	}
	
	public int getShipCount()
	{
		return ships.size();
	}
	
	public boolean shipCollision(Ship ship1, Ship ship2)
	{
		if(ship1.isSubmerged() || ship2.isSubmerged())
			return false;
		
		List<Point> ship1Location = ship1.getShipLocation();
		List<Point> ship2Location = ship2.getShipLocation();
		
		for(Point ship1Point : ship1Location)
		{
			for(Point ship2Point : ship2Location)
			{
				if(ship1Point.equals(ship2Point))
					return true;
			}
		}
		
		return false;
	}
}
