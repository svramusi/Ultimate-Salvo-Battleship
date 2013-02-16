package battleship;

import static org.junit.Assert.fail;
import display.Display;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;
import ships.*;
import ships.Ship.Direction;

public abstract class Player {
	private String playerName;
	protected Board board;

	public abstract void moveShips();
	public abstract Shot takeAShot();
	public abstract boolean isHit(Shot shot);
	public abstract void getResponse(boolean shotResult);
	
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

	public boolean isHit(Point shot)
	{
		return board.isHit(shot, true);
	}

	public boolean isDefeated()
	{
		if(board.getShipCount() > 0)
			return false;
		else
			return true;
	}
}
