package ships;

public class Submarine extends Ship {

	public Submarine()
	{
		super("Submarine");
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
		if(damage < 1)
			return false;
		else
			return true;
	}
}
