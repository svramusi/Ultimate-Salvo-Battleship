package tests.battleship;

import static org.junit.Assert.*;
import battleship.Carrier;
import battleship.Point;

import org.junit.Before;
import org.junit.Test;


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
	}
	
	@Test
	public void testGetEndPoint() {
		assertEquals(new Point(0,5), c.getEndPoint());
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
}
