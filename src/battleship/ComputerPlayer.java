package battleship;

import board.Board;

public abstract class ComputerPlayer extends Player {
	public ComputerPlayer(Board board, String playerName)
	{
		super(board, playerName);
	}
}
