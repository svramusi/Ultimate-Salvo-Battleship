package battleship;

import ships.Point;
import ships.Ship.ShipType;

public class Shot {
    private Point p;
    private ShipType shipType;

    public Shot(Point p, ShipType shipType)
    {
        this.p = p;
        this.shipType = shipType;
    }

    public Point getPoint()
    {
        return p;
    }

    public ShipType getShipType()
    {
        return shipType;
    }
}
