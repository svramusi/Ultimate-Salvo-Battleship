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
	public void testConvertToDamageIndex() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertEquals(0, c.convertToDamageIndex(new Point(0,0)));
		assertEquals(1, c.convertToDamageIndex(new Point(1,0)));
		assertEquals(2, c.convertToDamageIndex(new Point(2,0)));
		assertEquals(3, c.convertToDamageIndex(new Point(3,0)));
		assertEquals(4, c.convertToDamageIndex(new Point(4,0)));

		assertEquals(-1, c.convertToDamageIndex(new Point(9,9)));
	}

	@Test
	public void testTakesDamage() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(c.isAHit(new Point(0,0), true));
		assertTrue(c.isAHit(new Point(1,0), true));
		assertFalse(c.isAHit(new Point(9,9), true));
	}
	
	@Test
	public void testSunk() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(c.isAHit(new Point(0,0), true));
		assertTrue(c.isAHit(new Point(1,0), true));
		assertTrue(c.isAHit(new Point(2,0), true));
		assertTrue(c.isAHit(new Point(3,0), true));
		assertTrue(c.isAHit(new Point(4,0), true));
		
		assertTrue(c.isSunk());
	}
	
	@Test
	public void testIsDamaged() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(c.isAHit(new Point(0,0), true));

		assertTrue(c.isDamaged(new Point(0,0)));
		assertFalse(c.isDamaged(new Point(1,0)));
		assertFalse(c.isDamaged(new Point(9,9)));
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
	
	@Test
	public void testIsSubmerged() {
		assertFalse(c.isSubmerged());
	}
	@Test
	public void test90DegRotation() {
		assertTrue(c.is90DegRotation(Direction.UP, Direction.RIGHT));
		assertTrue(c.is90DegRotation(Direction.UP, Direction.LEFT));
		assertFalse(c.is90DegRotation(Direction.UP, Direction.DOWN));
		assertFalse(c.is90DegRotation(Direction.UP, Direction.UP));

		assertTrue(c.is90DegRotation(Direction.DOWN, Direction.RIGHT));
		assertTrue(c.is90DegRotation(Direction.DOWN, Direction.LEFT));
		assertFalse(c.is90DegRotation(Direction.DOWN, Direction.UP));
		assertFalse(c.is90DegRotation(Direction.DOWN, Direction.DOWN));

		assertTrue(c.is90DegRotation(Direction.LEFT, Direction.UP));
		assertTrue(c.is90DegRotation(Direction.LEFT, Direction.DOWN));
		assertFalse(c.is90DegRotation(Direction.LEFT, Direction.RIGHT));
		assertFalse(c.is90DegRotation(Direction.LEFT, Direction.LEFT));

		assertTrue(c.is90DegRotation(Direction.RIGHT, Direction.UP));
		assertTrue(c.is90DegRotation(Direction.RIGHT, Direction.DOWN));
		assertFalse(c.is90DegRotation(Direction.RIGHT, Direction.LEFT));
		assertFalse(c.is90DegRotation(Direction.RIGHT, Direction.RIGHT));
	}

	@Test
	public void testRotation() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertFalse(c.isValidRotation(new Point(0,0), Direction.DOWN));
		assertFalse(c.isValidRotation(new Point(1,0), Direction.DOWN));

		assertTrue(c.isValidRotation(new Point(0,0), Direction.RIGHT)); //Pivot right
		assertTrue(c.isValidRotation(new Point(0,4), Direction.LEFT)); //Pivot right, swapping start and end

		assertFalse(c.isValidRotation(new Point(4,0), Direction.DOWN)); //180 deg rotation
		assertFalse(c.isValidRotation(new Point(8,0), Direction.UP)); //180 deg rotation

		c.setStartPoint(new Point(4,0), Direction.DOWN);
		assertFalse(c.isValidRotation(new Point(4,0), Direction.UP));
	}

	@Test
	public void testIsMoveWithinRowOrCol() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);
		assertTrue(c.isMoveWithinRowOrCol(new Point(1,0), Direction.DOWN));

		c.setStartPoint(new Point(9,0), Direction.UP);
		assertTrue(c.isMoveWithinRowOrCol(new Point(8,0), Direction.UP));

		c.setStartPoint(new Point(0,0), Direction.RIGHT);
		assertTrue(c.isMoveWithinRowOrCol(new Point(0,1), Direction.RIGHT));

		c.setStartPoint(new Point(0,9), Direction.LEFT);
		assertTrue(c.isMoveWithinRowOrCol(new Point(0,8), Direction.LEFT));
	}

	@Test
	public void testValidMove() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(c.isValidMove(new Point(1,0), Direction.DOWN));
		assertTrue(c.isValidMove(new Point(0,0), Direction.RIGHT)); //Pivot right
		assertTrue(c.isValidMove(new Point(0,4), Direction.LEFT)); //Pivot right, swapping start and end

		assertFalse(c.isValidMove(new Point(2,0), Direction.DOWN));
		assertFalse(c.isValidMove(new Point(8,0), Direction.UP));
		assertFalse(c.isValidMove(new Point(0,4), Direction.RIGHT));


		assertFalse(c.isValidMove(new Point(1,1), Direction.DOWN)); //Move parallel and down
		assertFalse(c.isValidMove(new Point(0,1), Direction.DOWN)); //Move parallel

		c.setStartPoint(new Point(0,4), Direction.DOWN);
		assertTrue(c.isValidMove(new Point(4,8), Direction.LEFT));
	}
	
	@Test
	public void testValidShot() {
		c.setStartPoint(new Point(0,0), Direction.DOWN);

		assertTrue(c.isValidShot(new Point(0,1)));
		assertTrue(c.isValidShot(new Point(9,0)));
		assertTrue(c.isValidShot(new Point(1,5)));
		assertTrue(c.isValidShot(new Point(9,5)));

		assertFalse(c.isValidShot(new Point(9,7)));
	}

    @Test
    public void testDoIHaveRightOfWay() {
        assertFalse(c.doIHaveRightOfWay(ShipType.BATTLESHIP));
        assertFalse(c.doIHaveRightOfWay(ShipType.DESTROYER));
        assertFalse(c.doIHaveRightOfWay(ShipType.PATROLBOAT));
        assertFalse(c.doIHaveRightOfWay(ShipType.SUBMARINE));
    }
}
