package ships;

public class PatrolBoat extends Ship {

	public PatrolBoat()
	{
		super("Patrol Boat");
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
	public boolean isSunk() {
		if(damage < 2)
			return false;
		else
			return true;
	}
}
