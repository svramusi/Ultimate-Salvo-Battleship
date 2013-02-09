package display;

import board.Board;
import ships.*;
import ships.Ship.ShipType;

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
				Info shipToDisplay = shipLocations.getShip(row, col);
				
				if(shipToDisplay == null)
					sb.append(".");
				else
				{
					if(shipToDisplay.isDamaged())
						sb.append("X");
					else if(shipToDisplay.getShipType() == ShipType.CARRIER)
						sb.append("C");
					else if(shipToDisplay.getShipType() == ShipType.BATTLESHIP)
						sb.append("B");
					else if(shipToDisplay.getShipType() == ShipType.DESTROYER)
						sb.append("D");
					else if(shipToDisplay.getShipType() == ShipType.PATROLBOAT)
						sb.append("P");
					else if(shipToDisplay.getShipType() == ShipType.SUBMARINE)
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
