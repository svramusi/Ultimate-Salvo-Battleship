package ships;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import board.Board;

import ships.Ship.ShipType;

public class Mover {
    private Point target;
    private Ship ship;
    private List<Mover> observerCollection;
    private List<Point> desiredLocation;

    public Mover(Ship ship) {
        this.ship = ship;
        observerCollection = new ArrayList<Mover>();
        desiredLocation = new ArrayList<Point>();
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
        desiredLocation.clear();
        desiredLocation = ShipMover.moveShip(this.ship, point, board);
    }

    public List<Point> getDesiredLocation() {
        return desiredLocation;
    }

    public List<Point> getSpacesCovered() {
        List<Point> locationPoints = new ArrayList<Point>();
        locationPoints.addAll(ship.getShipLocation());
        locationPoints.addAll(desiredLocation);
        
        Collections.sort(locationPoints);
        Point starting = locationPoints.get(0);
        Point ending = locationPoints.get(locationPoints.size() - 1);
        
        List<Point> spacesCovered = new ArrayList<Point>();
        int difference = -1;
        if(starting.getX() == ending.getX())
        {
            difference = ending.getY() - starting.getY();
            int x = starting.getX();
            int startingY = starting.getY();
            for(int i=startingY; i<=difference; i++)
            {
                spacesCovered.add(new Point(x, i));
            }
        }
        else
        {
            difference = ending.getX() - starting.getX();
            int y = starting.getY();
            int startingX = starting.getX();
            for(int i=startingX; i<=difference; i++)
            {
                spacesCovered.add(new Point(i, y));
            }
        }
        
        spacesCovered.removeAll(locationPoints);
        
        return spacesCovered;
    }
}