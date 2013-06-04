package board;

import java.util.*;

import ships.*;
import ships.Ship.ShipType;
import battleship.BattleshipUtils;
import battleship.Shot;

public class Scan {
    private Point startPoint;
    private Point endPoint;
    private Board board;

    public Scan(Point startPoint, Point endPoint, Board board)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.board = board;
    }

    public List<Point> getPointsToScan() {
        List<Point> pointsToScan = new ArrayList<Point>();

        int x = getSmallestX();
        int y = getSmallestY();
        int xDistance = BattleshipUtils.getXDistance(startPoint, endPoint);
        int yDistance = BattleshipUtils.getYDistance(startPoint, endPoint);

        if(xDistance > 2 &&
                yDistance > 2 &&
                startPoint.getX() != endPoint.getX() &&
                startPoint.getY() != endPoint.getY())
        { //DIAGONAL
            Point currentPoint = startPoint;
            pointsToScan.add(currentPoint);

            while(!currentPoint.equals(endPoint))
            {
                currentPoint = calculateClosestPoint(currentPoint, endPoint);
                pointsToScan.add(currentPoint);
            }
        }
        else //ROW, COL, or BOX
        {
            for(int row=x; row<=x+xDistance; row++)
            {
                for(int col=y; col<=y+yDistance; col++)
                {
                    pointsToScan.add(new Point(row, col));
                }
            }
        }

        return pointsToScan;
    }

    public List<Point> findShips() {
        List<Point> foundShips = new ArrayList<Point>();
        List<Point> pointsToScan = getPointsToScan();

        for(Point scanPoint : pointsToScan)
        {
            if(board.isHit(new Shot(scanPoint, ShipType.CARRIER), false).isAHit())
                foundShips.add(scanPoint);
        }

        return foundShips;
    }

    private Point calculateClosestPoint(Point currentPoint, Point end)
    {
        Point topLeft = new Point(currentPoint.getX()-1, currentPoint.getY()-1);
        Point topRight = new Point(currentPoint.getX()-1, currentPoint.getY()+1);
        Point bottomRight = new Point(currentPoint.getX()+1, currentPoint.getY()-1);
        Point bottomLeft = new Point(currentPoint.getX()+1, currentPoint.getY()+1);

        int topLeftDistance = BattleshipUtils.getXDistance(topLeft, end) + BattleshipUtils.getYDistance(topLeft, end);
        int topRightDistance = BattleshipUtils.getXDistance(topRight, end) + BattleshipUtils.getYDistance(topRight, end);
        int bottomRightDistance = BattleshipUtils.getXDistance(bottomRight, end) + BattleshipUtils.getYDistance(bottomRight, end);
        int bottomLeftDistance = BattleshipUtils.getXDistance(bottomLeft, end) + BattleshipUtils.getYDistance(bottomLeft, end);

        if(topLeftDistance <= topRightDistance &&
                topLeftDistance <= bottomRightDistance &&
                topLeftDistance <= bottomLeftDistance)
            return topLeft;
        else if(topRightDistance <= topLeftDistance &&
                topRightDistance <= bottomRightDistance &&
                topRightDistance <= bottomLeftDistance)
            return topRight;
        else if(bottomRightDistance <= topLeftDistance &&
                bottomRightDistance <= topRightDistance &&
                bottomRightDistance <= bottomLeftDistance)
            return bottomRight;
        else if(bottomLeftDistance <= topLeftDistance &&
                bottomLeftDistance <= topRightDistance &&
                bottomLeftDistance <= bottomRightDistance)
            return bottomLeft;
        else
            return null;
    }

    private int getSmallestX() 
    {
        if(startPoint.getX() <= endPoint.getX())
            return startPoint.getX();
        else
            return endPoint.getX();
    }

    private int getSmallestY() 
    {
        if(startPoint.getY() <= endPoint.getY())
            return startPoint.getY();
        else
            return endPoint.getY();
    }
}
