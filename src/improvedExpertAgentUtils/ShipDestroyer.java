package improvedExpertAgentUtils;

import ships.Point;
import board.Board;

import java.util.*;

public class ShipDestroyer {
    private boolean foundShipButDidntSink;
    private boolean lastShotWasAHit;

    private Point origHit;
    private Point lastHit;

    private List<Point> missed;

    private Board board;

    //Need to handle tracking 2 different ships

    public ShipDestroyer(Board board)
    {
        this.board = board;
        missed = new ArrayList<Point>();

        reset();
    }

    public void reset()
    {
        origHit = null;
        lastHit = null;

        missed.clear();

        foundShipButDidntSink = false;
        lastShotWasAHit = false;
    }

    public boolean hotOnTrail()
    {
        return foundShipButDidntSink;
    }

    public void hit(Point point)
    {
        foundShipButDidntSink = true;
        lastShotWasAHit = true;

        if(origHit == null)
            origHit = point;
        else
            lastHit = point;
    }

    public void miss(Point point)
    {
        missed.add(point);
        lastShotWasAHit = false;
    }

    private boolean alreadyTriedShot(Point shot)
    {
        for(Point previousShot : missed)
        {
            if(previousShot.equals(shot))
                return true;
        }

        return false;
    }

    private Point getRandomShot()
    {
        Random random = new Random();

        Point nextShot = null;
        boolean validShot = false;

        int nextX;
        int nextY;

        int origX = origHit.getX();
        int origY = origHit.getY();

        while(!validShot)
        {
            if(random.nextInt() % 2 == 0)
            {
                if(random.nextInt() % 2 == 0)
                    nextX = origX - 1;
                else
                    nextX = origX + 1;

                nextY = origY;
            }
            else
            {
                nextX = origX;

                if(random.nextInt() % 2 == 0)
                    nextY = origY - 1;
                else
                    nextY = origY + 1;
            }

            nextShot = new Point(nextX, nextY);

            if(!board.isValidShot(nextShot) || alreadyTriedShot(nextShot))
                validShot = false;
            else
                validShot = true;
        }

        return nextShot;
    }

    private boolean isYIncreasing()
    {
        if(lastHit.getY() > origHit.getY())
            return true;
        else
            return false;
    }

    private boolean isYDecreasing()
    {
        if(lastHit.getY() < origHit.getY())
            return true;
        else
            return false;
    }

    private boolean isXIncreasing()
    {
        if(lastHit.getX() > origHit.getX())
            return true;
        else
            return false;
    }

    private boolean isXDecreasing()
    {
        if(lastHit.getX() < origHit.getX())
            return true;
        else
            return false;
    }

    private Point getShotInSequence()
    {
        Point nextShot = null;

        if(isXIncreasing())
            nextShot = new Point(lastHit.getX()+1, lastHit.getY());
        else if(isXDecreasing())
            nextShot = new Point(lastHit.getX()-1, lastHit.getY());
        else if(isYIncreasing())
            nextShot = new Point(lastHit.getX(), lastHit.getY()+1);
        else if(isYDecreasing())
            nextShot = new Point(lastHit.getX(), lastHit.getY()-1);

        return nextShot;
    }

    private Point getShotReverseSequence()
    {
        Point nextShot = null;

        if(isXIncreasing())
            nextShot = new Point(origHit.getX()-1, origHit.getY());
        else if(isXDecreasing())
            nextShot = new Point(origHit.getX()+1, origHit.getY());
        else if(isYIncreasing())
            nextShot = new Point(origHit.getX(), origHit.getY()-1);
        else if(isYDecreasing())
            nextShot = new Point(origHit.getX(), origHit.getY()+1);

        return nextShot;
    }

    public Point getNextShot()
    {
        Point nextShot = null;
        if(lastHit == null)
            nextShot = getRandomShot();
        else
        {
            if(lastShotWasAHit)
                nextShot = getShotInSequence();
            else
                nextShot = getShotReverseSequence();
        }

        return nextShot;
    }

}
