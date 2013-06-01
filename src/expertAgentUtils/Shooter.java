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

public class Shooter
{

    private ScanResult scanResult;
    private ShipType targetedShipType;
    private ShipDestroyer shipDestroyer;
    private ShipPredictor predictor;
    private Ship ship;

    private boolean didAScan;

    public Shooter(Board board, Ship ship) {
        scanResult = new ScanResult();
        targetedShipType = null;
        shipDestroyer = new ShipDestroyer(board);
        predictor = new ShipPredictor(board);
        this.ship = ship;

        didAScan = false;
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

    public void setTargetedShip(ShipType targetedShip)
    {
        this.targetedShipType = targetedShip;
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
        List<Point> pointsToAttack = new ArrayList<Point>();

        if (scanResult.size() > 0
                && scanResult.getShipType().equals(this.targetedShipType))
        {
            pointsToAttack.add(scanResult.getNextResult());
        } else
        {
            boolean shipHasMoved = true;
            if (shipDestroyer.hotOnTrail())
            {
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
                if (amIACarrier() && !didAScan)
                {
                    pointsToAttack.addAll(performCarrierScan(board));

                    // We're just calculating where to move next
                    if (gettingTarget)
                        didAScan = false;
                } else
                {
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

    public void sunk()
    {
        shipDestroyer.reset();
        this.targetedShipType = null;
    }

    public void reset()
    {
        didAScan = false;
        scanResult.clear();
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

    public Point getLastAttack()
    {
        return shipDestroyer.getLastHit();
    }
}
