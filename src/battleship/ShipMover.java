package battleship;

import ships.Ship.*;
import ships.*;
import ships.Ship.Direction;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;

import java.util.*;

public class ShipMover 
{

	private static int getMoveDistance(int maxMoveDistance, int distanceNeeded)
	{
		if(distanceNeeded > maxMoveDistance)
			return maxMoveDistance;
		else
			return distanceNeeded;
	}
	
	//NEED TO MAKE THIS A UTILITY FUNCION
	//NEED TO EVALUATE ALL OPTIONS AND GO FROM THERE
	
	private static Point getBestHorizontalMovement(Ship ship, Point origStartingPoint, Point target)
	{
		Point closestPoint = GetClosestPoint(target, ship.getShipLocation());
		int yDistance = BattleshipUtils.getYDistance(closestPoint, target);

		int distanceToMove = getMoveDistance(ship.getMoveDistance(), yDistance);

		if(origStartingPoint.getY() < target.getY())
			return new Point(origStartingPoint.getX(), origStartingPoint.getY() + distanceToMove);
		else
			return new Point(origStartingPoint.getX(), origStartingPoint.getY() - distanceToMove);
	}
	
	private static Point getBestVerticalMovement(Ship ship, Point origStartingPoint, Point target)
	{
		Point closestPoint = GetClosestPoint(target, ship.getShipLocation());
		int xDistance = BattleshipUtils.getXDistance(closestPoint, target);
		
		int distanceToMove = getMoveDistance(ship.getMoveDistance(), xDistance);

		if(origStartingPoint.getX() < target.getX())
			return new Point(origStartingPoint.getX() + distanceToMove, origStartingPoint.getY());
		else
			return new Point(origStartingPoint.getX() - distanceToMove, origStartingPoint.getY());
	}
	
	private static void utility(ShipType shipType, Point target, Board board)
	{
		Ship ship = board.getShip(shipType);

		if(ship.isValidShot(target))
			return;
	
	
		Point origStartingPoint = ship.getStartPoint();
		Point newStartingPoint = origStartingPoint;
		
		Point closestPoint = GetClosestPoint(target, ship.getShipLocation());
		
		int xDistance = BattleshipUtils.getXDistance(closestPoint, target);
		int yDistance = BattleshipUtils.getYDistance(closestPoint, target);
		
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
		else
		{
			if(xDistance > ship.getShootDistance() || yDistance > ship.getShootDistance())
			{
				if(direction == Direction.UP || direction == Direction.DOWN)
					direction = getNewDirection(ship, newStartingPoint, direction, Direction.RIGHT, Direction.LEFT);
				else //(direction == Direction.RIGHT || direction == Direction.LEFT)
					direction = getNewDirection(ship, newStartingPoint, direction, Direction.UP, Direction.DOWN);
			}
		}
		
		
	}

	private static Point GetClosestPoint(Point target, List<Point> shipLocation)
	{
		Point closestXPoint = BattleshipUtils.getClosestXPoint(shipLocation, target);
		Point closestYPoint = BattleshipUtils.getClosestYPoint(shipLocation, target);
		
		return new Point(closestXPoint.getX(), closestYPoint.getY());
	}
	
	public static void moveShip(ShipType shipType, Point target, Board board)
	{
		Ship ship = board.getShip(shipType);
		
		if(!ship.isValidShot(target))
		{
			Point origStartingPoint = ship.getStartPoint();
			Point newStartingPoint = origStartingPoint;
			
			Point closestPoint = GetClosestPoint(target, ship);
			
			int xDistance = BattleshipUtils.getXDistance(closestPoint, target);
			int yDistance = BattleshipUtils.getYDistance(closestPoint, target);
			
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
			else
			{
				if(xDistance > ship.getShootDistance() || yDistance > ship.getShootDistance())
				{
					if(direction == Direction.UP || direction == Direction.DOWN)
						direction = getNewDirection(ship, newStartingPoint, direction, Direction.RIGHT, Direction.LEFT);
					else //(direction == Direction.RIGHT || direction == Direction.LEFT)
						direction = getNewDirection(ship, newStartingPoint, direction, Direction.UP, Direction.DOWN);
				}
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

	private static Direction getNewDirection(Ship ship, Point newStartingPoint, 
			Direction origDirection, Direction option1, Direction option2) 
	{
		int option1Dist = tryDifferentDirection(ship, newStartingPoint, option1, origDirection);
		int option2Dist = tryDifferentDirection(ship, newStartingPoint, option2, origDirection);
		
		System.out.println("option1Dist : " + option1Dist + " option2Dist : " + option2Dist);
		
		if(option1Dist < option2Dist)
			return option1;
		else
			return option2;
	}

	private int tryDifferentDirection(Ship ship, Point origStartingPoint, Direction directoinToTry, Direction origDirection)
	{
		int distance = Integer.MAX_VALUE;
		
		try
		{
			board.moveShip(ship.getShipType(), origStartingPoint, directoinToTry);

			int xDist = BattleshipUtils.getXDistance(ship.getEndPoint(), target);
			int yDist = BattleshipUtils.getYDistance(ship.getEndPoint(), target);
			
			distance = xDist + yDist;
		}
		catch(Exception e)
		{ /*ignore it, try the other way */ }
		finally
		{
			//go back to the way it was...
			try
			{
				board.moveShip(ship.getShipType(), origStartingPoint, origDirection);
			}
			catch(Exception e)
			{ }
		}
		
		return distance;
	}
}
