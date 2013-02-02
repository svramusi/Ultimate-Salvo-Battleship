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
	public boolean isSunk() {
		if(damage < 4)
			return false;
		else
			return true;
	}
}
