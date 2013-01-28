package tests.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import battleship.Point;
import battleship.Submarine;

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
	}
	
	@Test
	public void testGetEndPoint() {
		assertEquals(new Point(0,3), s.getEndPoint());
	}
	
	@Test
	public void testInitialDamage() {
		assertEquals(0, s.damage());
	}
	
	@Test
	public void testTakesDamage() {
		assertEquals(0, s.damage());
		s.takeDamage();
		assertEquals(1, s.damage());
	}
	
	@Test
	public void testSunk() {
		s.takeDamage();
		assertTrue(s.isSunk());
	}
}
