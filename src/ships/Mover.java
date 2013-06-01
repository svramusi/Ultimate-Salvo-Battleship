package ships;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;

import ships.Ship.Direction;
import ships.Ship.ShipType;

public class Mover
{
    private Ship ship;
    private Point destination;
    private ShipType intersectingShipType;
    private List<Mover> observerCollection;
    private List<Point> desiredLocation;
    private List<Point> desiredPath;
    private Map<ShipType, List<Point>> observerDesiredLocations;
    private Map<ShipType, List<Point>> observerDesiredPaths;
    private boolean iveMoved;
    private boolean recalculated;
    private Point recalculatedStart;
    private Direction recalculatedDirection;
    private ShipType targetedShip;

    public Mover(Ship ship) {
        this.ship = ship;
        intersectingShipType = null;
        observerCollection = new ArrayList<Mover>();
        desiredLocation = new ArrayList<Point>();
        desiredPath = new ArrayList<Point>();

        observerDesiredLocations = new HashMap<ShipType, List<Point>>();
        observerDesiredPaths = new HashMap<ShipType, List<Point>>();

        iveMoved = false;

        recalculated = false;
        recalculatedStart = null;
        recalculatedDirection = null;
        targetedShip = null;
    }

    public void register(Mover shipMover)
    {
        observerCollection.add(shipMover);
    }

    public void unregister(Mover shipMover)
    {
        observerCollection.remove(shipMover);
    }

    public ShipType getShipType()
    {
        return ship.getShipType();
    }

    public void setAllTargets(Map<ShipType, Point> allTargets, Board board)
    {
        // Need to change this!
        this.targetedShip = findClosestShip(allTargets);
        calculateDesiredLocation(allTargets.get(targetedShip), board);
    }

    private int getDistance(Point point1, Point point2)
    {
        int xDistance = Math.abs(point1.getX() - point2.getX());
        int yDistance = Math.abs(point1.getY() - point2.getY());

        return xDistance + yDistance;
    }

    private double getMinDistance(List<Point> points, Point targetPoint)
    {
        double minDistance = Double.POSITIVE_INFINITY;

        int distance = 0;
        for (Point p : points)
        {
            distance = getDistance(p, targetPoint);
            if (distance < minDistance)
                minDistance = distance;
        }

        return minDistance;
    }

    private ShipType findClosestShip(Map<ShipType, Point> allTargets)
    {
        ShipType closestShip = null;
        double shortestDistance = Double.POSITIVE_INFINITY;

        List<Point> currentLocation = ship.getShipLocation();

        for (Map.Entry<ShipType, Point> mapEntry : allTargets.entrySet())
        {
            double distance = getMinDistance(currentLocation, mapEntry.getValue());
            if (distance < shortestDistance)
            {
                shortestDistance = distance;
                closestShip = mapEntry.getKey();
            }
        }

        return closestShip;
    }

    public ShipType getTargetedShip()
    {
        return this.targetedShip;
    }

    private void calculateDesiredLocation(Point destination, Board board)
    {
        iveMoved = false;
        recalculated = false;
        recalculatedStart = null;
        recalculatedDirection = null;

        this.destination = destination;
        this.desiredLocation.clear();
        this.desiredPath.clear();

        try
        {
            this.desiredLocation = ShipMover.testMoveShip(this.ship, destination, board);
        } catch (Exception e)
        {
            System.out.println("Caught and exception when I shouldn't have!");
        }
        this.desiredPath = calculateDesiredPath(desiredLocation);

        for (Mover observer : observerCollection)
        {
            observer.notifyDesiredLocation(getShipType(), this.desiredLocation);
            observer.notifyDesiredPath(getShipType(), this.desiredPath);
        }
    }

    private void removePreviousEntry(Map<ShipType, List<Point>> mapToClean,
            ShipType shipType)
    {
        boolean needsToBeCleaned = false;

        for (Map.Entry<ShipType, List<Point>> mapEntry : mapToClean.entrySet())
        {
            if (mapEntry.getKey().equals(shipType))
                needsToBeCleaned = true;
        }

        // Do this after the fact because we don't trust removing an entry in
        // the middle of an iterator
        if (needsToBeCleaned)
        {
            mapToClean.remove(shipType);
        }
    }

    private void notifyDesiredLocation(ShipType shipType, List<Point> location)
    {
        removePreviousEntry(observerDesiredLocations, shipType);
        observerDesiredLocations.put(shipType, location);
    }

    private void notifyDesiredPath(ShipType shipType, List<Point> path)
    {
        removePreviousEntry(observerDesiredPaths, shipType);
        observerDesiredPaths.put(shipType, path);
    }

    private void notifyMove(ShipType shipType)
    {
        observerDesiredLocations.remove(shipType);
        observerDesiredPaths.remove(shipType);
    }

    public List<Point> getDesiredLocation()
    {
        return this.desiredLocation;
    }

    private List<Point> calculateDesiredPath(List<Point> desiredLocation)
    {
        List<Point> locationPoints = new ArrayList<Point>();
        locationPoints.addAll(ship.getShipLocation());
        locationPoints.addAll(desiredLocation);

        Collections.sort(locationPoints);
        Point starting = locationPoints.get(0);
        Point ending = locationPoints.get(locationPoints.size() - 1);

        List<Point> desiredPath = new ArrayList<Point>();

        int difference = -1;
        if (starting.getX() == ending.getX())
        {
            difference = ending.getY() - starting.getY();
            int x = starting.getX();
            int y = starting.getY();
            int counter = 0;
            while (counter <= difference)
            {
                desiredPath.add(new Point(x, y));
                counter++;
                y++;
            }
        } else
        {
            difference = ending.getX() - starting.getX();
            int x = starting.getX();
            int y = starting.getY();
            int counter = 0;
            while (counter <= difference)
            {
                desiredPath.add(new Point(x, y));
                counter++;
                x++;
            }
        }

        desiredPath.removeAll(locationPoints);

        return desiredPath;
    }

