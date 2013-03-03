package battleship;

import java.util.List;
import java.util.Random;

import ships.Point;
import ships.Ship;
import ships.Ship.Direction;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;
import display.Display;
import display.FileDisplay;

public class ExpertComputerPlayer extends Player  {
	private ShipPredictor predictor;
	private Display display;

	private int salvoCount;
	private int shipToFireThisTurn;
	private int shipsFiredThisTurn;

	public ExpertComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
		display = new FileDisplay(board, playerName, "player-log-" + playerName + ".txt");
		
		predictor = new ShipPredictor(board);
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

	@Override
	public Shot takeAShot()
	{
		List<Ship> ships = board.getActiveShips();

		Shot shot = getShotFromShip(ships.get(shipToFireThisTurn));

		shipToFireThisTurn++;
		shipsFiredThisTurn++;

		return shot;
	}

	@Override
	public void getResponse(boolean shotResult)
	{
		//Ignore response, it isn't going to affect anything
		if(shipsFiredThisTurn >= salvoCount)
		{
			display.writeLine("************************* TURN OVER *************************");
			doneWithTurn = true;
		}
	}

	@Override
	public boolean isHit(Shot shot, List<Point> actualShipLocation)
	{
		boolean isAHit;
		display.writeLine("board before:");
		display.printBoard();
		
		isAHit = isHit(shot.getPoint());

		display.writeLine("board after:");
		display.printBoard();
		
		predictor.addInfo(shot);
		
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
		
		return isAHit;
	}
}
