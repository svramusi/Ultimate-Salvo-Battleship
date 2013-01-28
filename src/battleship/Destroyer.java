package battleship;

public class Destroyer extends Ship {

	public Destroyer()
	{
		super();
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
	
	@Override
	public boolean isSunk() {
		if(damage < 3)
			return false;
		else
			return true;
	}
}
