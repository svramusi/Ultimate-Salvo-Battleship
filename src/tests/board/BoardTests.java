package tests.board;

import static org.junit.Assert.*;

import org.junit.*;
import board.*;
import ships.*;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleshipExceptions.*;
import java.util.*;

public class BoardTests {

	private Board board;
	private Carrier c;
	private Battleship b;
	private Destroyer d;
	private PatrolBoat pb;
	private Submarine s;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
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
	public void testAddShipsToBoardOntopOfEachother() {
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
		}
	}

	@Test
	public void testAddShipsToBoardOutOfBounds() {

		try {
			b.setStartPoint(new Point(-1,0), Direction.DOWN);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(0,-1), Direction.RIGHT);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}
		
		try {
			b.setStartPoint(new Point(0,0), Direction.UP);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(0,0), Direction.LEFT);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(0,7), Direction.RIGHT);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(0,10), Direction.LEFT);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(7,0), Direction.DOWN);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(10,0), Direction.UP);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(9,9), Direction.RIGHT);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(9,9), Direction.DOWN);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}

		try {
			b.setStartPoint(new Point(9,9), Direction.DOWN);
			board.addShip(b);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testMoveShipToValidLocation() {
		try {
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(0,1), Direction.DOWN);
			assertEquals(new Point(0,1), c.getStartPoint());
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidMoveException when I shouldn't have");
		}
		
	}

	@Test
	public void testMoveShipOutOfBounds() {
		try {
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		try {
			board.moveShip(ShipType.CARRIER, new Point(0,1), Direction.UP);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
			assertEquals(new Point(0,0), c.getStartPoint());
		}
	}

	@Test
	public void testMoveShipCollision() {
		try {
			board.addShip(c);
			d.setStartPoint(new Point(5,0), Direction.DOWN);
			board.addShip(d);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		try {
			board.moveShip(ShipType.CARRIER, new Point(1,0), Direction.DOWN);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
			assertEquals(new Point(0,0), c.getStartPoint());
		}
	}

	@Test
	public void testLinearMoveCarrier() {
		try {
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(2,0), Direction.DOWN);
			fail("InvalidShipPosition not thrown.");
		} catch (InvalidShipPositionException e) {
			assertNotNull(e.getMessage());
			assertEquals(new Point(0,0), c.getStartPoint());
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(1,0), Direction.DOWN);
			assertEquals(new Point(1,0), c.getStartPoint());
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
	}

	@Test
	public void testRotateCarrier() {
		try {
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(0,4), Direction.LEFT);
			assertEquals(new Point(0,4), c.getStartPoint());
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(0,4), Direction.DOWN);
			assertEquals(new Point(0,4), c.getStartPoint());
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		try {
			board.moveShip(ShipType.CARRIER, new Point(4,8), Direction.LEFT);
			assertEquals(new Point(4,8), c.getStartPoint());
		} catch (InvalidShipPositionException e) {
			System.out.println(e.getMessage());
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
	}

	@Test
	public void testGetAllShips() {
		try {
			board.addShip(c);
			board.addShip(b);
			board.addShip(d);
			board.addShip(pb);
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		List<Ship> actualShips = board.getShips();
		assertEquals(5, actualShips.size());

		assertTrue(actualShips.contains(c));
		assertTrue(actualShips.contains(b));
		assertTrue(actualShips.contains(d));
		assertTrue(actualShips.contains(pb));
		assertTrue(actualShips.contains(s));
	}

	@Test
	public void testIsAShipHit() {
		try {
			board.addShip(c);
			board.addShip(b);
			board.addShip(d);
			board.addShip(pb);
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}

		assertTrue(board.isHit(new Point(0,0), true));
		assertFalse(board.isHit(new Point(8,8), true));
	}
}
