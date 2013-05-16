package ships;

public class Carrier extends Ship {

    public Carrier()
    {
        super(ShipType.CARRIER);
    }

    @Override
    public int getMoveDistance() {
        return 1;
    }

    @Override
    public int getShootDistance() {
        return 5;
    }

    @Override
    public int getSize() {
        return 5;
    }

    @Override
    public boolean doIHaveRightOfWay(ShipType shipType) {
        //Carrier never has right of way
        return false;
    }
}
