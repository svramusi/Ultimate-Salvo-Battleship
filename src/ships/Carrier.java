package ships;

import ships.Ship.ShipType;

public class Carrier extends Ship {

	public Carrier()
	{
		super(ShipType.CARRIER);
	}
	
	@Override
	public int getMoveDistance() {
		return 1;
	}
	
	@Override
	public int getShootDistance() {
		return 5;
	}
	
	@Override
	public int getSize() {
		return 5;
	}
	
	@Override
	public boolean isSunk() {
		if(damage < 5)
			return false;
		else
			return true;
	}
}
