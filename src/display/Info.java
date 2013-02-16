package display;

import ships.Ship;

public class Info
{
	private Ship.ShipType shipType;
	private int col;
	private boolean isDamaged;
	
	public Info(Ship.ShipType shipType, int col, boolean isDamaged)
	{
		this.shipType = shipType;
		this.col = col;
		this.isDamaged = isDamaged;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public Ship.ShipType getShipType()
	{
		return shipType;
	}

	public boolean isDamaged()
	{
		return isDamaged;
	}
}
