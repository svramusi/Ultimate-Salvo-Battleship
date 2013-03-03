package battleship;

import java.util.*;

import ships.*;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
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

	public ExpertComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
		
		predictor = new ShipPredictor(board);
		enemyShips = new EnemyShips();
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

	/*
	//THIS NEEDS TO CHANGE!!!!!!!!!!
	private Shot getShotFromShip(Ship ship)
	{
		Random random = new Random();
		int randomFrontBack = random.nextInt();
		int randomPlusMinus = random.nextInt();
		int randomShootDistanceX = random.nextInt(ship.getShootDistance());
		int randomShootDistanceY = random.nextInt(ship.getShootDistance());

		Point pointToShootFrom;
		if(randomFrontBack % 2 == 0)
			pointToShootFrom = ship.getStartPoint();
		else
			pointToShootFrom = ship.getEndPoint();

		Point shotPoint;
		if(randomPlusMinus % 2 == 0)
			shotPoint = new Point(pointToShootFrom.getX() + randomShootDistanceX, pointToShootFrom.getY() + randomShootDistanceY);
		else
			shotPoint = new Point(pointToShootFrom.getX() - randomShootDistanceX, pointToShootFrom.getY() - randomShootDistanceY);

		display.writeLine("Shooting at: " + shotPoint.toString() + " from: " + ship.getShipType().toString());

		return new Shot(shotPoint, ship.getShipType());
	}
	*/
	
	private Point getOptimalShot()
	{
		display.writeLine("finding optimal shot");
		
		Point optimalShot;
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
		}
		else
			display.writeLine("I shot at: " + lastShot.getPoint().toString() + " trying to hit: " + targetedShip + " and I missed!");
		
		if(hitResponse.getSunkShip() != null)
		{
			ShipType sunkShip = hitResponse.getSunkShip();
			enemyShips.sunkShip(sunkShip);
			display.writeLine("You sunk their " + sunkShip + "!");
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
		
		
		//Ignore response, it isn't going to affect anything
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
		
		/*
		Point suspectedLocation = predictor.getPrediction(shot.getShipType());
		
		display.writeLine("There was a shot to point: " + shot.getPoint().toString() + " from ship: "  + shot.getShipType().toString() + " and my prediction is: " + suspectedLocation.toString());
		display.writeLine("the ship's actual location was: ");
		for(Point p : actualShipLocation)
		{
			display.writeLine(p.toString());
		}
		
		display.writeLine("history of predictions from that ship: ");
		for(Point p : predictor.getHistoryOfGuesses(shot.getShipType()))
		{
			display.writeLine(p.toString());
		}
		*/
		
		return hitResponse;
	}
}
