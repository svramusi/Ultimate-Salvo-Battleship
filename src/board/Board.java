package board;

import ships.*;

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
		for(Ship s : ships)
		{
			if(shipCollision(newShip, s))
				throw new InvalidShipPositionException("Invalid location for ship: " + newShip.getName() + " starting point: " + newShip.getStartPoint() + " end point: " + newShip.getEndPoint());
		}
		ships.add(newShip);
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
