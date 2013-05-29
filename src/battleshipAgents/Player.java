package battleshipAgents;

import static org.junit.Assert.fail;
import java.util.List;

import display.Display;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;
import board.HitResponse;
import ships.*;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleship.Shot;

public abstract class Player {
	private String playerName;
	protected Board board;

	protected boolean doneWithTurn;

	public abstract void moveShips();
	public abstract List<Shot> takeAShot();
	public abstract List<HitResponse> isHit(List<Shot> shots);
	public abstract void getResponse(List<HitResponse> hitResponses);
	
	//public abstract ShipType getTargedShipType();
	//public abstract void informActualLocation(List<Point> actualShipLocation);
	
	public Player(Board board, String playerName)
	{
		this.playerName = playerName;
		this.board = board;

		board.clearBoard();

		Carrier c = new Carrier();
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		Battleship b = new Battleship();
		b.setStartPoint(new Point(0,9), Direction.DOWN);

		Destroyer d = new Destroyer();
		d.setStartPoint(new Point(9,0), Direction.UP);

		Submarine s = new Submarine();
		s.setStartPoint(new Point(9,9), Direction.UP);

		PatrolBoat pb = new PatrolBoat();
		pb.setStartPoint(new Point(5,5), Direction.LEFT);

		try {
			board.addShip(c);
			board.addShip(b);
			board.addShip(d);
			board.addShip(pb);
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
	}
	
	public String getName()
	{
		return playerName;
	}

	public HitResponse isHit(Point shot, boolean dealDamage)
	{
		return board.isHit(shot, dealDamage);
	}
	
	public List<Point> getShipLocation(ShipType shipType)
	{
		return board.getShipLocation(shipType);
	}

	public boolean isDefeated()
	{
		if(board.getActiveShips().size() > 0)
			return false;
		else
			return true;
	}

	public boolean isTurnOver()
	{
		return doneWithTurn;
	}
}
