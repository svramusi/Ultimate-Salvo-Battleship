package battleship;

import java.util.*;

import ships.*;
import ships.Ship.ShipType;
import board.Board;
import board.HitResponse;
import display.Display;
import display.FileDisplay;

public class ExpertComputerPlayer extends Player  {
	private ShipPredictor predictor;
	private Display display;

	private int salvoCount;
	private int shipToFireThisTurn;
	private int shipsFiredThisTurn;
	
	private Shot lastShot;
	private ShipType targetedShip;
	private List<Point> actualShipLocation;
	
	private EnemyShips enemyShips;
	private ShipDestroyer shipDestroyer;

	public ExpertComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
		
		predictor = new ShipPredictor(board);
		enemyShips = new EnemyShips();
		shipDestroyer = new ShipDestroyer(board);
	}

	@Override
	public void moveShips() 
	{
		display.writeLine("************************* START TURN *************************");
		board.nextTurn();

		List<Ship> ships = board.getActiveShips();


		salvoCount = ships.size();
		shipsFiredThisTurn = 0;
		shipToFireThisTurn = 0;

		doneWithTurn = false;
	}
	
	private Point getOptimalShot()
	{
		Point optimalShot;

		display.writeLine("finding optimal shot");
		
		if(shipDestroyer.hotOnTrail())
			optimalShot = shipDestroyer.getNextShot();
		else
		{
			if(enemyShips.isShipStillFloating(ShipType.SUBMARINE))
			{
				optimalShot = predictor.getPrediction(ShipType.SUBMARINE);
				targetedShip = ShipType.SUBMARINE;
			}
			else if(enemyShips.isShipStillFloating(ShipType.PATROLBOAT))
			{
				optimalShot = predictor.getPrediction(ShipType.PATROLBOAT);
				targetedShip = ShipType.PATROLBOAT;
			}
			else if(enemyShips.isShipStillFloating(ShipType.DESTROYER))
			{
				optimalShot = predictor.getPrediction(ShipType.DESTROYER);
				targetedShip = ShipType.DESTROYER;
			}
			else if(enemyShips.isShipStillFloating(ShipType.CARRIER))
			{
				optimalShot = predictor.getPrediction(ShipType.CARRIER);
				targetedShip = ShipType.CARRIER;
			}
			else //if(enemyShips.isShipStillFloating(ShipType.BATTLESHIP))
			{
				optimalShot = predictor.getPrediction(ShipType.BATTLESHIP);
				targetedShip = ShipType.BATTLESHIP;
			}
		}
		
		return optimalShot;
	}

	@Override
	public Shot takeAShot()
	{
		List<Ship> ships = board.getActiveShips();
		
		lastShot = new Shot(getOptimalShot(), ships.get(shipToFireThisTurn).getShipType());

		shipToFireThisTurn++;
		shipsFiredThisTurn++;

		return lastShot;
	}
	
	@Override
	public ShipType getTargedShipType()
	{
		return targetedShip;
	}
	
	@Override
	public void informActualLocation(List<Point> actualShipLocation)
	{
		this.actualShipLocation = actualShipLocation;
	}

	@Override
	public void getResponse(HitResponse hitResponse)
	{
		if(hitResponse.isAHit())
		{
			display.writeLine("I shot at: " + lastShot.getPoint().toString() + " trying to hit: " + targetedShip + " and I hit something!");
			enemyShips.registerHit(targetedShip);
			shipDestroyer.hit(lastShot.getPoint());
		}
		else
		{
			display.writeLine("I shot at: " + lastShot.getPoint().toString() + " trying to hit: " + targetedShip + " and I missed!");

			//It doesn't need to know about every miss...
			if(shipDestroyer.hotOnTrail())
				shipDestroyer.miss(lastShot.getPoint());
		}
		
		if(hitResponse.getSunkShip() != null)
		{
			ShipType sunkShip = hitResponse.getSunkShip();
			enemyShips.sunkShip(sunkShip);
			display.writeLine("You sunk their " + sunkShip + "!");
			shipDestroyer.reset();
		}
		

		display.writeLine("the ship's actual location was: ");
		for(Point p : actualShipLocation)
		{
			display.writeLine(p.toString());
		}
		
		display.writeLine("history of predictions from that ship: ");
		for(Point p : predictor.getHistoryOfGuesses(lastShot.getShipType()))
		{
			display.writeLine(p.toString());
		}
		
		
		if(shipsFiredThisTurn >= salvoCount)
		{
			display.writeLine("************************* TURN OVER *************************");
			doneWithTurn = true;
		}
	}

	@Override
	public HitResponse isHit(Shot shot)
	{
		HitResponse hitResponse;
		display.writeLine("board before:");
		display.printBoard();
		
		hitResponse = isHit(shot.getPoint());

		display.writeLine("board after:");
		display.printBoard();
		
		predictor.addInfo(shot);
		
		return hitResponse;
	}
}
