package tests.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import battleship.Battleship;
import battleship.Point;

public class BattleshipTests {

	private Battleship b;
	@Before
	public void setUp() throws Exception {
		b = new Battleship();
	}
	
	@Test
	public void testShipGetMoveDistance() {
		assertEquals(2, b.getMoveDistance());
	}
	
	@Test
	public void testShootDistance() {
		assertEquals(4, b.getShootDistance());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(4, b.getSize());
	}

	@Test
	public void testGetStartPoint() {
		assertEquals(new Point(0,0), b.getStartPoint());
	}
	
	@Test
	public void testGetEndPoint() {
		assertEquals(new Point(0,4), b.getEndPoint());
	}
	
	@Test
	public void testInitialDamage() {
		assertEquals(0, b.damage());
	}
	
	@Test
	public void testTakesDamage() {
		assertEquals(0, b.damage());
		b.takeDamage();
		assertEquals(1, b.damage());
	}
	
	@Test
	public void testSunk() {
		b.takeDamage();
		b.takeDamage();
		b.takeDamage();
		b.takeDamage();
		assertTrue(b.isSunk());
	}
}
