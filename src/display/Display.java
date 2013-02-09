package display;

import board.Board;
import ships.*;
import ships.Ship.ShipType;

import java.util.*;

public abstract class Display {
	
	protected Board board;
	private String playerName;
	
	public abstract void writeLine(String line);
	
	public Display(Board board, String playerName)
	{
		this.board = board;
		this.playerName = playerName;
	}

	public void printBoard()
	{
		StringBuilder sb = new StringBuilder();

		ShipLocations shipLocations = new ShipLocations(board);

		writeLine("\n\n" + playerName + "'s board:\n");
		for(int row=0; row<board.getHeight(); row++)
		{
			sb.append("|");
			for(int col=0; col<board.getWidth(); col++)
			{
				Ship.ShipType shipToDisplay = shipLocations.getShip(row, col);
				
				if(shipToDisplay == null)
					sb.append(".");
				else
				{
					if(shipToDisplay == ShipType.CARRIER)
						sb.append("C");
					else if(shipToDisplay == ShipType.BATTLESHIP)
						sb.append("B");
					else if(shipToDisplay == ShipType.DESTROYER)
						sb.append("D");
					else if(shipToDisplay == ShipType.PATROLBOAT)
						sb.append("P");
					else if(shipToDisplay == ShipType.SUBMARINE)
						sb.append("S");
				}
			}
			sb.append("|");
			writeLine(sb.toString());
			sb.setLength(0);
		}
		writeLine("\n\n");
	}
	
}
