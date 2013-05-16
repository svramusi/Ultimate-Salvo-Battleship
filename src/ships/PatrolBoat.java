package ships;

public class PatrolBoat extends Ship {

    public PatrolBoat()
    {
        super(ShipType.PATROLBOAT);
    }

    @Override
    public int getMoveDistance() {
        return 4;
    }

    @Override
    public int getShootDistance() {
        return 2;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public boolean doIHaveRightOfWay(ShipType shipType) {
        if(shipType.equals(ShipType.SUBMARINE))
            return false;
        else
            return true;
    }
}
