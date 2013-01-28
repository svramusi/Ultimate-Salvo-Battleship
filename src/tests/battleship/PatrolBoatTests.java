package tests.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import battleship.PatrolBoat;
import battleship.Point;

public class PatrolBoatTests {

	private PatrolBoat pb;
	@Before
	public void setUp() throws Exception {
		pb = new PatrolBoat();
	}
	
	@Test
	public void testShipGetMoveDistance() {
		assertEquals(4, pb.getMoveDistance());
	}
	
	@Test
	public void testShootDistance() {
		assertEquals(2, pb.getShootDistance());
	}
	
	@Test
	public void testGetSize() {
		assertEquals(2, pb.getSize());
	}

	@Test
	public void testGetStartPoint() {
		assertEquals(new Point(0,0), pb.getStartPoint());
	}
	
	@Test
	public void testGetEndPoint() {
		assertEquals(new Point(0,2), pb.getEndPoint());
	}
	
	@Test
	public void testInitialDamage() {
		assertEquals(0, pb.damage());
	}
	
	@Test
	public void testTakesDamage() {
		assertEquals(0, pb.damage());
		pb.takeDamage();
		assertEquals(1, pb.damage());
	}
	
	@Test
	public void testSunk() {
		pb.takeDamage();
		pb.takeDamage();
		assertTrue(pb.isSunk());
	}
}
