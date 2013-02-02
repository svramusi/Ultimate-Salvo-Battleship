package tests.ships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ships.Destroyer;
import ships.Point;
import ships.Ship;


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
	public void testInitialDamage() {
		assertEquals(0, d.damage());
	}
	
	@Test
	public void testTakesDamage() {
		assertEquals(0, d.damage());
		d.takeDamage();
		assertEquals(1, d.damage());
	}
	
	@Test
	public void testSunk() {
		d.takeDamage();
		d.takeDamage();
		d.takeDamage();
		assertTrue(d.isSunk());
	}
	
	@Test
	public void testGetShipName() {
		assertEquals("Destroyer", d.getName());
	}
}
