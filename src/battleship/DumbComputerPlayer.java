package battleship;

import java.util.*;

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
	public List<Shot> takeAShot()
	{
		//Always shoot at the same spot
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
	public void getResponse(List<HitResponse> hitResponse)
	{
		//Ignore response, it isn't going to affect anything
		doneWithTurn = true;
	}

	@Override
	public List<HitResponse> isHit(List<Shot> shots)
	{
		List<HitResponse> hitResponses = new ArrayList<HitResponse>();
		
		//Must respond with result, but ignore where the shot came from
		display.writeLine("board before:");
		display.printBoard();

		boolean dealDamage = true;
		if(shots.size() > 1)
			dealDamage = false; //ITS A SCAN!
		
		for(Shot shot : shots)
		{
			hitResponses.add(isHit(shot, dealDamage));
		}

		display.writeLine("board after:");
		display.printBoard();
		
		return hitResponses;
	}
}
