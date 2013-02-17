package battleshipExceptions;

import ships.Ship;

public class ShipDamagedException extends Exception {

	private static final long serialVersionUID = 1L;
	private Ship ship;

	public ShipDamagedException(Ship ship) {
		super();
		this.ship = ship;
	}

	public ShipDamagedException(String message)
	{
		super(message);
	}
	
	@Override
	public String getMessage()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("There was an error trying to move ship: ");
		sb.append(ship.getShipType().toString());
		sb.append(". It can't be moved because it was recently damaged.");
		
		return sb.toString();
	}
}