    public List<Point> getDesiredPath()
    {
        return this.desiredPath;
    }

    private boolean isThereASimilar(List<Point> list1, List<Point> list2)
    {
        for (Point p1 : list1)
        {
            for (Point p2 : list2)
            {
                if (p1.equals(p2))
                    return true;
            }
        }

        return false;
    }

    public boolean checkIntersection(Map<ShipType, List<Point>> mapToCheck,
            List<Point> pointsToCheck)
    {
        for (Map.Entry<ShipType, List<Point>> mapEntry : mapToCheck.entrySet())
        {
            ShipType shipType = mapEntry.getKey();

            if (isThereASimilar(mapEntry.getValue(), pointsToCheck))
            {
                intersectingShipType = shipType;
                return true;
            }
        }

        return false;
    }

    public boolean isPathIntersection()
    {
        if (iveMoved)
            return false;

        if (checkIntersection(observerDesiredPaths, this.desiredPath))
            return true;

        if (checkIntersection(observerDesiredLocations, this.desiredPath))
            return true;

        return false;
    }

    public boolean isDestinationIntersection()
    {
        return checkIntersection(observerDesiredLocations, this.desiredLocation);
    }

    public int getNumberOfObservers()
    {
        return observerCollection.size();
    }

    public boolean shouldDelayMove()
    {
        if (iveMoved)
            return false;

        if (isPathIntersection())
        {
            if (this.ship.doIHaveRightOfWay(this.intersectingShipType))
                return false;
            else
                return true;
        } else
        {
            return false;
        }
    }

    public boolean shouldCalcNewPosition()
    {
        if (iveMoved)
            return false;

        if (isDestinationIntersection())
        {
            if (this.ship.doIHaveRightOfWay(this.intersectingShipType))
                return false;
            else
                return true;
        } else
        {
            return false;
        }
    }

    private Direction getCollisionDirection(Point collisionPoint, Point startingPoint)
    {
        int collisionX = collisionPoint.getX();
        int collisionY = collisionPoint.getY();

        int startingX = startingPoint.getX();
        int startingY = startingPoint.getY();

        if (startingX == collisionX)
        {
            if (collisionY < startingY)
                return Direction.LEFT;
            else
                return Direction.RIGHT;
        } else
        {
            if (startingX < collisionX)
                return Direction.DOWN;
            else
                return Direction.UP;
        }
    }

    private Point findCollisionPoint(List<Point> origDesiredLocation,
            List<Point> intersectingShipLocation)
    {
        for (Point intersectingShip : intersectingShipLocation)
        {
            for (Point origDesired : origDesiredLocation)
                if (intersectingShip.equals(origDesired))
                    return origDesired;
        }

        return null;
    }

    private List<Point> getNewLocation(Point collisionPoint)
    {
        List<Point> newLocation = new ArrayList<Point>();

        int collisionX = collisionPoint.getX();
        int collisionY = collisionPoint.getY();

        Direction collisionDirection = getCollisionDirection(collisionPoint,
                this.ship.getStartPoint());

        if (collisionDirection.equals(Direction.RIGHT))
        {
            for (int i = 0; i < this.ship.getSize(); i++)
            {
                newLocation.add(new Point(collisionX, collisionY - 1 - i));
            }
            recalculatedDirection = Direction.LEFT;
        } else if (collisionDirection.equals(Direction.LEFT))
        {
            for (int i = 0; i < this.ship.getSize(); i++)
            {
                newLocation.add(new Point(collisionX, collisionY + 1 + i));
            }
            recalculatedDirection = Direction.RIGHT;
        } else if (collisionDirection.equals(Direction.DOWN))
        {
            for (int i = 0; i < this.ship.getSize(); i++)
            {
                newLocation.add(new Point(collisionX - 1 - i, collisionY));
            }
            recalculatedDirection = Direction.UP;
        } else if (collisionDirection.equals(Direction.UP))
        {
            for (int i = 0; i < this.ship.getSize(); i++)
            {
                newLocation.add(new Point(collisionX + 1 + i, collisionY));
            }
            recalculatedDirection = Direction.DOWN;
        }
        recalculatedStart = newLocation.get(0);

        return newLocation;
    }

    public void recalculateDesiredLocation()
    {
        iveMoved = false;
        Point collisionPoint = findCollisionPoint(this.desiredLocation,
                this.observerDesiredLocations.get(this.intersectingShipType));
        this.desiredLocation = getNewLocation(collisionPoint);
        this.desiredPath = calculateDesiredPath(this.desiredLocation);
        this.recalculated = true;

        for (Mover observer : observerCollection)
        {
            observer.notifyDesiredLocation(getShipType(), this.desiredLocation);
            observer.notifyDesiredPath(getShipType(), this.desiredPath);
        }
    }

    public void move(Board board) throws InvalidShipPositionException,
            ShipDamagedException
    {
        if (this.destination != null)
        {
            if (this.recalculated)
            {
                ShipMover.moveShip(this.ship, this.recalculatedStart,
                        this.recalculatedDirection, board);
            } else
            {
                ShipMover.moveShip(this.ship, this.destination, board);
            }

            iveMoved = true;

            for (Mover observer : observerCollection)
            {
                observer.notifyMove(getShipType());
            }
        }
    }
}
