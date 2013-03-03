package battleship;

import java.util.List;

import ships.Point;
import ships.Ship.ShipType;
import board.Board;
import board.HitResponse;
import display.Display;
import display.FileDisplay;

public class DumbComputerPlayer extends ComputerPlayer {
	private Display display;
	
	private Shot lastShot;
	
	public DumbComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
	}
	
	@Override
	public void moveShips() 
	{
		//Don't move ships
		doneWithTurn = false;
	}
	
	@Override
	public Shot takeAShot()
	{
		//Always shoot at the same spot
		lastShot = new Shot(new Point(0,0), ShipType.CARRIER);
		
		return lastShot;
	}

	@Override
	public ShipType getTargedShipType()
	{
		return lastShot.getShipType();
	}
	
	@Override
	public void informActualLocation(List<Point> actualShipLocation)
	{
		//I'M NOT A CHEATER!
	}
	
	@Override
	public void getResponse(HitResponse hitResponse)
	{
		//Ignore response, it isn't going to affect anything
		doneWithTurn = true;
	}

	@Override
	public HitResponse isHit(Shot shot)
	{
		HitResponse hitResponse;
		//Must respond with result, but ignore where the shot came from
		display.writeLine("board before:");
		display.printBoard();
		
		hitResponse = isHit(shot.getPoint());

		display.writeLine("board after:");
		display.printBoard();
		
		return hitResponse;
	}
}
