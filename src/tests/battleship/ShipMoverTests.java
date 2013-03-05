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
		
		ShipMover shipMover = new ShipMover(ShipType.CARRIER, new Point(1,1), board);
		shipMover.moveShip();

		shipLocation = board.getShipLocation(ShipType.CARRIER);
		assertTrue(shipLocation.contains(new Point(0,0)));

		
		shipMover = new ShipMover(ShipType.CARRIER, new Point(5,9), board);
		shipMover.moveShip();

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
		
		shipMover = new ShipMover(ShipType.SUBMARINE, new Point(3,1), board);
		shipMover.moveShip();

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
		
		ShipMover shipMover = new ShipMover(ShipType.SUBMARINE, new Point(4,1), board);
		shipMover.moveShip();

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
		
		ShipMover shipMover = new ShipMover(ShipType.SUBMARINE, new Point(0,1), board);
		shipMover.moveShip();

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
		
		ShipMover shipMover = new ShipMover(ShipType.PATROLBOAT, new Point(0,1), board);
		shipMover.moveShip();

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(8,0)));
		
		assertTrue(shipLocation.contains(new Point(4,0)));
		assertTrue(shipLocation.contains(new Point(5,0)));
		

		shipMover.moveShip();

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
		
		ShipMover shipMover = new ShipMover(ShipType.SUBMARINE, new Point(1,4), board);
		shipMover.moveShip();

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
		
		ShipMover shipMover = new ShipMover(ShipType.PATROLBOAT, new Point(0,9), board);
		shipMover.moveShip();

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(0,1)));
		
		assertTrue(shipLocation.contains(new Point(0,5)));
		assertTrue(shipLocation.contains(new Point(0,4)));
		

		shipMover.moveShip();

		shipLocation = board.getShipLocation(ShipType.PATROLBOAT);

		assertFalse(shipLocation.contains(new Point(0,4)));
		
		assertTrue(shipLocation.contains(new Point(0,9)));
		assertTrue(shipLocation.contains(new Point(0,8)));
	}

}
