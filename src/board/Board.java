package board;

public class Board {

	private static Board theBoard = null;
	private int width;
	private int height;
	
	private Board()
	{
		width = 10;
		height = 10;
	}
	
	public static Board getBoard()
	{
		if(theBoard == null)
			theBoard = new Board();
		
		return theBoard;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
