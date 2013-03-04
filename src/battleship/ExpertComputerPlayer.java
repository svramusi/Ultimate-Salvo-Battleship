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
	
	//private Shot lastShot;
	//private ShipType targetedShip;
	//private List<Point> actualShipLocation;
	
	private EnemyShips enemyShips;
	private ShipDestroyer shipDestroyer;
	
	private List<Point> scanResults;
	private boolean didAScan;

	public ExpertComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
		
		predictor = new ShipPredictor(board);
		enemyShips = new EnemyShips();
		shipDestroyer = new ShipDestroyer(board);
		
		didAScan = false;
		scanResults = new ArrayList<Point>();
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
			//targetedShip = ShipType.SUBMARINE;
		}
		else if(enemyShips.isShipStillFloating(ShipType.PATROLBOAT))
		{
			optimalShot = predictor.getPrediction(ShipType.PATROLBOAT);
			//targetedShip = ShipType.PATROLBOAT;
		}
		else if(enemyShips.isShipStillFloating(ShipType.DESTROYER))
		{
			optimalShot = predictor.getPrediction(ShipType.DESTROYER);
			//targetedShip = ShipType.DESTROYER;
		}
		else if(enemyShips.isShipStillFloating(ShipType.CARRIER))
		{
			optimalShot = predictor.getPrediction(ShipType.CARRIER);
			//targetedShip = ShipType.CARRIER;
		}
		else //if(enemyShips.isShipStillFloating(ShipType.BATTLESHIP))
		{
			optimalShot = predictor.getPrediction(ShipType.BATTLESHIP);
			//targetedShip = ShipType.BATTLESHIP;
		}
		
		return optimalShot;
	}
	
	private List<Point> performCarrierScan()
	{
		didAScan = true;
		
		Point pointToScan = getOptimalShot();
		boolean needToSearchNextLine = false;
		
		Point startPoint = null;
		startPoint = new Point(pointToScan.getX()-1, pointToScan.getY()-1);
		
		if(!board.isValidShot(startPoint))
		{
			startPoint = new Point(pointToScan.getX()-1, pointToScan.getY());
			
			if(!board.isValidShot(startPoint))
			{
				needToSearchNextLine = true;
				startPoint = new Point(pointToScan.getX(), pointToScan.getY()-1);
				
				if(!board.isValidShot(startPoint))
				{
					startPoint = new Point(pointToScan.getX(), pointToScan.getY());
				}
			}
		}
		
		Point endPoint = null;
		int row;
		if(needToSearchNextLine)
			row = pointToScan.getX()+1;
		else
			row = pointToScan.getX();

		endPoint = new Point(row, pointToScan.getY()+1);
		
		if(!board.isValidShot(endPoint))
		{
			endPoint = new Point(row, pointToScan.getY());
		}

		display.writeLine("start point: " + startPoint);
		display.writeLine("end point: " + endPoint);
		
		Scan scan = new Scan(startPoint, endPoint, board);
		return scan.getPointsToScan();
		
		/*
		scanResults = scan.findShips();
		
		display.writeLine("Point to scan: " + pointToScan.toString() + " I did a scan of " + startPoint + " " + endPoint + " and I found:");
		for(Point result : scanResults)
		{
			display.writeLine(result.toString());
		}
		*/
	}

	@Override
	public List<Shot> takeAShot()
	{
		List<Shot> shots = new ArrayList<Shot>();
		List<Point> pointsToAttack = new ArrayList<Point>();
		List<Ship> ships = board.getActiveShips();

		if(scanResults.size() > 0)
		{
			pointsToAttack.add(scanResults.get(0));
			scanResults.remove(0);
		}
		else
		{
			if(shipDestroyer.hotOnTrail())
				pointsToAttack.add(shipDestroyer.getNextShot());
			else
			{
				if(!didAScan)
				{
					pointsToAttack = performCarrierScan();
				}
				else
				{
					pointsToAttack.add(getOptimalShot());
				}
			}
		}
		
		/*
		if(!shipType.equals(ShipType.CARRIER)) //carrier always scans for now
		{
		}
		*/

		ShipType shipType = ships.get(shipToFireThisTurn).getShipType();
		for(Point pointToAttack : pointsToAttack)
		{
			display.writeLine("attacking/scanning: " + pointToAttack);
			shots.add(new Shot(pointToAttack, shipType));
		}
		
		shipToFireThisTurn++;
		shipsFiredThisTurn++;

		return shots;
	}
	
	/*
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
	*/
	
	@Override
	public void getResponse(List<HitResponse> hitResponses)
	{
		if(hitResponses.size() == 1)
		{
			HitResponse response = hitResponses.get(0);
			
			if(response.isAHit())
			{
				display.writeLine("I shot at: " + response.getLocation().toString() + " and I hit something!");
				//enemyShips.registerHit(targetedShip);
				shipDestroyer.hit(response.getLocation());
			}
			else
			{
				display.writeLine("I shot at: " + response.getLocation().toString() + " and I missed!");
	
				//It doesn't need to know about every miss...
				if(shipDestroyer.hotOnTrail())
					shipDestroyer.miss(response.getLocation());
			}
			
			if(response.getSunkShip() != null)
			{
				ShipType sunkShip = response.getSunkShip();
				enemyShips.sunkShip(sunkShip);
				display.writeLine("You sunk their " + sunkShip + "!");
				shipDestroyer.reset();
			}
		}
		else //IT WAS A SCAN!
		{
			for(HitResponse response : hitResponses)
			{
				if(response.isAHit())
					scanResults.add(response.getLocation());
			}
			display.writeLine("The scan found the following: ");
			for(Point foundLocation : scanResults)
			{
				display.writeLine(foundLocation.toString());
			}
		}
		
		/*
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
		*/
		
		if(shipsFiredThisTurn >= salvoCount)
		{
			display.writeLine("************************* TURN OVER *************************");
			didAScan = false;
			doneWithTurn = true;
			scanResults.clear();
		}
	}

	@Override
	public List<HitResponse> isHit(List<Shot> shots)
	{
		List<HitResponse> hitResponses = new ArrayList<HitResponse>();
		
		display.writeLine("board before:");
		display.printBoard();
		
		boolean dealDamage = true;
		if(shots.size() > 1)
			dealDamage = false; //ITS A SCAN!
		
		for(Shot shot : shots)
		{
			hitResponses.add(isHit(shot.getPoint(), dealDamage));
			predictor.addInfo(shot);
		}

		display.writeLine("board after:");
		display.printBoard();
		
		return hitResponses;
	}
}
