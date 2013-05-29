package battleshipAgents;

import java.util.*;

import board.Board;
import board.HitResponse;
import display.Display;
import display.ConsoleDisplay;
import ships.Point;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleship.Shot;

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
	public List<Shot> takeAShot()
	{
		List<Shot> shots = new ArrayList<Shot>();
		shots.add(new Shot(new Point(0,0), ShipType.CARRIER));
		return shots;
	}
	
	/*
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
	*/
	
	@Override
	public void getResponse(List<HitResponse> hitResponses)
	{
		for(HitResponse response : hitResponses)
		{
			if(response.isAHit())
				display.writeLine("Your shot was a hit!");
			else
				display.writeLine("Your shot was a mist.");
		}
	}

	@Override
	public List<HitResponse> isHit(List<Shot> shots)
	{
		List<HitResponse> hitResponses = new ArrayList<HitResponse>();
		
		for(Shot shot : shots)
		{
			display.writeLine("shot from: " + shot.getShipType() + " at: " + shot.getPoint());

			boolean dealDamage = true;
			if(shots.size() > 1)
				dealDamage = false; //ITS A SCAN!
			
			HitResponse hitResponse = isHit(shot.getPoint(), dealDamage);
	
			if(hitResponse.isAHit())
			{
				display.writeLine("and it was a hit!");
				display.writeLine("board after:");
				display.printBoard();
			}
			else
				display.writeLine("and it mist!");
			
			hitResponses.add(hitResponse);
		}

		return hitResponses;
	}
}
