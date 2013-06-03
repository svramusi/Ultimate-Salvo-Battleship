package tests.ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import battleship.Shot;

import java.util.*;

import ships.Battleship;
import ships.Point;
import ships.Ship;
import ships.Ship.Direction;
import ships.Ship.ShipType;


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
    public void testConvertToDamageIndex() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertEquals(0, b.convertToDamageIndex(new Point(0,0)));
        assertEquals(1, b.convertToDamageIndex(new Point(1,0)));
        assertEquals(2, b.convertToDamageIndex(new Point(2,0)));
        assertEquals(3, b.convertToDamageIndex(new Point(3,0)));

        assertEquals(-1, b.convertToDamageIndex(new Point(9,9)));
    }

    @Test
    public void testTakesDamage() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(b.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));
        assertTrue(b.isAHit(new Shot(new Point(1,0), ShipType.CARRIER), true));
        assertFalse(b.isAHit(new Shot(new Point(9,9), ShipType.CARRIER), true));
    }
    
    @Test
    public void testSunk() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(b.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));
        assertTrue(b.isAHit(new Shot(new Point(1,0), ShipType.CARRIER), true));
        assertTrue(b.isAHit(new Shot(new Point(2,0), ShipType.CARRIER), true));
        assertTrue(b.isAHit(new Shot(new Point(3,0), ShipType.CARRIER), true));
        
        assertTrue(b.isSunk());
    }
    
    @Test
    public void testIsDamaged() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(b.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));

        assertTrue(b.isDamaged(new Point(0,0)));
        assertFalse(b.isDamaged(new Point(1,0)));
        assertFalse(b.isDamaged(new Point(9,9)));
    }
    
    @Test
    public void testGetSunkBySubOnOneShot() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(b.isAHit(new Shot(new Point(0,0), ShipType.SUBMARINE), true));

        assertTrue(b.isDamaged(new Point(0,0)));
        assertTrue(b.isDamaged(new Point(1,0)));
        assertTrue(b.isSunk());
    }
    
    @Test
    public void testGetShipName() {
        assertEquals("BATTLESHIP", b.getName());
    }
    
    @Test
    public void testGetShipLocation() {
        List<Point> shipLocation;

        b.setStartPoint(new Point(3,0), Direction.UP);
        
        shipLocation = b.getShipLocation();
        assertEquals(new Point(3,0), shipLocation.get(0));
        assertEquals(new Point(2,0), shipLocation.get(1));
        assertEquals(new Point(1,0), shipLocation.get(2));
        assertEquals(new Point(0,0), shipLocation.get(3));
        
        b.setStartPoint(new Point(0,0), Direction.DOWN);
        
        shipLocation = b.getShipLocation();
        assertEquals(new Point(0,0), shipLocation.get(0));
        assertEquals(new Point(1,0), shipLocation.get(1));
        assertEquals(new Point(2,0), shipLocation.get(2));
        assertEquals(new Point(3,0), shipLocation.get(3));
        
        b.setStartPoint(new Point(0,3), Direction.LEFT);
        
        shipLocation = b.getShipLocation();
        assertEquals(new Point(0,3), shipLocation.get(0));
        assertEquals(new Point(0,2), shipLocation.get(1));
        assertEquals(new Point(0,1), shipLocation.get(2));
        assertEquals(new Point(0,0), shipLocation.get(3));
        
        b.setStartPoint(new Point(0,0), Direction.RIGHT);
        
        shipLocation = b.getShipLocation();
        assertEquals(new Point(0,0), shipLocation.get(0));
        assertEquals(new Point(0,1), shipLocation.get(1));
        assertEquals(new Point(0,2), shipLocation.get(2));
        assertEquals(new Point(0,3), shipLocation.get(3));
    }
    
    @Test
    public void testShipType() {
        assertEquals(ShipType.BATTLESHIP, b.getShipType());
    }
    
    @Test
    public void testIsSubmerged() {
        assertFalse(b.isSubmerged());
    }
    
    @Test
    public void testValidShot() {
        b.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(b.isValidShot(new Point(1,0)));
        assertTrue(b.isValidShot(new Point(4,0)));
        assertTrue(b.isValidShot(new Point(4,4)));

        assertFalse(b.isValidShot(new Point(0,5)));
    }

    @Test
    public void testDoIHaveRightOfWay() {
        assertTrue(b.doIHaveRightOfWay(ShipType.CARRIER));
        assertFalse(b.doIHaveRightOfWay(ShipType.DESTROYER));
        assertFalse(b.doIHaveRightOfWay(ShipType.PATROLBOAT));
        assertFalse(b.doIHaveRightOfWay(ShipType.SUBMARINE));
    }
}
