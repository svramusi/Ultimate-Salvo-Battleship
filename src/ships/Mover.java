package ships;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import board.Board;

import ships.Ship.ShipType;

public class Mover {
    private Point target;
    private Ship ship;
    private List<Mover> observerCollection;
    private List<Point> desiredLocation;
    private List<Point> desiredPath;
    private Map<ShipType, List<Point>> observerDesiredLocations;
    private Map<ShipType, List<Point>> observerDesiredPaths;

    public Mover(Ship ship) {
        this.ship = ship;
        observerCollection = new ArrayList<Mover>();
        desiredLocation = new ArrayList<Point>();
        desiredPath = new ArrayList<Point>();

        observerDesiredLocations = new HashMap<ShipType, List<Point>>();
        observerDesiredPaths = new HashMap<ShipType, List<Point>>();
    }

    public void setTarget(Point target) {
        this.target = target;
    }
    
    public Point getTarget() {
        return this.target;
    }

    public void register(Mover shipMover) {
        observerCollection.add(shipMover);
    }

    public ShipType getShipType() {
        return ship.getShipType();
    }

    public void calculateDesiredLocation(Point point, Board board) {
        this.desiredLocation.clear();
        this.desiredPath.clear();

        this.desiredLocation = ShipMover.moveShip(this.ship, point, board);
        this.desiredPath = calculateDesiredPath(desiredLocation);
        
        for(Mover observer : observerCollection) {
            observer.notifyDesiredLocation(getShipType(), this.desiredLocation);
            observer.notifyDesiredPath(getShipType(), this.desiredPath);
        }
    }

    private void notifyDesiredLocation(ShipType shipType, List<Point> location) {
        observerDesiredLocations.put(shipType, location);
    }

    private void notifyDesiredPath(ShipType shipType, List<Point> path) {
        observerDesiredPaths.put(shipType, path);
    }

    public List<Point> getDesiredLocation() {
        return this.desiredLocation;
    }

    private List<Point> calculateDesiredPath(List<Point> desiredLocation) {
        List<Point> locationPoints = new ArrayList<Point>();
        locationPoints.addAll(ship.getShipLocation());
        locationPoints.addAll(desiredLocation);

        Collections.sort(locationPoints);
        Point starting = locationPoints.get(0);
        Point ending = locationPoints.get(locationPoints.size() - 1);

        List<Point> desiredPath = new ArrayList<Point>();

        int difference = -1;
        if(starting.getX() == ending.getX())
        {
            difference = ending.getY() - starting.getY();
            int x = starting.getX();
            int y = starting.getY();
            int counter = 0;
            while(counter <= difference)
            {
                desiredPath.add(new Point(x, y));
                counter++;
                y++;
            }
        }
        else
        {
            difference = ending.getX() - starting.getX();
            int x = starting.getX();
            int y = starting.getY();
            int counter = 0;
            while(counter <= difference)
            {
                desiredPath.add(new Point(x, y));
                counter++;
                x++;
            }
        }

        desiredPath.removeAll(locationPoints);

        return desiredPath;
    }

    public List<Point> getDesiredPath() {
        return this.desiredPath;
    }

    private boolean isThereASimilar(List<Point> list1, List<Point> list2) {
        for(Point p1 : list1) {
            for (Point p2 : list2) {
                if(p1.equals(p2))
                    return true;
            }
        }

        return false;
    }

    public boolean checkIntersection(Map<ShipType, List<Point>> mapToCheck, List<Point> pointsToCheck) {
        for(Map.Entry<ShipType, List<Point>> mapEntry : mapToCheck.entrySet()) {
//          ShipType shipType = mapEntry.getKey();

            if(isThereASimilar(mapEntry.getValue(), pointsToCheck))
                return true;
        }
        
        return false;
    }

    public boolean isPathIntersection() {
        if(checkIntersection(observerDesiredPaths, this.desiredPath))
            return true;

        if(checkIntersection(observerDesiredLocations, this.desiredPath))
            return true;

        return false;
    }

    public boolean isDestinationIntersection() {
        return checkIntersection(observerDesiredLocations, this.desiredLocation);
    }
}