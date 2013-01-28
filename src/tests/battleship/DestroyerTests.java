package tests.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import battleship.Destroyer;
import battleship.Point;

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
	}
	
	@Test
	public void testGetEndPoint() {
		assertEquals(new Point(0,3), d.getEndPoint());
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
}
