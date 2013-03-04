package battleship;

import java.util.*;

import ships.*;
import ships.Ship.ShipType;
import board.*;
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
	
	private boolean didAScan;
	private List<Point> scanResults;

	public ExpertComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
		
		predictor = new ShipPredictor(board);
		enemyShips = new EnemyShips();
		shipDestroyer = new ShipDestroyer(board);
		
		didAScan = false;
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
		
		return optimalShot;
	}
	
	private void performCarrierScan()
	{
		didAScan = true;
		
		Point pointToScan = getOptimalShot();
		
		Point startPoint = null;
		startPoint = new Point(pointToScan.getX()-1, pointToScan.getY()-1);
		
		if(board.isValidShot(startPoint))
		{
			startPoint = new Point(pointToScan.getX(), pointToScan.getY()-1);
			
			if(board.isValidShot(startPoint))
			{
				startPoint = new Point(pointToScan.getX()-1, pointToScan.getY());
				
				if(board.isValidShot(startPoint))
				{
					startPoint = new Point(pointToScan.getX(), pointToScan.getY());
				}
			}
		}
		
		Point endPoint = null;
		endPoint = new Point(pointToScan.getX()+1, pointToScan.getY()+1);
		
		if(board.isValidShot(endPoint))
		{
			endPoint = new Point(pointToScan.getX(), pointToScan.getY()+1);
			
			if(board.isValidShot(endPoint))
			{
				endPoint = new Point(pointToScan.getX()+1, pointToScan.getY());
				
				if(board.isValidShot(endPoint))
				{
					endPoint = new Point(pointToScan.getX(), pointToScan.getY());
				}
			}
		}
		
		Scan scan = new Scan(startPoint, endPoint, board);
		scanResults = scan.findShips();
		
		display.writeLine("I did a scan of " + startPoint + " " + endPoint + " and I found:");
		for(Point result : scanResults)
		{
			display.writeLine(result.toString());
		}
	}

	@Override
	public Shot takeAShot()
	{
		List<Ship> ships = board.getActiveShips();
		Point shotToTake;
		
		if(shipDestroyer.hotOnTrail())
			shotToTake = shipDestroyer.getNextShot();
		else
		{
			performCarrierScan();
			
			if(scanResults.size() > 0)
			{
				shotToTake = scanResults.get(0);
				scanResults.remove(0);
			}
			else
			{
				shotToTake = getOptimalShot();
			}
		}
		
		ShipType shipType = ships.get(shipToFireThisTurn).getShipType();
		/*
		if(!shipType.equals(ShipType.CARRIER)) //carrier always scans for now
		{
		}
		*/
		
		lastShot = new Shot(shotToTake, shipType);
		
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
			didAScan = false;
			doneWithTurn = true;
			scanResults.clear();
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
