package display;

import board.Board;

public class ConsoleDisplay extends Display {
	
	public ConsoleDisplay(Board board)
	{
		super(board);
	}
	
	public void writeLine(String line)
	{
		System.out.println(line);
	}
}
