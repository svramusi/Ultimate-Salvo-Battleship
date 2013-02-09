package ships;

public class Destroyer extends Ship {

	public Destroyer()
	{
		super(ShipType.DESTROYER);
	}
	
	@Override
	public int getMoveDistance() {
		return 3;
	}
	
	@Override
	public int getShootDistance() {
		return 3;
	}
	
	@Override
	public int getSize() {
		return 3;
	}
}
