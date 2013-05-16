package ships;

import ships.Ship.ShipType;

public class ShipFactory {
    public Ship getShip(Ship.ShipType shipType)
    {
        if(shipType.equals(ShipType.CARRIER))
            return new Carrier();
        else if(shipType.equals(ShipType.BATTLESHIP))
            return new Battleship();
        else if(shipType.equals(ShipType.DESTROYER))
            return new Destroyer();
        else if(shipType.equals(ShipType.PATROLBOAT))
            return new PatrolBoat();
        else if(shipType.equals(ShipType.SUBMARINE))
            return new Submarine();
        else
            return null;
    }
}
