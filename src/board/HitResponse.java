package board;

import ships.Ship.ShipType;
import ships.Point;

public class HitResponse {
	private Point location;
	private boolean isAHit;
	private ShipType shipSunk;
	
	public HitResponse(Point location, boolean isAHit, ShipType shipSunk)
	{
		this.location = location;
		this.isAHit = isAHit;
		this.shipSunk = shipSunk;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public boolean isAHit()
	{
		return isAHit;
	}
	
	public ShipType getSunkShip()
	{
		return shipSunk;
	}
}
