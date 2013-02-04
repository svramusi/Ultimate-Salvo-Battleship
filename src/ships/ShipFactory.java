package ships;

import ships.Ship.ShipType;

public class ShipFactory {
	
	public Ship getShip(Ship.ShipType shipType)
	{
		if(shipType == ShipType.CARRIER)
			return new Carrier();
		else if(shipType == ShipType.BATTLESHIP)
			return new Carrier();
		else if(shipType == ShipType.DESTROYER)
			return new Carrier();
		else if(shipType == ShipType.PATROLBOAT)
			return new Carrier();
		else if(shipType == ShipType.SUBMARINE)
			return new Carrier();
		else
			return null;
	}
}
