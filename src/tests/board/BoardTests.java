package tests.board;

import static org.junit.Assert.*;

import org.junit.*;
import board.*;
import ships.*;
import ships.Ship.Direction;

public class BoardTests {

	private Board board;
	private Carrier c;
	private Battleship b;
	private Destroyer d;
	private PatrolBoat pb;
	private Submarine s;
	
	@Before
	public void setUp() throws Exception {
		board = Board.getBoard();
		board.clearBoard();
		
		c = new Carrier();
		c.setStartPoint(new Point(0,0), Direction.DOWN);
		
		b = new Battleship();
		b.setStartPoint(new Point(0,9), Direction.DOWN);
		
		d = new Destroyer();
		d.setStartPoint(new Point(9,0), Direction.UP);
		
		s = new Submarine();
		s.setStartPoint(new Point(9,9), Direction.UP);
		
		pb = new PatrolBoat();
		pb.setStartPoint(new Point(5,5), Direction.LEFT);
	}

	@Test
	public void testBoardSize() {
		assertEquals(10, board.getWidth());
		assertEquals(10, board.getHeight());
	}

	@Test
	public void testAddShipsToBoardValidLocations() {
		try {
			board.addShip(c);
			board.addShip(b);
			board.addShip(d);
			board.addShip(pb);
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		assertEquals(5, board.getShipCount());
	}

	@Test
	public void testShipCollision() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);
		b.setStartPoint(new Point(0,0), Direction.DOWN);
		assertTrue(board.shipCollision(b,c));

		c.setStartPoint(new Point(0,0), Direction.DOWN);
		b.setStartPoint(new Point(5,0), Direction.DOWN);
		assertFalse(board.shipCollision(b,c));

		c.setStartPoint(new Point(5,3), Direction.RIGHT);
		b.setStartPoint(new Point(3,5), Direction.DOWN);
		assertTrue(board.shipCollision(b,c));

		c.setStartPoint(new Point(0,0), Direction.DOWN);
		s.setStartPoint(new Point(0,0), Direction.DOWN);
		assertFalse(board.shipCollision(c,s));
	}

	@Test
	public void testAddShipsToBoardInvalidLocations() {
		try {
			c.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		try {
			b.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
}
