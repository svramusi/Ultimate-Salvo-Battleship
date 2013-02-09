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
}
