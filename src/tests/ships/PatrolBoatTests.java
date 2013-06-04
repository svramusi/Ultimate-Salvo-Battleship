package tests.ships;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import battleship.Shot;

import ships.PatrolBoat;
import ships.Point;
import ships.Ship;
import ships.Ship.Direction;
import ships.Ship.ShipType;


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
    public void testConvertToDamageIndex() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertEquals(0, pb.convertToDamageIndex(new Point(0,0)));
        assertEquals(1, pb.convertToDamageIndex(new Point(1,0)));

        assertEquals(-1, pb.convertToDamageIndex(new Point(9,9)));
    }

    @Test
    public void testTakesDamage() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(pb.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));
        assertTrue(pb.isAHit(new Shot(new Point(1,0), ShipType.CARRIER), true));
        assertFalse(pb.isAHit(new Shot(new Point(9,9), ShipType.CARRIER), true));
    }
    
    @Test
    public void testSunk() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(pb.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));
        assertTrue(pb.isAHit(new Shot(new Point(1,0), ShipType.CARRIER), true));
        
        assertTrue(pb.isSunk());
    }
    
    @Test
    public void testIsDamaged() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(pb.isAHit(new Shot(new Point(0,0), ShipType.CARRIER), true));

        assertTrue(pb.isDamaged(new Point(0,0)));
        assertFalse(pb.isDamaged(new Point(1,0)));
        assertFalse(pb.isDamaged(new Point(9,9)));
    }
    
    @Test
    public void testGetSunkBySubOnOneShot() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(pb.isAHit(new Shot(new Point(0,0), ShipType.SUBMARINE), true));

        assertTrue(pb.isDamaged(new Point(0,0)));
        assertTrue(pb.isDamaged(new Point(1,0)));
        assertTrue(pb.isSunk());
    }
    
    @Test
    public void testGetShipName() {
        assertEquals("PATROLBOAT", pb.getName());
    }
    
    @Test
    public void testGetShipLocation() {
        List<Point> shipLocation;

        pb.setStartPoint(new Point(1,0), Direction.UP);
        
        shipLocation = pb.getShipLocation();
        assertEquals(new Point(1,0), shipLocation.get(0));
        assertEquals(new Point(0,0), shipLocation.get(1));
        
        pb.setStartPoint(new Point(0,0), Direction.DOWN);
        
        shipLocation = pb.getShipLocation();
        assertEquals(new Point(0,0), shipLocation.get(0));
        assertEquals(new Point(1,0), shipLocation.get(1));
        
        pb.setStartPoint(new Point(0,1), Direction.LEFT);
        
        shipLocation = pb.getShipLocation();
        assertEquals(new Point(0,1), shipLocation.get(0));
        assertEquals(new Point(0,0), shipLocation.get(1));
        
        pb.setStartPoint(new Point(0,0), Direction.RIGHT);
        
        shipLocation = pb.getShipLocation();
        assertEquals(new Point(0,0), shipLocation.get(0));
        assertEquals(new Point(0,1), shipLocation.get(1));
    }
    
    @Test
    public void testShipType() {
        assertEquals(ShipType.PATROLBOAT, pb.getShipType());
    }
    
    @Test
    public void testIsSubmerged() {
        assertFalse(pb.isSubmerged());
    }
    
    @Test
    public void testValidShot() {
        pb.setStartPoint(new Point(0,0), Direction.DOWN);

        assertTrue(pb.isValidShot(new Point(0,1)));
        assertTrue(pb.isValidShot(new Point(3,0)));
        assertTrue(pb.isValidShot(new Point(1,2)));
        assertTrue(pb.isValidShot(new Point(3,2)));

        assertFalse(pb.isValidShot(new Point(4,0)));
        assertFalse(pb.isValidShot(new Point(3,3)));
    }

    @Test
    public void testDoIHaveRightOfWay() {
        assertTrue(pb.doIHaveRightOfWay(ShipType.CARRIER));
        assertTrue(pb.doIHaveRightOfWay(ShipType.BATTLESHIP));
        assertTrue(pb.doIHaveRightOfWay(ShipType.DESTROYER));
        assertFalse(pb.doIHaveRightOfWay(ShipType.SUBMARINE));
    }
}
