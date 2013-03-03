package battleship;

import java.util.List;

import board.Board;
import display.Display;
import display.ConsoleDisplay;
import ships.Point;
import ships.Ship.Direction;
import ships.Ship.ShipType;

import battleshipExceptions.InvalidShipPositionException;

public class HumanPlayer extends Player {

	private Display display;
	
	public HumanPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new ConsoleDisplay(board, playerName);
	}

	@Override
	public void moveShips() 
	{
		display.writeLine("It's time to move your ships.\nHere's your board:\n");
		display.printBoard();
	}

	@Override
	public Shot takeAShot()
	{
		return new Shot(new Point(0,0), ShipType.CARRIER);
	}

	@Override
	public void getResponse(boolean shotResult)
	{
		if(shotResult)
			display.writeLine("Your shot was a hit!");
		else
			display.writeLine("Your shot was a mist.");
	}

	@Override
	public boolean isHit(Shot shot, List<Point> actualShipLocation)
	{
		display.writeLine("shot from: " + shot.getShipType() + " at: " + shot.getPoint());

		boolean isAHit = isHit(shot.getPoint());

		if(isAHit)
		{
			display.writeLine("and it was a hit!");
			display.writeLine("board after:");
			display.printBoard();
		}
		else
			display.writeLine("and it mist!");

		return isAHit;
	}
}
