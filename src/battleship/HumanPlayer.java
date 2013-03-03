package battleship;

import java.util.List;

import board.Board;
import board.HitResponse;
import display.Display;
import display.ConsoleDisplay;
import ships.Point;
import ships.Ship.Direction;
import ships.Ship.ShipType;

import battleshipExceptions.InvalidShipPositionException;

public class HumanPlayer extends Player {

	private Display display;
	private Shot lastShot;
	
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
		if(hitResponse.isAHit())
			display.writeLine("Your shot was a hit!");
		else
			display.writeLine("Your shot was a mist.");
	}

	@Override
	public HitResponse isHit(Shot shot)
	{
		display.writeLine("shot from: " + shot.getShipType() + " at: " + shot.getPoint());

		HitResponse hitResponse = isHit(shot.getPoint());

		if(hitResponse.isAHit())
		{
			display.writeLine("and it was a hit!");
			display.writeLine("board after:");
			display.printBoard();
		}
		else
			display.writeLine("and it mist!");

		return hitResponse;
	}
}
