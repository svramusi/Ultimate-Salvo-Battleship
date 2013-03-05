package battleship;

import ships.Ship.*;
import ships.*;
import ships.Ship.Direction;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;

import java.util.*;

public class ShipMover {
	
	ShipType shipType;
	Point target;
	Board board;
	
	public ShipMover(ShipType shipType, Point target, Board board)
	{
		this.shipType = shipType;
		this.target = target;
		this.board = board;
	}

	private int getMoveDistance(int maxMoveDistance, int distanceNeeded)
	{
		if(distanceNeeded > maxMoveDistance)
			return maxMoveDistance;
		else
			return distanceNeeded;
	}
	
	public void moveShip()
	{
		Ship ship = board.getShip(shipType);
		
		if(!ship.isValidShot(target))
		{
			Point origStartingPoint = ship.getStartPoint();
			Point newStartingPoint = origStartingPoint;
			
			Point closestXPoint = BattleshipUtils.getClosestXPoint(ship.getShipLocation(), target);
			Point closestYPoint = BattleshipUtils.getClosestYPoint(ship.getShipLocation(), target);
			
			int xDistance = BattleshipUtils.getXDistance(closestXPoint, target);
			int yDistance = BattleshipUtils.getYDistance(closestYPoint, target);
			
			Direction direction = ship.getDirection();

			int distanceToMove;
			if((direction == Direction.UP || direction == Direction.DOWN) && xDistance > ship.getShootDistance())
			{
				distanceToMove = getMoveDistance(ship.getMoveDistance(), xDistance);

				if(origStartingPoint.getX() < target.getX())
					newStartingPoint = new Point(origStartingPoint.getX() + distanceToMove, origStartingPoint.getY());
				else
					newStartingPoint = new Point(origStartingPoint.getX() - distanceToMove, origStartingPoint.getY());
			}
			else if((direction == Direction.RIGHT || direction == Direction.LEFT) && yDistance > ship.getShootDistance())
			{
				distanceToMove = getMoveDistance(ship.getMoveDistance(), yDistance);

				if(origStartingPoint.getY() < target.getY())
					newStartingPoint = new Point(origStartingPoint.getX(), origStartingPoint.getY() + distanceToMove);
				else
					newStartingPoint = new Point(origStartingPoint.getX(), origStartingPoint.getY() - distanceToMove);
			}
			
			try
			{
				board.moveShip(ship.getShipType(), newStartingPoint, direction);
			}
			catch(InvalidShipPositionException e)
			{
				//NEED TO HANDLE THIS!!!
				System.out.println("InvalidShipPositionException!!! " + e.getMessage());
			}
			catch(ShipDamagedException e)
			{
				//NEED TO HANDLE THIS!!!
				System.out.println("ShipDamagedException!!! ");
			}
		}
	}
}
