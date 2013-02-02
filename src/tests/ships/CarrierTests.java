package tests.ships;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ships.Carrier;
import ships.Point;
import ships.Ship;
import ships.Ship.Direction;
import ships.Ship.ShipType;


public class CarrierTests {

	private Carrier c;
	@Before
	public void setUp() throws Exception {
		c = new Carrier();
	}
	
	@Test
	public void testShipGetMoveDistance() {
		assertEquals(1, c.getMoveDistance());
	}
	
	@Test
	public void testShootDistance() {
		assertEquals(5, c.getShootDistance());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(5, c.getSize());
	}

	@Test
	public void testGetStartPoint() {
		assertEquals(new Point(0,0), c.getStartPoint());
		assertEquals(new Point(0,4), c.getEndPoint());
	}
	
	@Test
	public void testSetStartPoint() {
		c.setStartPoint(new Point(4,0), Ship.Direction.UP);
		assertEquals(new Point(4,0), c.getStartPoint());
		assertEquals(new Point(0,0), c.getEndPoint());
		
		c.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
		assertEquals(new Point(0,0), c.getStartPoint());
		assertEquals(new Point(4,0), c.getEndPoint());
		
		c.setStartPoint(new Point(0,0), Ship.Direction.RIGHT);
		assertEquals(new Point(0,0), c.getStartPoint());
		assertEquals(new Point(0,4), c.getEndPoint());
		
		c.setStartPoint(new Point(0,4), Ship.Direction.LEFT);
		assertEquals(new Point(0,4), c.getStartPoint());
		assertEquals(new Point(0,0), c.getEndPoint());
	}
	
	@Test
	public void testInitialDamage() {
		assertEquals(0, c.damage());
	}
	
	@Test
	public void testTakesDamage() {
		assertEquals(0, c.damage());
		c.takeDamage();
		assertEquals(1, c.damage());
	}
	
	@Test
	public void testSunk() {
		c.takeDamage();
		c.takeDamage();
		c.takeDamage();
		c.takeDamage();
		c.takeDamage();
		assertTrue(c.isSunk());
	}
	
	@Test
	public void testGetShipName() {
		assertEquals("CARRIER", c.getName());
	}
	
	@Test
	public void testGetShipLocation() {
		List<Point> shipLocation;

		c.setStartPoint(new Point(4,0), Direction.UP);
		
		shipLocation = c.getShipLocation();
		assertEquals(new Point(4,0), shipLocation.get(0));
		assertEquals(new Point(3,0), shipLocation.get(1));
		assertEquals(new Point(2,0), shipLocation.get(2));
		assertEquals(new Point(1,0), shipLocation.get(3));
		assertEquals(new Point(0,0), shipLocation.get(4));
		
		c.setStartPoint(new Point(0,0), Direction.DOWN);
		
		shipLocation = c.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(1,0), shipLocation.get(1));
		assertEquals(new Point(2,0), shipLocation.get(2));
		assertEquals(new Point(3,0), shipLocation.get(3));
		assertEquals(new Point(4,0), shipLocation.get(4));
		
		c.setStartPoint(new Point(0,4), Direction.LEFT);
		
		shipLocation = c.getShipLocation();
		assertEquals(new Point(0,4), shipLocation.get(0));
		assertEquals(new Point(0,3), shipLocation.get(1));
		assertEquals(new Point(0,2), shipLocation.get(2));
		assertEquals(new Point(0,1), shipLocation.get(3));
		assertEquals(new Point(0,0), shipLocation.get(4));
		
		c.setStartPoint(new Point(0,0), Direction.RIGHT);
		
		shipLocation = c.getShipLocation();
		assertEquals(new Point(0,0), shipLocation.get(0));
		assertEquals(new Point(0,1), shipLocation.get(1));
		assertEquals(new Point(0,2), shipLocation.get(2));
		assertEquals(new Point(0,3), shipLocation.get(3));
		assertEquals(new Point(0,4), shipLocation.get(4));
	}
	
	@Test
	public void testShipType() {
		assertEquals(ShipType.CARRIER, c.getShipType());
	}
}
