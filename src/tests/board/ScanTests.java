package tests.board;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import ships.*;
import ships.Ship.Direction;

import battleshipExceptions.InvalidShipPositionException;
import board.*;

public class ScanTests {

	private Scan scan;
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
	public void testConvertEndPointsRow() {
		List<Point> placesToScan;
		
		scan = new Scan(new Point(0,0), new Point(0,5), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(0,1)));
		assertTrue(placesToScan.contains(new Point(0,2)));
		assertTrue(placesToScan.contains(new Point(0,3)));
		assertTrue(placesToScan.contains(new Point(0,4)));
		assertTrue(placesToScan.contains(new Point(0,5)));
		assertEquals(6, placesToScan.size());
		

		scan = new Scan(new Point(1,1), new Point(1,6), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(1,2)));
		assertTrue(placesToScan.contains(new Point(1,3)));
		assertTrue(placesToScan.contains(new Point(1,4)));
		assertTrue(placesToScan.contains(new Point(1,5)));
		assertTrue(placesToScan.contains(new Point(1,6)));
		assertEquals(6, placesToScan.size());

		
		scan = new Scan(new Point(1,7), new Point(1,9), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(1,7)));
		assertTrue(placesToScan.contains(new Point(1,8)));
		assertTrue(placesToScan.contains(new Point(1,9)));
		assertEquals(3, placesToScan.size());


		scan = new Scan(new Point(1,9), new Point(1,7), board);
		placesToScan = scan.getPointsToScan();
		
		assertTrue(placesToScan.contains(new Point(1,9)));
		assertTrue(placesToScan.contains(new Point(1,8)));
		assertTrue(placesToScan.contains(new Point(1,7)));
		assertEquals(3, placesToScan.size());
	}
	
	@Test
	public void testConvertEndPointsCol() {
		List<Point> placesToScan;
		
		scan = new Scan(new Point(0,0), new Point(5,0), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(1,0)));
		assertTrue(placesToScan.contains(new Point(2,0)));
		assertTrue(placesToScan.contains(new Point(3,0)));
		assertTrue(placesToScan.contains(new Point(4,0)));
		assertTrue(placesToScan.contains(new Point(5,0)));
		assertEquals(6, placesToScan.size());
		

		scan = new Scan(new Point(1,1), new Point(6,1), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(2,1)));
		assertTrue(placesToScan.contains(new Point(3,1)));
		assertTrue(placesToScan.contains(new Point(4,1)));
		assertTrue(placesToScan.contains(new Point(5,1)));
		assertTrue(placesToScan.contains(new Point(6,1)));
		assertEquals(6, placesToScan.size());


		scan = new Scan(new Point(7,1), new Point(9,1), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(7,1)));
		assertTrue(placesToScan.contains(new Point(8,1)));
		assertTrue(placesToScan.contains(new Point(9,1)));
		assertEquals(3, placesToScan.size());

		
		scan = new Scan(new Point(9,1), new Point(7,1), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(7,1)));
		assertTrue(placesToScan.contains(new Point(8,1)));
		assertTrue(placesToScan.contains(new Point(9,1)));
		assertEquals(3, placesToScan.size());
	}
	
	@Test
	public void testConvertEndPointsBox() {
		List<Point> placesToScan;

		scan = new Scan(new Point(0,0), new Point(1,2), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(0,1)));
		assertTrue(placesToScan.contains(new Point(0,2)));
		assertTrue(placesToScan.contains(new Point(1,0)));
		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(1,2)));
		assertEquals(6, placesToScan.size());
		

		scan = new Scan(new Point(8,7), new Point(9,9), board);
		placesToScan = scan.getPointsToScan();
		
		assertTrue(placesToScan.contains(new Point(8,7)));
		assertTrue(placesToScan.contains(new Point(8,8)));
		assertTrue(placesToScan.contains(new Point(8,9)));
		assertTrue(placesToScan.contains(new Point(9,7)));
		assertTrue(placesToScan.contains(new Point(9,8)));
		assertTrue(placesToScan.contains(new Point(9,9)));
		assertEquals(6, placesToScan.size());
		
		
		
		


		scan = new Scan(new Point(0,0), new Point(2,1), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(0,1)));
		assertTrue(placesToScan.contains(new Point(1,0)));
		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(2,0)));
		assertTrue(placesToScan.contains(new Point(2,1)));
		assertEquals(6, placesToScan.size());
		

		scan = new Scan(new Point(7,8), new Point(9,9), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(7,8)));
		assertTrue(placesToScan.contains(new Point(7,9)));
		assertTrue(placesToScan.contains(new Point(8,8)));
		assertTrue(placesToScan.contains(new Point(8,9)));
		assertTrue(placesToScan.contains(new Point(9,8)));
		assertTrue(placesToScan.contains(new Point(9,9)));
		assertEquals(6, placesToScan.size());
	}

	@Test
	public void testConvertEndPointsDiagonal() {
		List<Point> placesToScan;

		scan = new Scan(new Point(0,0), new Point(5,5), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(2,2)));
		assertTrue(placesToScan.contains(new Point(3,3)));
		assertTrue(placesToScan.contains(new Point(4,4)));
		assertTrue(placesToScan.contains(new Point(5,5)));
		assertEquals(6, placesToScan.size());


		scan = new Scan(new Point(5,5), new Point(0,0), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(0,0)));
		assertTrue(placesToScan.contains(new Point(1,1)));
		assertTrue(placesToScan.contains(new Point(2,2)));
		assertTrue(placesToScan.contains(new Point(3,3)));
		assertTrue(placesToScan.contains(new Point(4,4)));
		assertTrue(placesToScan.contains(new Point(5,5)));
		assertEquals(6, placesToScan.size());


		scan = new Scan(new Point(5,5), new Point(9,1), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(5,5)));
		assertTrue(placesToScan.contains(new Point(6,4)));
		assertTrue(placesToScan.contains(new Point(7,3)));
		assertTrue(placesToScan.contains(new Point(8,2)));
		assertTrue(placesToScan.contains(new Point(9,1)));
		assertEquals(5, placesToScan.size());
		

		scan = new Scan(new Point(9,1), new Point(5,5), board);
		placesToScan = scan.getPointsToScan();

		assertTrue(placesToScan.contains(new Point(5,5)));
		assertTrue(placesToScan.contains(new Point(6,4)));
		assertTrue(placesToScan.contains(new Point(7,3)));
		assertTrue(placesToScan.contains(new Point(8,2)));
		assertTrue(placesToScan.contains(new Point(9,1)));
		assertEquals(5, placesToScan.size());
	}

	@Test
	public void testScanColFindsCarrier() {
		try {
			c.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipFound;
		scan = new Scan(new Point(0,0), new Point(5,0), board);
		
		shipFound = scan.findShips();
		assertTrue(shipFound.contains(new Point(0,0)));
		assertTrue(shipFound.contains(new Point(1,0)));
		assertTrue(shipFound.contains(new Point(2,0)));
		assertTrue(shipFound.contains(new Point(3,0)));
		assertTrue(shipFound.contains(new Point(4,0)));
		assertEquals(5, shipFound.size());
		
		assertFalse(c.isSunk());
	}

	@Test
	public void testScanRowFindsCarrier() {
		try {
			c.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipFound;
		scan = new Scan(new Point(0,0), new Point(0,5), board);
		
		shipFound = scan.findShips();
		assertTrue(shipFound.contains(new Point(0,0)));
		assertEquals(1, shipFound.size());
		
		assertFalse(c.isSunk());
	}

	@Test
	public void testScanBoxFindsCarrier() {
		try {
			c.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipFound;
		scan = new Scan(new Point(0,0), new Point(1,2), board);
		
		shipFound = scan.findShips();
		assertTrue(shipFound.contains(new Point(0,0)));
		assertTrue(shipFound.contains(new Point(1,0)));
		assertEquals(2, shipFound.size());
		
		assertFalse(c.isSunk());
	}

	@Test
	public void testScanDiagonalFindsCarrier() {
		try {
			c.setStartPoint(new Point(0,0), Direction.DOWN);
			board.addShip(c);
		} catch (InvalidShipPositionException e) {
			fail("caught InvalidShipPositionException when I shouldn't have");
		}
		
		List<Point> shipFound;
		scan = new Scan(new Point(0,0), new Point(5,5), board);
		
		shipFound = scan.findShips();
		assertTrue(shipFound.contains(new Point(0,0)));
		assertEquals(1, shipFound.size());
		
		assertFalse(c.isSunk());
	}
}
