package tests.ships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ships.Point;
import ships.Ship;
import ships.Submarine;
import ships.Ship.Direction;
import ships.Ship.ShipType;


public class SubmarineTests {

	private Submarine s;
	@Before
	public void setUp() throws Exception {
		s = new Submarine();
	}
	
	@Test
	public void testShipGetMoveDistance() {
		assertEquals(5, s.getMoveDistance());
	}
	
	@Test
	public void testShootDistance() {
		assertEquals(1, s.getShootDistance());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(3, s.getSize());
	}

	@Test
	public void testGetStartPoint() {
		assertEquals(new Point(0,0), s.getStartPoint());
		assertEquals(new Point(0,2), s.getEndPoint());
	}
	
	@Test
	public void testSetStartPoint() {
		s.setStartPoint(new Point(2,0), Ship.Direction.UP);
		assertEquals(new Point(2,0), s.getStartPoint());
		assertEquals(new Point(0,0), s.getEndPoint());
		
		s.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
		assertEquals(new Point(0,0), s.getStartPoint());
		assertEquals(new Point(2,0), s.getEndPoint());
		
		s.setStartPoint(new Point(0,0), Ship.Direction.RIGHT);
		assertEquals(new Point(0,0), s.getStartPoint());
		assertEquals(new Point(0,2), s.getEndPoint());
		
		s.setStartPoint(new Point(0,2), Ship.Direction.LEFT);
		assertEquals(new Point(0,2), s.getStartPoint());
		assertEquals(new Point(0,0), s.getEndPoint());
	}

	@Test
	public void testConvertToDamageIndex() {
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		assertEquals(0, s.convertToDamageIndex(new Point(0,0)));
		assertEquals(1, s.convertToDamageIndex(new Point(1,0)));
		assertEquals(2, s.convertToDamageIndex(new Point(2,0)));

		assertEquals(-1, s.convertToDamageIndex(new Point(9,9)));
	}

	@Test
	public void testTakesDamage() {
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(s.takesDamage(new Point(0,0)));
		assertTrue(s.takesDamage(new Point(1,0)));
		assertFalse(s.takesDamage(new Point(9,9)));
	}
	
	@Test
	public void testSunk() {
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(s.takesDamage(new Point(0,0)));
		assertTrue(s.takesDamage(new Point(1,0)));
		assertTrue(s.takesDamage(new Point(2,0)));
		
		assertTrue(s.isSunk());
	}
	
	@Test
	public void testIsDamaged() {
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(s.takesDamage(new Point(0,0)));

		assertTrue(s.isDamaged(new Point(0,0)));
		assertFalse(s.isDamaged(new Point(1,0)));
		assertFalse(s.isDamaged(new Point(9,9)));
	}
	
	
	@Test
	public void testGetShipName() {
		assertEquals("SUBMARINE", s.getName());
	}

	@Test
	public void testGetShipLocation() {
		List<Point> shipLocation;

		s.setStartPoint(new Point(2,0), Direction.UP);
		
		shipLocation = s.getShipLocation();
		assertEquals(new Point(2,0), shipLocation.get(0));
		assertEquals(new Point(1,0), shipLocation.get(1));
		assertEquals(new Point(0,0), shipLocation.get(2));
		
		s.setStartPoint(new Point(0,0), Direction.DOWN);
		
		shipLocation = s.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(1,0), shipLocation.get(1));
		assertEquals(new Point(2,0), shipLocation.get(2));
		
		s.setStartPoint(new Point(0,2), Direction.LEFT);
		
		shipLocation = s.getShipLocation();
		assertEquals(new Point(0,2), shipLocation.get(0));
		assertEquals(new Point(0,1), shipLocation.get(1));
		assertEquals(new Point(0,0), shipLocation.get(2));
		
		s.setStartPoint(new Point(0,0), Direction.RIGHT);
		
		shipLocation = s.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(0,1), shipLocation.get(1));
		assertEquals(new Point(0,2), shipLocation.get(2));
	}
	
	@Test
	public void testShipType() {
		assertEquals(ShipType.SUBMARINE, s.getShipType());
	}
	
	@Test
	public void testIsSubmerged() {
		assertTrue(s.isSubmerged());
	}
	
	@Test
	public void testValidShot() {
		s.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(s.isValidShot(new Point(0,1)));
		assertTrue(s.isValidShot(new Point(3,0)));
		assertTrue(s.isValidShot(new Point(3,1)));
		assertTrue(s.isValidShot(new Point(2,1)));

		assertFalse(s.isValidShot(new Point(4,0)));
		assertFalse(s.isValidShot(new Point(3,2)));
	}
}
