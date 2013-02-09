package display;

import board.Board;

public class ConsoleDisplay extends Display {
	
	public ConsoleDisplay(Board board, String playerName)
	{
		super(board, playerName);
	}
	
	public void writeLine(String line)
	{
		System.out.println(line);
	}
}
