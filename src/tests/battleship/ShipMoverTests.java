package tests.battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import ships.Battleship;
import ships.Carrier;
import ships.Destroyer;
import ships.PatrolBoat;
import ships.Point;
import ships.Submarine;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import battleship.ShipDestroyer;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;

import battleship.ShipMover;

public class ShipMoverTests {
	
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.clearBoard();
	}

	@Test
	public void testNoMove() {
		Carrier c = new Carrier();
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		try {
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.CARRIER);
		assertTrue(shipLocation.contains(new Point(0,0)));
		
		
		ShipMover.moveShip(ShipType.CARRIER, new Point(1,1), board);

		shipLocation = board.getShipLocation(ShipType.CARRIER);
		assertTrue(shipLocation.contains(new Point(0,0)));


		ShipMover.moveShip(ShipType.CARRIER, new Point(9,5), board);

		shipLocation = board.getShipLocation(ShipType.CARRIER);
		assertTrue(shipLocation.contains(new Point(0,0)));
		

		
		Submarine s = new Submarine();
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(0,0)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(3,1), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertTrue(shipLocation.contains(new Point(0,0)));
		assertTrue(shipLocation.contains(new Point(1,0)));
		assertTrue(shipLocation.contains(new Point(2,0)));
	}

	@Test
	public void testDoubleDownMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(0,0)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(4,1), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,0)));
		
		assertTrue(shipLocation.contains(new Point(2,0)));
		assertTrue(shipLocation.contains(new Point(3,0)));
		assertTrue(shipLocation.contains(new Point(4,0)));
	}

	@Test
	public void testDoubleUpMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(3,0), Direction.DOWN);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(3,0)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(0,1), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(3,0)));
		
		assertTrue(shipLocation.contains(new Point(0,0)));
		assertTrue(shipLocation.contains(new Point(1,0)));
		assertTrue(shipLocation.contains(new Point(2,0)));
	}

	@Test
	public void testTwoVerticalMoves() {
		PatrolBoat pb = new PatrolBoat();
		pb.setStartPoint(new Point(9,0), Direction.UP);

		try {
			board.addShip(pb);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
		assertTrue(shipLocation.contains(new Point(8,0)));

		ShipMover.moveShip(ShipType.PATROLBOAT, new Point(0,1), board);

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(8,0)));
		
		assertTrue(shipLocation.contains(new Point(4,0)));
		assertTrue(shipLocation.contains(new Point(5,0)));
		

		ShipMover.moveShip(ShipType.PATROLBOAT, new Point(0,1), board);

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(4,0)));
		
		assertTrue(shipLocation.contains(new Point(0,0)));
		assertTrue(shipLocation.contains(new Point(1,0)));
	}

	@Test
	public void testDoubleRightMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(0,0), Direction.RIGHT);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(0,2)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(1,4), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,0)));

		assertTrue(shipLocation.contains(new Point(0,4)));
		assertTrue(shipLocation.contains(new Point(0,3)));
		assertTrue(shipLocation.contains(new Point(0,2)));
	}

	@Test
	public void testTwoHorizontalMoves() {
		PatrolBoat pb = new PatrolBoat();
		pb.setStartPoint(new Point(0,0), Direction.RIGHT);

		try {
			board.addShip(pb);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
		assertTrue(shipLocation.contains(new Point(0,1)));

		ShipMover.moveShip(ShipType.PATROLBOAT, new Point(0,9), board);

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(0,1)));
		
		assertTrue(shipLocation.contains(new Point(0,5)));
		assertTrue(shipLocation.contains(new Point(0,4)));
		

		ShipMover.moveShip(ShipType.PATROLBOAT, new Point(0,9), board);

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(0,4)));
		
		assertTrue(shipLocation.contains(new Point(0,9)));
		assertTrue(shipLocation.contains(new Point(0,8)));
	}

	@Test
	public void testRotateDownMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(0,0), Direction.RIGHT);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(0,2)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(3,1), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,2)));

		assertTrue(shipLocation.contains(new Point(0,0)));
		assertTrue(shipLocation.contains(new Point(1,0)));
		assertTrue(shipLocation.contains(new Point(2,0)));
	}

	@Test
	public void testRotateUpMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(5,5), Direction.RIGHT);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(5,7)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(3,4), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(5,7)));

		assertTrue(shipLocation.contains(new Point(5,5)));
		assertTrue(shipLocation.contains(new Point(4,5)));
		assertTrue(shipLocation.contains(new Point(3,5)));
	}

	@Test
	public void testRotateLeftMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(5,5), Direction.UP);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(3,5)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(6,3), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(3,5)));

		assertTrue(shipLocation.contains(new Point(5,3)));
		assertTrue(shipLocation.contains(new Point(5,4)));
		assertTrue(shipLocation.contains(new Point(5,5)));
	}

	@Test
	public void testRotateRightMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(5,5), Direction.UP);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(3,5)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(6,7), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(3,5)));

		assertTrue(shipLocation.contains(new Point(5,5)));
		assertTrue(shipLocation.contains(new Point(5,6)));
		assertTrue(shipLocation.contains(new Point(5,7)));
	}

	@Test
	public void testComplexMove() {
		Submarine s = new Submarine();
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		try {
			board.addShip(s);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipLocation;
		shipLocation = board.getShipLocation(ShipType.SUBMARINE);
		assertTrue(shipLocation.contains(new Point(0,0)));

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(5,5), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,0)));

		assertTrue(shipLocation.contains(new Point(5,0)));
		assertTrue(shipLocation.contains(new Point(4,0)));
		assertTrue(shipLocation.contains(new Point(3,0)));
		

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(5,5), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,0)));
		assertFalse(shipLocation.contains(new Point(3,0)));

		assertTrue(shipLocation.contains(new Point(5,0)));
		assertTrue(shipLocation.contains(new Point(5,1)));
		assertTrue(shipLocation.contains(new Point(5,2)));
		

		ShipMover.moveShip(ShipType.SUBMARINE, new Point(5,5), board);

		shipLocation = board.getShipLocation(ShipType.SUBMARINE);

		assertFalse(shipLocation.contains(new Point(0,0)));
		assertFalse(shipLocation.contains(new Point(3,0)));
		assertFalse(shipLocation.contains(new Point(5,0)));

		assertTrue(shipLocation.contains(new Point(5,4)));
		assertTrue(shipLocation.contains(new Point(5,3)));
		assertTrue(shipLocation.contains(new Point(5,2)));
	}
}
