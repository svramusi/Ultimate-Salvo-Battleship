package ships;

public class Submarine extends Ship {

	public Submarine()
	{
		super(ShipType.SUBMARINE);
	}
	
	@Override
	public int getMoveDistance() {
		return 5;
	}
	
	@Override
	public int getShootDistance() {
		return 1;
	}
	
	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public boolean isSunk() {
		for(int i=0; i<getSize(); i++)
		{
			if(damage[i])
				return true;
		}

		return false;
	}
}
