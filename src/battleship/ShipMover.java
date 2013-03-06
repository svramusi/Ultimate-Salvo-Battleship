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
	
	private static Point getBestHorizontalMovement(Ship ship, Point origStartingPoint, Point target)
	{
		Direction direction = ship.getDirection();
		if(direction == Direction.LEFT || direction == Direction.RIGHT)
		{
			Point closestPoint = getClosestPoint(target, ship.getShipLocation());
			int yDistance = BattleshipUtils.getYDistance(closestPoint, target);

			//If you're closing in on the target, take into account your shooting distance
			if(target.getX() == closestPoint.getX())
				yDistance -= ship.getShootDistance();
	
			int distanceToMove = getMoveDistance(ship.getMoveDistance(), yDistance);
	
			if(origStartingPoint.getY() < target.getY())
				return new Point(origStartingPoint.getX(), origStartingPoint.getY() + distanceToMove);
			else
				return new Point(origStartingPoint.getX(), origStartingPoint.getY() - distanceToMove);
		}
		else
			return null;
	}
	
	private static Point getBestVerticalMovement(Ship ship, Point origStartingPoint, Point target)
	{
		Direction direction = ship.getDirection();
		if(direction == Direction.UP || direction == Direction.DOWN)
		{
			Point closestPoint = getClosestPoint(target, ship.getShipLocation());
			int xDistance = BattleshipUtils.getXDistance(closestPoint, target);

			//If you're closing in on the target, take into account your shooting distance
			if(target.getY() == closestPoint.getY())
				xDistance -= ship.getShootDistance();
			
			int distanceToMove = getMoveDistance(ship.getMoveDistance(), xDistance);
	
			if(origStartingPoint.getX() < target.getX())
				return new Point(origStartingPoint.getX() + distanceToMove, origStartingPoint.getY());
			else
				return new Point(origStartingPoint.getX() - distanceToMove, origStartingPoint.getY());
		}
		else
			return null;
	}
	
	/*
	private static Point getUpRotation(Board board, Ship ship, Point origStartingPoint, Point target)
	{
		return rotateShip(board, ship, origStartingPoint, target, Direction.UP);
	}
	
	private static Point getDownRotation(Board board, Ship ship, Point origStartingPoint, Point target)
	{
		return rotateShip(board, ship, origStartingPoint, target, Direction.DOWN);
	}
	
	private static Point getLeftRotation(Board board, Ship ship, Point origStartingPoint, Point target)
	{
		return rotateShip(board, ship, origStartingPoint, target, Direction.LEFT);
	}
	
	private static Point getRightRotation(Board board, Ship ship, Point origStartingPoint, Point target)
	{
		return rotateShip(board, ship, origStartingPoint, target, Direction.RIGHT);
	}
	
	private static Point rotateShip(Board board, Ship ship, Point origStartingPoint, Point target, Direction directionToRotate)
	{
		Point retVal = null;
		
		if(ship.getDirection() == directionToRotate)
			return null;

		List<Point> newShipLocation = getTempLocation(board, ship, origStartingPoint, directionToRotate);
		
		if(newShipLocation.size() > 0)
			retVal = getClosestPoint(target, newShipLocation);
		
		return retVal;
	}
	
	*/
	
	private static List<Point> getTempLocation(Board board, Ship ship, Point newStartingPoint, Direction newDirection)
	{
		Point origStartingPoint = ship.getStartPoint();
		Direction origDirection = ship.getDirection();
		
		List<Point> retVal = new ArrayList<Point>();
		
		try
		{
			board.moveShip(ship.getShipType(), newStartingPoint, newDirection);
			retVal = ship.getShipLocation();
		}
		catch(Exception e)
		{ }
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
		
		return retVal;
	}
	private static void utility(Board board, Ship ship, Point target)
	{
		//Don't move unless you have to...
		if(ship.isValidShot(target))
			return;

		Point origStartingPoint = ship.getStartPoint();
		Point origEndingPoint = ship.getEndPoint();
		Direction origDirection = ship.getDirection();

		Point bestHorizontal = getBestHorizontalMovement(ship, origStartingPoint, target);
		Point bestVertical = getBestVerticalMovement(ship, origStartingPoint, target);

		/*
		Point upRotation = getUpRotation(board, ship, origStartingPoint, target);
		Point downRotation = getDownRotation(board, ship, origStartingPoint, target);
		Point leftRotation = getLeftRotation(board, ship, origStartingPoint, target);
		Point rightRotation = getRightRotation(board, ship, origStartingPoint, target);
		*/

		/*
		System.out.println("\n\n-------------------------");
		System.out.println("best origStartingPoint: " + origStartingPoint);
		System.out.println("best horizontal: " + bestHorizontal);
		System.out.println("best bestVertical: " + bestVertical);
		System.out.println("-------------------------\n\n");
		*/

		int horizontalDistance = getBestDistance(board, ship, bestHorizontal, ship.getDirection(), target);
		int verticalDistance = getBestDistance(board, ship, bestVertical, ship.getDirection(), target);

		//ROTATE ON STARTING POINT
		int upDistanceStart = getBestDistance(board, ship, origStartingPoint, Direction.UP, target);
		int downDistanceStart = getBestDistance(board, ship, origStartingPoint, Direction.DOWN, target);
		int leftDistanceStart = getBestDistance(board, ship, origStartingPoint, Direction.LEFT, target);
		int rightDistanceStart = getBestDistance(board, ship, origStartingPoint, Direction.RIGHT, target);

		//ROTATE ON ENDING POINT
		int endRotation1;
		int endRotation2;

		Direction newDirection1;
		Direction newDirection2;
		
		if(origDirection == Direction.UP || origDirection == Direction.DOWN)
		{
			endRotation1 = getBestDistance(board, ship, origEndingPoint, Direction.LEFT, target);
			newDirection1 = Direction.LEFT;
			
			endRotation2 = getBestDistance(board, ship, origEndingPoint, Direction.RIGHT, target);
			newDirection2 = Direction.RIGHT;
		}
		else //if(origDirection == Direction.LEFT || origDirection == Direction.RIGHT)
		{
			endRotation1 = getBestDistance(board, ship, origEndingPoint, Direction.UP, target);
			newDirection1 = Direction.UP;
			
			endRotation2 = getBestDistance(board, ship, origEndingPoint, Direction.DOWN, target);
			newDirection2 = Direction.DOWN;
		}
		
		/*
		System.out.println("\n\n-------------------------");
		System.out.println("best horizontalDistance: " + horizontalDistance);
		System.out.println("best verticalDistance: " + verticalDistance);

		System.out.println("\nbest upDistanceStart: " + upDistanceStart);
		System.out.println("best downDistanceStart: " + downDistanceStart);
		System.out.println("best leftDistanceStart: " + leftDistanceStart);
		System.out.println("best rightDistanceStart: " + rightDistanceStart);

		System.out.println("\nbest endRotation1: " + endRotation1);
		System.out.println("best endRotation2: " + endRotation2);
		System.out.println("-------------------------\n\n");
		*/
		
		Point newStartingPoint = origStartingPoint;
		Direction newDirection = ship.getDirection();

		if(horizontalDistance <= verticalDistance
				&& horizontalDistance <= upDistanceStart
				&& horizontalDistance <= downDistanceStart
				&& horizontalDistance <= leftDistanceStart
				&& horizontalDistance <= rightDistanceStart
				&& horizontalDistance <= endRotation1
				&& horizontalDistance <= endRotation2)
		{
			newStartingPoint = bestHorizontal;
		}
		else if(verticalDistance <= horizontalDistance
				&& verticalDistance <= upDistanceStart
				&& verticalDistance <= downDistanceStart
				&& verticalDistance <= leftDistanceStart
				&& verticalDistance <= rightDistanceStart
				&& verticalDistance <= endRotation1
				&& verticalDistance <= endRotation2)
		{
			newStartingPoint = bestVertical;
		}
		else if(upDistanceStart <= horizontalDistance
				&& upDistanceStart <= verticalDistance
				&& upDistanceStart <= downDistanceStart
				&& upDistanceStart <= leftDistanceStart
				&& upDistanceStart <= rightDistanceStart
				&& upDistanceStart <= endRotation1
				&& upDistanceStart <= endRotation2)
		{
			newStartingPoint = origStartingPoint;
			newDirection = Direction.UP;
		}
		else if(downDistanceStart <= horizontalDistance
				&& downDistanceStart <= verticalDistance
				&& downDistanceStart <= upDistanceStart
				&& downDistanceStart <= leftDistanceStart
				&& downDistanceStart <= rightDistanceStart
				&& downDistanceStart <= endRotation1
				&& downDistanceStart <= endRotation2)
		{
			newStartingPoint = origStartingPoint;
			newDirection = Direction.DOWN;
		}
		else if(leftDistanceStart <= horizontalDistance
				&& leftDistanceStart <= verticalDistance
				&& leftDistanceStart <= upDistanceStart
				&& leftDistanceStart <= downDistanceStart
				&& leftDistanceStart <= rightDistanceStart
				&& leftDistanceStart <= endRotation1
				&& leftDistanceStart <= endRotation2)
		{
			newStartingPoint = origStartingPoint;
			newDirection = Direction.LEFT;
		}
		else if(rightDistanceStart <= horizontalDistance
				&& rightDistanceStart <= verticalDistance
				&& rightDistanceStart <= upDistanceStart
				&& rightDistanceStart <= downDistanceStart
				&& rightDistanceStart <= leftDistanceStart
				&& rightDistanceStart <= endRotation1
				&& rightDistanceStart <= endRotation2)
		{
			newStartingPoint = origStartingPoint;
			newDirection = Direction.RIGHT;
		}
		else if(endRotation1 <= horizontalDistance
				&& endRotation1 <= verticalDistance
				&& endRotation1 <= upDistanceStart
				&& endRotation1 <= downDistanceStart
				&& endRotation1 <= leftDistanceStart
				&& endRotation1 <= rightDistanceStart
				&& endRotation1 <= endRotation2)
		{
			newStartingPoint = origEndingPoint;
			newDirection = newDirection1;
		}
		else if(endRotation2 <= horizontalDistance
				&& endRotation2 <= verticalDistance
				&& endRotation2 <= upDistanceStart
				&& endRotation2 <= downDistanceStart
				&& endRotation2 <= leftDistanceStart
				&& endRotation2 <= rightDistanceStart
				&& endRotation2 <= endRotation1)
		{
			newStartingPoint = origEndingPoint;
			newDirection = newDirection2;
		}
		
		
		try
		{
			board.moveShip(ship.getShipType(), newStartingPoint, newDirection);
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
	
	private static int getBestDistance(Board board, Ship ship, Point newStartingPoint, Direction newDirection, Point target)
	{
		if(newStartingPoint == null)
			return Integer.MAX_VALUE;
		
		List<Point> tempLocation = getTempLocation(board, ship, newStartingPoint, newDirection);

		if(tempLocation.size() == 0)
			return Integer.MAX_VALUE;
		
		Point closestPoint = getClosestPoint(target, tempLocation);

		int xDistance = BattleshipUtils.getXDistance(closestPoint, target);
		int yDistance = BattleshipUtils.getYDistance(closestPoint, target);
		
		return xDistance + yDistance;
	}

	private static Point getClosestPoint(Point target, List<Point> shipLocation)
	{
		Point closestXPoint = BattleshipUtils.getClosestXPoint(shipLocation, target);
		Point closestYPoint = BattleshipUtils.getClosestYPoint(shipLocation, target);
		
		return new Point(closestXPoint.getX(), closestYPoint.getY());
	}
	
	public static void moveShip(ShipType shipType, Point target, Board board)
	{
		Ship ship = board.getShip(shipType);
		utility(board, ship, target);
		
		/*
		
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
		*/
	}

	/*
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

	private int tryDifferentDirection(Board board, Ship ship, Point origStartingPoint, Direction directoinToTry, Direction origDirection)
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
		{ /-*ignore it, try the other way *-/ }
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
	*/
}
