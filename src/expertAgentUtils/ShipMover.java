package expertAgentUtils;

import ships.Mover;
import ships.Point;
import ships.Ship.ShipType;

import java.util.List;
import java.util.ArrayList;

import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;

public class ShipMover
{
    private List<Mover> movers;

    public ShipMover() {
        movers = new ArrayList<Mover>();
    }

    public void add(Mover mover) {
        movers.add(mover);
    }

    public void setTargetDestination(ShipType shipType, Point destination, Board board) {
        for(Mover mover : movers) {
            if(mover.getShipType().equals(shipType)) {
                mover.calculateDesiredLocation(destination, board);
            }
        }
    }

    public void moveShips(Board board) {
        int movedShips = 0;
        int totalShips = movers.size();

        while(movedShips < totalShips) {
            movedShips = 0;
            for(Mover mover : movers) {
                if(mover.shouldDelayMove()) {
                    continue;
                }

                if(mover.shouldCalcNewPosition()) {
                    mover.recalculateDesiredLocation();
                }

                try {
                    mover.move(board);
                } catch(InvalidShipPositionException e) {
                    System.out.println("Caught invalid ship position exception.");
                }
                catch(ShipDamagedException e) {
                    System.out.println("Caught invalid ship damaged exception.");
                }

                movedShips++;
            }
        }
    }

    public int getDistance(Point point1, Point point2) {
        int xDistance = Math.abs(point1.getX() - point2.getX());
        int yDistance = Math.abs(point1.getY() - point2.getY());

        return xDistance + yDistance;
    }

    public int getMinDistance(List<Point> points, Point targetPoint) {
        double minDistance = Double.POSITIVE_INFINITY;

        int distance = 0;
        for (Point p : points) {
            distance = getDistance(p, targetPoint);
            if (distance < minDistance)
                minDistance = distance;
        }

        return (int)minDistance;
    }
}
