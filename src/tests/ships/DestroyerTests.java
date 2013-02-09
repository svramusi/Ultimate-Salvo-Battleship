package tests.ships;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ships.Destroyer;
import ships.Point;
import ships.Ship;
import ships.Ship.Direction;
import ships.Ship.ShipType;


public class DestroyerTests {

	private Destroyer d;
	@Before
	public void setUp() throws Exception {
		d = new Destroyer();
	}
	
	@Test
	public void testShipGetMoveDistance() {
		assertEquals(3, d.getMoveDistance());
	}
	
	@Test
	public void testShootDistance() {
		assertEquals(3, d.getShootDistance());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(3, d.getSize());
	}

	@Test
	public void testGetStartPoint() {
		assertEquals(new Point(0,0), d.getStartPoint());
		assertEquals(new Point(0,2), d.getEndPoint());
	}
	
	@Test
	public void testSetStartPoint() {
		d.setStartPoint(new Point(2,0), Ship.Direction.UP);
		assertEquals(new Point(2,0), d.getStartPoint());
		assertEquals(new Point(0,0), d.getEndPoint());
		
		d.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
		assertEquals(new Point(0,0), d.getStartPoint());
		assertEquals(new Point(2,0), d.getEndPoint());
		
		d.setStartPoint(new Point(0,0), Ship.Direction.RIGHT);
		assertEquals(new Point(0,0), d.getStartPoint());
		assertEquals(new Point(0,2), d.getEndPoint());
		
		d.setStartPoint(new Point(0,2), Ship.Direction.LEFT);
		assertEquals(new Point(0,2), d.getStartPoint());
		assertEquals(new Point(0,0), d.getEndPoint());
	}

	@Test
	public void testConvertToDamageIndex() {
		d.setStartPoint(new Point(0,0), Direction.DOWN);

		assertEquals(0, d.convertToDamageIndex(new Point(0,0)));
		assertEquals(1, d.convertToDamageIndex(new Point(1,0)));
		assertEquals(2, d.convertToDamageIndex(new Point(2,0)));

		assertEquals(-1, d.convertToDamageIndex(new Point(9,9)));
	}

	@Test
	public void testTakesDamage() {
		d.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(d.takesDamage(new Point(0,0)));
		assertTrue(d.takesDamage(new Point(1,0)));
		assertFalse(d.takesDamage(new Point(9,9)));
	}
	
	@Test
	public void testSunk() {
		d.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(d.takesDamage(new Point(0,0)));
		assertTrue(d.takesDamage(new Point(1,0)));
		assertTrue(d.takesDamage(new Point(2,0)));
		
		assertTrue(d.isSunk());
	}
	
	@Test
	public void testIsDamaged() {
		d.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(d.takesDamage(new Point(0,0)));

		assertTrue(d.isDamaged(new Point(0,0)));
		assertFalse(d.isDamaged(new Point(1,0)));
		assertFalse(d.isDamaged(new Point(9,9)));
	}
	
	@Test
	public void testGetShipName() {
		assertEquals("DESTROYER", d.getName());
	}
	
	@Test
	public void testGetShipLocation() {
		List<Point> shipLocation;

		d.setStartPoint(new Point(2,0), Direction.UP);
		
		shipLocation = d.getShipLocation();
		assertEquals(new Point(2,0), shipLocation.get(0));
		assertEquals(new Point(1,0), shipLocation.get(1));
		assertEquals(new Point(0,0), shipLocation.get(2));
		
		d.setStartPoint(new Point(0,0), Direction.DOWN);
		
		shipLocation = d.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(1,0), shipLocation.get(1));
		assertEquals(new Point(2,0), shipLocation.get(2));
		
		d.setStartPoint(new Point(0,2), Direction.LEFT);
		
		shipLocation = d.getShipLocation();
		assertEquals(new Point(0,2), shipLocation.get(0));
		assertEquals(new Point(0,1), shipLocation.get(1));
		assertEquals(new Point(0,0), shipLocation.get(2));
		
		d.setStartPoint(new Point(0,0), Direction.RIGHT);
		
		shipLocation = d.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(0,1), shipLocation.get(1));
		assertEquals(new Point(0,2), shipLocation.get(2));
	}
	
	@Test
	public void testShipType() {
		assertEquals(ShipType.DESTROYER, d.getShipType());
	}
	
	@Test
	public void testIsSubmerged() {
		assertFalse(d.isSubmerged());
	}
	
	@Test
	public void testValidShot() {
		d.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(d.isValidShot(new Point(0,1)));
		assertTrue(d.isValidShot(new Point(5,0)));
		assertTrue(d.isValidShot(new Point(1,3)));
		assertTrue(d.isValidShot(new Point(5,3)));

		assertFalse(d.isValidShot(new Point(6,0)));
		assertFalse(d.isValidShot(new Point(5,5)));
	}
}
