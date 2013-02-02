package tests.ships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ships.PatrolBoat;
import ships.Point;
import ships.Ship;


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
		assertEquals(new Point(0,1), pb.getEndPoint());
	}
	
	@Test
	public void testSetStartPoint() {
		pb.setStartPoint(new Point(1,0), Ship.Direction.UP);
		assertEquals(new Point(1,0), pb.getStartPoint());
		assertEquals(new Point(0,0), pb.getEndPoint());
		
		pb.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
		assertEquals(new Point(0,0), pb.getStartPoint());
		assertEquals(new Point(1,0), pb.getEndPoint());
		
		pb.setStartPoint(new Point(0,0), Ship.Direction.RIGHT);
		assertEquals(new Point(0,0), pb.getStartPoint());
		assertEquals(new Point(0,1), pb.getEndPoint());
		
		pb.setStartPoint(new Point(0,1), Ship.Direction.LEFT);
		assertEquals(new Point(0,1), pb.getStartPoint());
		assertEquals(new Point(0,0), pb.getEndPoint());
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
	
	@Test
	public void testGetShipName() {
		assertEquals("Patrol Boat", pb.getName());
	}
}
