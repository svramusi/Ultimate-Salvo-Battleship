package battleship;

import java.util.List;

import ships.Point;

public class BattleshipUtils {

	public static Point getClosestXPoint(List<Point> points, Point target) {
		int targetX = target.getX();
		int minX = Integer.MAX_VALUE;
		Point cloestXPoint = null;
		
		for(Point point : points)
		{
			int diff = Math.abs(point.getX() - targetX);
			if(diff < minX)
			{
				minX = diff;
				cloestXPoint = point;
			}
		}
		
		return cloestXPoint;
	}

	public static Point getClosestYPoint(List<Point> points, Point target) {
		int targetY = target.getY();
		int minY = Integer.MAX_VALUE;
		Point cloestYPoint = null;
		
		for(Point point : points)
		{
			int diff = Math.abs(point.getY() - targetY);
			if(diff < minY)
			{
				minY = diff;
				cloestYPoint = point;
			}
		}
		
		return cloestYPoint;
	}

	public static int getXDistance(Point start, Point end) {
		return Math.abs(start.getX() - end.getX());
	}

	public static int getYDistance(Point start, Point end) {
		return Math.abs(start.getY() - end.getY());
	}
}
