package ships;

public class Battleship extends Ship {

	public Battleship()
	{
		super(ShipType.BATTLESHIP);
	}
	
	@Override
	public int getMoveDistance() {
		return 2;
	}
	
	@Override
	public int getShootDistance() {
		return 4;
	}
	
	@Override
	public int getSize() {
		return 4;
	}

    @Override
    public boolean doIHaveRightOfWay(ShipType shipType) {
        if(!shipType.equals(ShipType.CARRIER))
            return false;
        else
            return true;
    }
}
