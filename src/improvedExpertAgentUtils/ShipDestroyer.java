package expertAgentUtils;

import ships.Point;
import ships.Ship.ShipType;
import board.Board;
import battleshipExceptions.ShipMovedException;

import java.util.*;

public class ShipDestroyer
{
    private boolean foundShipButDidntSink;
    private boolean lastShotWasAHit;

    // private Point origHit;
    // private Point lastHit;

    private List<Point> hits;
    private List<Point> missed;

    private Board board;

    private ShipType attackingShip;
    private int numPossibleShots;

    // Need to handle tracking 2 different ships

    public ShipDestroyer(Board board) {
        this.board = board;
        hits = new ArrayList<Point>();
        missed = new ArrayList<Point>();

        reset();
    }

    public void reset()
    {
        // origHit = null;
        // lastHit = null;

        hits.clear();
        missed.clear();

        foundShipButDidntSink = false;
        lastShotWasAHit = false;
        attackingShip = null;
        numPossibleShots = 4;
    }

    public void clearHits()
    {
        hits.clear();
    }

    private Point getFirstHit()
    {
        return hits.get(0);
    }

    private Point getLastHit()
    {
        return hits.get(hits.size() - 1);
    }

    public boolean hotOnTrail()
    {
        return foundShipButDidntSink;
    }

    public ShipType getAttackingShipType()
    {
        return this.attackingShip;
    }

    public void hit(Point point, ShipType attackingShip)
    {
        this.attackingShip = attackingShip;
        foundShipButDidntSink = true;
        lastShotWasAHit = true;

        if (!hits.contains(point))
            hits.add(point);

        // if (origHit == null)
        // {
        // origHit = point;
        // lastHit = point;
        // } else
        // {
        // lastHit = point;
        // }
    }

    public void miss(Point point)
    {
        missed.add(point);
        lastShotWasAHit = false;
    }

    private boolean alreadyTriedShot(Point shot)
    {
        for (Point previousShot : missed)
        {
            if (previousShot.equals(shot))
                return true;
        }

        return false;
    }

    private Point getRandomShot() throws ShipMovedException
    {
        Random random = new Random();

        Point nextShot = null;
        boolean validShot = false;

        int nextX;
        int nextY;

        Point origHit = hits.get(0);
        int origX = origHit.getX();
        int origY = origHit.getY();

        while (!validShot)
        {
            if (random.nextInt() % 2 == 0)
            {
                if (random.nextInt() % 2 == 0)
                    nextX = origX - 1;
                else
                    nextX = origX + 1;

                nextY = origY;
            } else
            {
                nextX = origX;

                if (random.nextInt() % 2 == 0)
                    nextY = origY - 1;
                else
                    nextY = origY + 1;
            }

            nextShot = new Point(nextX, nextY);

            if (!board.isValidShot(nextShot))
            {
                numPossibleShots--;
                validShot = false;
            } else if (alreadyTriedShot(nextShot))
            {
                validShot = false;
            } else
            {
                validShot = true;
            }

            // We've tried as many shots as there are possibilities (4 in
            // non-edge case), the ship has moved
            if (numPossibleShots == missed.size())
                throw new ShipMovedException();
        }

        return nextShot;
    }

    private boolean isYIncreasing()
    {
        if (getLastHit().getY() > getFirstHit().getY())
            return true;
        else
            return false;
    }

    private boolean isYDecreasing()
    {
        if (getLastHit().getY() < getFirstHit().getY())
            return true;
        else
            return false;
    }

    private boolean isXIncreasing()
    {
        if (getLastHit().getX() > getFirstHit().getX())
            return true;
        else
            return false;
    }

    private boolean isXDecreasing()
    {
        if (getLastHit().getX() < getFirstHit().getX())
            return true;
        else
            return false;
    }

    private Point getShotInSequence()
    {
        Point nextShot = null;

        if (isXIncreasing())
            nextShot = new Point(getLastHit().getX() + 1, getLastHit().getY());
        else if (isXDecreasing())
            nextShot = new Point(getLastHit().getX() - 1, getLastHit().getY());
        else if (isYIncreasing())
            nextShot = new Point(getLastHit().getX(), getLastHit().getY() + 1);
        else if (isYDecreasing())
            nextShot = new Point(getLastHit().getX(), getLastHit().getY() - 1);

        return nextShot;
    }

    private Point getShotReverseSequence()
    {
        Point nextShot = null;

        if (isXIncreasing())
            nextShot = new Point(getFirstHit().getX() - 1, getFirstHit().getY());
        else if (isXDecreasing())
            nextShot = new Point(getFirstHit().getX() + 1, getFirstHit().getY());
        else if (isYIncreasing())
            nextShot = new Point(getFirstHit().getX(), getFirstHit().getY() - 1);
        else if (isYDecreasing())
            nextShot = new Point(getFirstHit().getX(), getFirstHit().getY() + 1);

        return nextShot;
    }

    public Point getNextShot() throws ShipMovedException
    {
        Point nextShot = null;
        if (getLastHit().equals(getFirstHit()))
        {
            nextShot = getRandomShot();
        } else
        {
            if (lastShotWasAHit)
            {
                nextShot = getShotInSequence();
            } else
            {
                nextShot = getShotReverseSequence();
            }
        }

        return nextShot;
    }

    public List<Point> getAllHits()
    {
        return hits;
    }

    public boolean isTargeting(Point location)
    {
        for (Point p : getAllHits())
        {
            if (p.equals(location))
                return true;
        }

        return false;
    }
}
