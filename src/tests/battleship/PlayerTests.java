package tests.battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ships.*;
import ships.Ship.Direction;
import battleshipAgents.HumanPlayer;
import battleshipAgents.Player;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;
import display.Display;
import display.FileDisplay;

public class PlayerTests {

	private Board board;
	private Player player;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.clearBoard();
		
		Carrier c = new Carrier();
		c.setStartPoint(new Point(0,0), Direction.DOWN);
		
		Battleship b = new Battleship();
		b.setStartPoint(new Point(0,9), Direction.DOWN);
		
		Destroyer d = new Destroyer();
		d.setStartPoint(new Point(9,0), Direction.UP);
		
		Submarine s = new Submarine();
		s.setStartPoint(new Point(9,9), Direction.UP);
		
		PatrolBoat pb = new PatrolBoat();
		pb.setStartPoint(new Point(5,5), Direction.LEFT);

		try {
			board.addShip(c);
			board.addShip(b);
			board.addShip(d);
			board.addShip(pb);
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		player = new HumanPlayer(board, "human player");
	}

	@Test
	public void testPlayerName() {
		assertEquals("human player", player.getName());
	}

	@Test
	public void testIsHit() {
		assertTrue(player.isHit(new Point(0,0), true).isAHit());
		assertFalse(player.isHit(new Point(8,8), true).isAHit());
	}

	@Test
	public void testIsDefeated() {
		assertFalse(player.isDefeated());
		board.clearBoard();
		assertTrue(player.isDefeated());
	}
}
