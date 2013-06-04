package improvedExpertAgentUtils;

import java.util.ArrayList;
import java.util.List;

import ships.Point;
import ships.Ship.ShipType;

public class ScanResult
{
    private List<Point> scanResult;
    private ShipType targetedShipType;

    public ScanResult() {
        scanResult = new ArrayList<Point>();
        targetedShipType = null;
    }

    public void setTargetedShip(ShipType targetedShipType)
    {
        this.targetedShipType = targetedShipType;
    }

    public ShipType getShipType()
    {
        return targetedShipType;
    }

    public void add(int index, Point point)
    {
        scanResult.add(index, point);
    }

    public void add(Point point)
    {
        scanResult.add(point);
    }

    public Point getNextResult()
    {
        return this.scanResult.remove(0);
    }

    public List<Point> getAll()
    {
        return this.scanResult;
    }

    public void clear()
    {
        scanResult.clear();
        targetedShipType = null;
    }

    public int size()
    {
        return scanResult.size();
    }
}
