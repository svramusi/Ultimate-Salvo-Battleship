package ships;

import improvedExpertAgentUtils.MetaData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleshipExceptions.InvalidShipPositionException;
import battleshipExceptions.ShipDamagedException;
import board.Board;
import display.Display;

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
    private Map<ShipType, HashMap<ShipType, Point>> observerTargets;
    private boolean iveMoved;
    private boolean recalculated;
    private Point recalculatedStart;
    private Direction recalculatedDirection;
    private Display display;
    private boolean deferMove;

    private ShipType targetedShip;
    private MetaData targetedShipMetaData;

    public Mover(Ship ship, Display display) {
        this.ship = ship;
        intersectingShipType = null;
        observerCollection = new ArrayList<Mover>();
        desiredLocation = new ArrayList<Point>();
        desiredPath = new ArrayList<Point>();

        observerDesiredLocations = new HashMap<ShipType, List<Point>>();
        observerDesiredPaths = new HashMap<ShipType, List<Point>>();
        observerTargets = new HashMap<ShipType, HashMap<ShipType, Point>>();

        iveMoved = false;

        recalculated = false;
        recalculatedStart = null;
        recalculatedDirection = null;
        this.display = display;
        deferMove = false;

        targetedShip = null;
        targetedShipMetaData = null;
    }

    public void register(Mover shipMover)
    {
        observerCollection.add(shipMover);
    }

    public void unregister(Mover shipMover)
    {
        observerCollection.remove(shipMover);
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

    private void notifyDesiredTarget(ShipType yourShipType, ShipType enemyShipType,
            Point target)
    {
        HashMap<ShipType, Point> targetMap = new HashMap<ShipType, Point>();
        targetMap.put(enemyShipType, target);
        observerTargets.put(yourShipType, targetMap);
    }

    public ShipType getShipType()
    {
        return ship.getShipType();
    }

    private boolean isWithinStrikingDistance(double targetDistance)
    {
        // This is terrible and needs to be changed...
        if (targetDistance <= (this.ship.getShootDistance() + this.ship.getMoveDistance()))
            return true;
        else
            return false;
    }

    public boolean didDeferMove()
    {
        return deferMove;
    }

    public void setAllTargets(Map<ShipType, MetaData> allTargets, Board board)
    {
        deferMove = false;

        Map<ShipType, MetaData> attackingLocations = new HashMap<ShipType, MetaData>();
        Map<ShipType, MetaData> scanningLocations = new HashMap<ShipType, MetaData>();
        Map<ShipType, MetaData> guessingLocations = new HashMap<ShipType, MetaData>();

        for (Map.Entry<ShipType, MetaData> mapEntry : allTargets.entrySet())
        {
            ShipType enemyShip = mapEntry.getKey();
            MetaData enemyShipMetaData = mapEntry.getValue();

            display.writeLine(enemyShip + " ### " + enemyShipMetaData.toString());

            if (enemyShipMetaData.isAttacking())
            {
                attackingLocations.put(enemyShip, enemyShipMetaData);
            } else if (enemyShipMetaData.isScanResult())
            {
                scanningLocations.put(enemyShip, enemyShipMetaData);
            } else if (enemyShipMetaData.isBestGuess())
            {
                guessingLocations.put(enemyShip, enemyShipMetaData);
            }
        }

        List<Point> currentLocation = ship.getShipLocation();

        double closestAttackingDistance = Double.POSITIVE_INFINITY;
        ShipType closestAttackingShipType = null;
        Point closestAttackingLocation = null;
        MetaData closestAttackingMetaData = null;

        double closestScanDistance = Double.POSITIVE_INFINITY;
        ShipType closestScanShipType = null;
        Point closestScanLocation = null;
        MetaData closestScanMetaData = null;

        double closestGuessDistance = Double.POSITIVE_INFINITY;
        ShipType closestGuessShipType = null;
        Point closestGuessLocation = null;
        MetaData closestGuessMetaData = null;

        for (Map.Entry<ShipType, MetaData> mapEntry : attackingLocations.entrySet())
        {
            MetaData placeAttackedMetaData = mapEntry.getValue();
            List<Point> placesAttacked = placeAttackedMetaData.getPoints();
            Point lastPlaceAttacked = placesAttacked.get(placesAttacked.size() - 1);

            double distance = getMinDistance(currentLocation, lastPlaceAttacked);
            if (distance < closestAttackingDistance)
            {
                closestAttackingDistance = distance;
                closestAttackingShipType = mapEntry.getKey();
                closestAttackingLocation = lastPlaceAttacked;
                closestAttackingMetaData = placeAttackedMetaData;

                // display.writeLine("closest attacking distance: "
                // + closestAttackingDistance);
                // display.writeLine("closest attacking type: " +
                // closestAttackingShipType);
                // display.writeLine("closest attacking location: "
                // + closestAttackingLocation);
            }
        }

        for (Map.Entry<ShipType, MetaData> mapEntry : scanningLocations.entrySet())
        {
            MetaData placeScannedMetaData = mapEntry.getValue();
            List<Point> placesScanned = placeScannedMetaData.getPoints();

            for (Point placeScanned : placesScanned)
            {
                double distance = getMinDistance(currentLocation, placeScanned);
                if (distance < closestScanDistance)
                {
                    closestScanDistance = distance;
                    closestScanShipType = mapEntry.getKey();
                    closestScanLocation = placeScanned;
                    closestScanMetaData = placeScannedMetaData;
                }
            }
        }

        for (Map.Entry<ShipType, MetaData> mapEntry : guessingLocations.entrySet())
        {
            MetaData placeGuessedMetaData = mapEntry.getValue();

            // There should only ever be one guessed location
            Point guessedLocation = placeGuessedMetaData.getPoints().get(0);
            double distance = getMinDistance(currentLocation, guessedLocation);
            if (distance < closestGuessDistance)
            {
                closestGuessDistance = distance;
                closestGuessShipType = mapEntry.getKey();
                closestGuessLocation = guessedLocation;
                closestGuessMetaData = placeGuessedMetaData;

                // display.writeLine("closest guess distance: " +
                // closestGuessDistance);
                // display.writeLine("closest guess type: " +
                // closestGuessShipType);
                // display.writeLine("closest guess location: " +
                // closestGuessLocation);
            }
        }

        if (isWithinStrikingDistance(closestAttackingDistance))
        {
            display.writeLine("i'm going after attacking");
            this.targetedShip = closestAttackingShipType;
            this.targetedShipMetaData = closestAttackingMetaData;
            calculateDesiredLocation(closestAttackingLocation, board);
        } else if (isWithinStrikingDistance(closestScanDistance))
        {
            display.writeLine("i'm going after scan");
            this.targetedShip = closestScanShipType;
            this.targetedShipMetaData = closestScanMetaData;
            calculateDesiredLocation(closestScanLocation, board);
        } else
        {
            if (observerTargets.containsKey(ShipType.CARRIER))
            {
                display.writeLine("i'm going after carrier current scan");
                Map<ShipType, Point> carrierTarget = observerTargets
                        .get(ShipType.CARRIER);

                // Should only contain one entry...
                for (Map.Entry<ShipType, Point> mapEntry : carrierTarget.entrySet())
                {
                    this.targetedShip = mapEntry.getKey();
                    // We're just going to help the carrier, we don't have any
                    // metadata
                    this.targetedShipMetaData = null;
                    calculateDesiredLocation(mapEntry.getValue(), board);
                }
            } else
            {
                // If we don't know of a good place to attack, then wait for the
                // carrier to tell me where he's scanning
                if (isThereStillACarrier())
                {
                    display.writeLine("i'm deferring");
                    deferMove = true;
                } else
                {
                    display.writeLine("i'm taking a best guess");
                    this.targetedShip = closestGuessShipType;
                    this.targetedShipMetaData = closestGuessMetaData;
                    calculateDesiredLocation(closestGuessLocation, board);
                }
            }
        }

        display.writeLine("I'm " + this.ship.getShipType() + " and i'm targeting "
                + this.targetedShip);

        // // Need to change this!
        // this.targetedShip = findClosestShip(allTargets);
        //
        // calculateDesiredLocation(allTargets.get(targetedShip).getPoint(),
        // board);

        for (Mover observer : observerCollection)
        {
            observer.notifyDesiredTarget(getShipType(), this.targetedShip,
                    this.destination);
        }
    }

    // This is wrong, but the lesser of two evils
    private int getDistance(Point point1, Point point2)
    {
        int xDistance = Math.abs(point1.getX() - point2.getX());
        int yDistance = Math.abs(point1.getY() - point2.getY());

        if (xDistance > yDistance)
            return xDistance;
        else
            return yDistance;
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

    // private ShipType findClosestShip(Map<ShipType, MetaData> allTargets)
    // {
    // ShipType closestShip = null;
    // double shortestDistance = Double.POSITIVE_INFINITY;
    //
    // List<Point> currentLocation = ship.getShipLocation();
    //
    // for (Map.Entry<ShipType, MetaData> mapEntry : allTargets.entrySet())
    // {
    // display.writeLine("finding distance to: " + mapEntry.getValue());
    // double distance = getMinDistance(currentLocation, mapEntry.getValue()
    // .getPoint());
    // if (distance < shortestDistance)
    // {
    // shortestDistance = distance;
    // closestShip = mapEntry.getKey();
    // }
    // }
    //
    // return closestShip;
    // }

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
            System.out.println("Caught an " + e.toString() + " when I shouldn't have!");
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
                display.writeLine("I'm " + getShipType()
                        + " and I've recalculated moved to " + this.recalculatedStart);
            } else
            {
                ShipMover.moveShip(this.ship, this.destination, board);
                display.writeLine("I'm " + getShipType() + " and I've moved to "
                        + this.destination);
            }

            iveMoved = true;

            for (Mover observer : observerCollection)
            {
                observer.notifyMove(getShipType());
            }
        }
    }

    public void sunk()
    {
        for (Mover observer : observerCollection)
        {
            observer.unregister(this);
        }
    }

    public boolean isThereStillACarrier()
    {
        for (Mover observer : observerCollection)
        {
            if (observer.getShipType().equals(ShipType.CARRIER))
                return true;
        }

        return false;
    }

    public MetaData getMetaData()
    {
        return this.targetedShipMetaData;
    }
}
