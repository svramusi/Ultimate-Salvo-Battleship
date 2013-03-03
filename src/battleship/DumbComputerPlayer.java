package battleship;

import java.util.List;

import ships.Point;
import ships.Ship.ShipType;
import board.Board;
import display.Display;
import display.FileDisplay;

public class DumbComputerPlayer extends ComputerPlayer {
	private Display display;
	
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
		return new Shot(new Point(0,0), ShipType.CARRIER);
	}
	
	@Override
	public void getResponse(boolean shotResult)
	{
		//Ignore response, it isn't going to affect anything
		doneWithTurn = true;
	}

	@Override
	public boolean isHit(Shot shot, List<Point> actualShipLocation)
	{
		boolean isAHit;
		//Must respond with result, but ignore where the shot came from
		display.writeLine("board before:");
		display.printBoard();
		
		isAHit = isHit(shot.getPoint());

		display.writeLine("board after:");
		display.printBoard();
		
		return isAHit;
	}
}
