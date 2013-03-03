package board;

import ships.Ship.ShipType;

public class HitResponse {
	private boolean isAHit;
	private ShipType shipSunk;
	
	public HitResponse(boolean isAHit, ShipType shipSunk)
	{
		this.isAHit = isAHit;
		this.shipSunk = shipSunk;
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
