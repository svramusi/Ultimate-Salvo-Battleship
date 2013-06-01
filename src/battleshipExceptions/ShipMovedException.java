package battleshipExceptions;

public class ShipMovedException extends Exception {

    private static final long serialVersionUID = 1L;

    public ShipMovedException() {
        super();
    }

    public ShipMovedException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return "The ship you are targeting has moved.  Better luck next time";
    }
}
