package tests.ships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ships.Battleship;
import ships.Point;
import ships.Ship;


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
		assertEquals(new Point(0,3), b.getEndPoint());
	}
	
	@Test
	public void testSetStartPoint() {
		b.setStartPoint(new Point(3,0), Ship.Direction.UP);
		assertEquals(new Point(3,0), b.getStartPoint());
		assertEquals(new Point(0,0), b.getEndPoint());
		
		b.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
		assertEquals(new Point(0,0), b.getStartPoint());
		assertEquals(new Point(3,0), b.getEndPoint());
		
		b.setStartPoint(new Point(0,0), Ship.Direction.RIGHT);
		assertEquals(new Point(0,0), b.getStartPoint());
		assertEquals(new Point(0,3), b.getEndPoint());
		
		b.setStartPoint(new Point(0,3), Ship.Direction.LEFT);
		assertEquals(new Point(0,3), b.getStartPoint());
		assertEquals(new Point(0,0), b.getEndPoint());
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
	
	@Test
	public void testGetShipName() {
		assertEquals("Battleship", b.getName());
	}
}
