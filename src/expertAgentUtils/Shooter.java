package expertAgentUtils;

import java.util.ArrayList;
import java.util.List;

import ships.Point;
import ships.Ship;
import ships.Ship.ShipType;
import battleship.Shot;
import board.Board;
import board.Scan;
import battleshipExceptions.ShipMovedException;
import display.Display;

public class Shooter
{
    private ShipType targetedShipType;

    private ScanResult scanResult;
    private ShipDestroyer shipDestroyer;
    private ShipPredictor predictor;
    private Ship ship;
    private Display display;

    private boolean didAScan;
    private boolean lastAttackWasFromScan;

    public Shooter(Board board, Ship ship, Display display) {
        scanResult = new ScanResult();
        targetedShipType = null;
        shipDestroyer = new ShipDestroyer(board);
        predictor = new ShipPredictor(board);
        this.ship = ship;
        this.display = display;

        didAScan = false;
        lastAttackWasFromScan = false;
    }

    public Ship getShip()
    {
        return this.ship;
    }

    public ShipType getShipType()
    {
        return this.ship.getShipType();
    }

    public void setScanResult(ScanResult scanResult)
    {
        this.scanResult = scanResult;
    }

    public void setTargetedShipMetaData(ShipType targetedShip,
            MetaData targetedShipMetaData)
    {
        this.targetedShipType = targetedShip;

        if (targetedShipMetaData != null && targetedShipMetaData.isAttacking())
        {
            // need to communicate hits with each other!!!!!

            // Throws a nasty concurrent errors if you don't clone the list
            List<Point> previousHits = new ArrayList<Point>(
                    targetedShipMetaData.getPoints());

            if (previousHits.size() > 0)
            {
                // This is correct... right?
                if (this.targetedShipType.equals(shipDestroyer.getAttackingShipType()))
                    shipDestroyer.clearHits();
                else
                    shipDestroyer.reset();
            }

            for (Point previousHit : previousHits)
            {
                display.writeLine("adding " + previousHit);
                shipDestroyer.hit(previousHit, targetedShip);
            }
        }
    }

    public ShipType getTargetedShip()
    {
        return this.targetedShipType;
    }

    private boolean amIACarrier()
    {
        return ship.getShipType().equals(ShipType.CARRIER);
    }

    private Point getOptimalShot()
    {
        return predictor.getPrediction(this.targetedShipType);
    }

    public Point getOptimalShot(ShipType shipType)
    {
        return predictor.getPrediction(shipType);
    }

    private List<Point> performCarrierScan(Board board)
    {
        this.didAScan = true;

        Point pointToScan = getOptimalShot();
        boolean needToSearchNextLine = false;

        Point startPoint = new Point(pointToScan.getX() - 1, pointToScan.getY() - 1);

        if (!board.isValidShot(startPoint))
        {
            startPoint = new Point(pointToScan.getX() - 1, pointToScan.getY());

            if (!board.isValidShot(startPoint))
            {
                needToSearchNextLine = true;
                startPoint = new Point(pointToScan.getX(), pointToScan.getY() - 1);

                if (!board.isValidShot(startPoint))
                {
                    startPoint = new Point(pointToScan.getX(), pointToScan.getY());
                }
            }
        }

        Point endPoint = null;
        int row;
        if (needToSearchNextLine)
            row = pointToScan.getX() + 1;
        else
            row = pointToScan.getX();

        endPoint = new Point(row, pointToScan.getY() + 1);

        if (!board.isValidShot(endPoint))
        {
            endPoint = new Point(row, pointToScan.getY());
        }

        return (new Scan(startPoint, endPoint, board)).getPointsToScan();
    }

    private List<Point> findPlacesToAttack(Board board, boolean gettingTarget)
    {
        lastAttackWasFromScan = false;
        List<Point> pointsToAttack = new ArrayList<Point>();

        if (scanResult.size() > 0
                && scanResult.getShipType().equals(this.targetedShipType))
        {
            display.writeLine("1");
            lastAttackWasFromScan = true;
            pointsToAttack.add(scanResult.getNextResult());
        } else
        {
            display.writeLine("2");
            boolean shipHasMoved = true;
            if (shipDestroyer.hotOnTrail())
            {
                display.writeLine("3");
                try
                {
                    pointsToAttack.add(shipDestroyer.getNextShot());
                    shipHasMoved = false;
                } catch (ShipMovedException e)
                {
                    shipDestroyer.reset();
                }
            }

            if (shipHasMoved)
            {
                display.writeLine("4");
                if (amIACarrier() && !didAScan)
                {
                    display.writeLine("5");
                    pointsToAttack.addAll(performCarrierScan(board));

                    // We're just calculating where to move next
                    if (gettingTarget)
                        didAScan = false;
                } else
                {
                    display.writeLine("6");
                    pointsToAttack.add(getOptimalShot());
                }
            }
        }

        return pointsToAttack;
    }

    public List<Point> findPlacesToAttack(Board board)
    {
        List<Point> placesToAttack = findPlacesToAttack(board, false);

        // Regardless if the carrier scanned, no more scans can be done
        // It doesn't even matter if you're not the carrier!
        didAScan = true;

        return placesToAttack;
    }

    public Point findNextTarget(Board board)
    {
        return findPlacesToAttack(board, true).get(0);
    }

    public void undoLastAttack(Point lastAttack)
    {
        didAScan = false;
        if (lastAttackWasFromScan)
            scanResult.add(0, lastAttack);
    }

    public void undoLastScan()
    {
        scanResult.clear();
    }

    public void registerHit(Point location)
    {
        shipDestroyer.hit(location, targetedShipType);
    }

    public void registerMiss(Point location)
    {
        // It doesn't need to know about every miss...
        if (shipDestroyer.hotOnTrail())
            shipDestroyer.miss(location);
    }

    public void sunk(Point location)
    {
        if (shipDestroyer.isTargeting(location))
        {
            shipDestroyer.reset();
            this.targetedShipType = null;
        }
    }

    public void reset()
    {
        didAScan = false;
    }

    public void addInfo(List<Shot> shots)
    {
        for (Shot shot : shots)
        {
            predictor.addInfo(shot);
        }
    }

    public ShipType attackingShip()
    {
        return shipDestroyer.getAttackingShipType();
    }

    public List<Point> getAllHits()
    {
        return shipDestroyer.getAllHits();
    }
}
