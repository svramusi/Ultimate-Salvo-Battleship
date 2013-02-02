package board;

public class InvalidShipPositionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidShipPositionException() {}
	
	public InvalidShipPositionException(String message)
	{
		super(message);
	}
}
