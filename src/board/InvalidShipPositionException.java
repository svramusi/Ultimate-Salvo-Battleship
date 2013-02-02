package board;

import ships.Ship;
import java.lang.StringBuilder;

public class InvalidShipPositionException extends Exception {

	private static final long serialVersionUID = 1L;
	private Ship movingShip;
	private Ship stationaryShip;

	public InvalidShipPositionException(Ship movingShip, Ship stationaryShip) {
		super();
		this.movingShip = movingShip;
		this.stationaryShip = stationaryShip;
	}
	
	public InvalidShipPositionException(String message)
	{
		super(message);
	}
	
	@Override
	public String getMessage()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("There was an error trying to place ship: ");
		sb.append(movingShip.getShipType().toString());
		sb.append(" starting at location: (");
		sb.append(movingShip.getStartPoint().getX());
		sb.append(",");
		sb.append(movingShip.getStartPoint().getY());
		sb.append(") to location: (");
		sb.append(movingShip.getEndPoint().getX());
		sb.append(",");
		sb.append(movingShip.getEndPoint().getY());
		sb.append(")");
		
		if(stationaryShip != null)
		{
			sb.append("\nThere is already ship: ");
			sb.append(stationaryShip.getShipType().toString());
			sb.append(" starting at location: (");
			sb.append(stationaryShip.getStartPoint().getX());
			sb.append(",");
			sb.append(stationaryShip.getStartPoint().getY());
			sb.append(") to location: (");
			sb.append(stationaryShip.getEndPoint().getX());
			sb.append(",");
			sb.append(stationaryShip.getEndPoint().getY());
			sb.append(")");
		}
		
		return sb.toString();
	}
}
