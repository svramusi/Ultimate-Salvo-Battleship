package display;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import ships.*;

//This class is designed to be an efficient way for the display
//to query what ship is at a given location.
//Rather than have the display iterate over every single ship
//for every element that it needs to render, query this class.

//The main data structure (shipLocations) is a list of lists
//Each entry in shipLocations corresponds to a row on the board
//Each data element in shipLocations is a list of ships that occupy
//a space in that row.
//Each data element in that data structure contains the info of
//what column it is found in as well as the type of ship that it is

//Time complexity to create the data structure is O(# of ships)
//Space complexity is O(# of points for # of ships) = O(17)
//Time complexity to query is O(# of points for # of ships)

public class ShipLocations {

	private List<List<Info>> shipLocations;
	
	public ShipLocations(Board board)
	{
		shipLocations = new ArrayList<List<Info>>();

		for(int i=0;i<board.getHeight();i++)
			shipLocations.add(new ArrayList<Info>());

		List<Ship> ships = board.getShips();
		for(Ship s : ships)
		{
			for(Point p : s.getShipLocation())
			{
				shipLocations.get(p.getX()).add(convertToInfo(p, s));
			}
		}
	}
	
	public Ship.ShipType getShip(int row, int col)
	{
		List<Info> cols = shipLocations.get(row);
		for(Info i : cols)
		{
			if(i.getCol() == col)
				return i.getShipType();
		}
		
		return null;
	}
	
	private Info convertToInfo(Point p, Ship s)
	{
		return new Info(s.getShipType(), p.getY());
	}
}

class Info
{
	private Ship.ShipType shipType;
	private int col;
	
	public Info(Ship.ShipType shipType, int col)
	{
		this.shipType = shipType;
		this.col = col;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public Ship.ShipType getShipType()
	{
		return shipType;
	}
}