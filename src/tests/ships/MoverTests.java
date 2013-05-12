package tests.ships;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import battleshipExceptions.InvalidShipPositionException;
import board.Board;
import expertAgentUtils.ShipMover;

import ships.Carrier;
import ships.Battleship;
import ships.Destroyer;
import ships.PatrolBoat;
import ships.Ship.Direction;
import ships.Ship.ShipType;
import ships.Submarine;
import ships.Mover;
import ships.Point;

public class MoverTests {

    private Board board;
    
    private Mover carrierMover;
    private Mover battleshipMover;
    private Mover destroyerMover;
    private Mover patrolboatMover;
    private Mover submarineMover;
    
    private Carrier carrier;
    private Battleship battleship;
    private Destroyer destroyer;
    private PatrolBoat patrolboat;
    private Submarine submarine;
    
    @Before
    public void setUp() {
        board = new Board();

        carrier = new Carrier();
        battleship = new Battleship();
        destroyer = new Destroyer();
        patrolboat = new PatrolBoat();
        submarine = new Submarine();

        carrierMover = new Mover(carrier);
        battleshipMover = new Mover(battleship);
        destroyerMover = new Mover(destroyer);
        patrolboatMover = new Mover(patrolboat);
        submarineMover = new Mover(submarine);

        carrierMover.register(battleshipMover);
        carrierMover.register(destroyerMover);
        carrierMover.register(patrolboatMover);
        carrierMover.register(submarineMover);

        battleshipMover.register(carrierMover);
        battleshipMover.register(destroyerMover);
        battleshipMover.register(patrolboatMover);
        battleshipMover.register(submarineMover);

        destroyerMover.register(carrierMover);
        destroyerMover.register(battleshipMover);
        destroyerMover.register(patrolboatMover);
        destroyerMover.register(submarineMover);

        patrolboatMover.register(carrierMover);
        patrolboatMover.register(battleshipMover);
        patrolboatMover.register(destroyerMover);
        patrolboatMover.register(submarineMover);

        submarineMover.register(carrierMover);
        submarineMover.register(battleshipMover);
        submarineMover.register(destroyerMover);
        submarineMover.register(patrolboatMover);
    }

    @Test
    public void testMoverTakesTargetPosition() {
        Point target = new Point(0,0);
        carrierMover.setTarget(target);
        
        assertEquals(target, carrierMover.getTarget());
    }

    @Test
    public void testGetDesiredLocation() {
        submarine.setStartPoint(new Point(3,0), Direction.DOWN);

        try {
            board.addShip(submarine);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        List<Point> shipLocation;
        shipLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(shipLocation.contains(new Point(3,0)));

        submarineMover.calculateDesiredLocation(new Point(0,1), board);

        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(actualLocation.contains(new Point(3,0)));
        assertTrue(actualLocation.contains(new Point(4,0)));
        assertTrue(actualLocation.contains(new Point(5,0)));

        List<Point> desiredLocation = submarineMover.getDesiredLocation();
        assertFalse(desiredLocation.contains(new Point(3,0)));
        
        assertTrue(desiredLocation.contains(new Point(0,0)));
        assertTrue(desiredLocation.contains(new Point(1,0)));
        assertTrue(desiredLocation.contains(new Point(2,0)));
    }

    @Test
    public void testGetDesiredLocationNoMove() {
        submarine.setStartPoint(new Point(3,0), Direction.DOWN);

        try {
            board.addShip(submarine);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }

        assertTrue(board.getShipLocation(ShipType.SUBMARINE).contains(new Point(3,0)));

        submarineMover.calculateDesiredLocation(new Point(3,1), board);
        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);

        assertTrue(actualLocation.contains(new Point(3,0)));
        assertTrue(actualLocation.contains(new Point(4,0)));
        assertTrue(actualLocation.contains(new Point(5,0)));

        List<Point> desiredLocation = submarineMover.getDesiredLocation();
        assertTrue(desiredLocation.contains(new Point(3,0)));
        assertTrue(desiredLocation.contains(new Point(4,0)));
        assertTrue(desiredLocation.contains(new Point(5,0)));
    }

    @Test
    public void testGetSpacesCoveredVertical() {
        submarine.setStartPoint(new Point(6,0), Direction.DOWN);

        try {
            board.addShip(submarine);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(shipLocation.contains(new Point(6,0)));

        submarineMover.calculateDesiredLocation(new Point(0,1), board);

        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(actualLocation.contains(new Point(6,0)));
        assertTrue(actualLocation.contains(new Point(7,0)));
        assertTrue(actualLocation.contains(new Point(8,0)));

        List<Point> desiredLocation = submarineMover.getDesiredLocation();
        assertTrue(desiredLocation.contains(new Point(1,0)));
        assertTrue(desiredLocation.contains(new Point(2,0)));
        assertTrue(desiredLocation.contains(new Point(3,0)));

        List<Point> spacesCovered = submarineMover.getSpacesCovered();
        assertTrue(spacesCovered.contains(new Point(4,0)));
        assertTrue(spacesCovered.contains(new Point(5,0)));
    }

    @Test
    public void testGetSpacesCoveredHorizontal() {
        submarine.setStartPoint(new Point(0,0), Direction.RIGHT);

        try {
            board.addShip(submarine);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(shipLocation.contains(new Point(0,2)));

        submarineMover.calculateDesiredLocation(new Point(0,8), board);

        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(actualLocation.contains(new Point(0,0)));
        assertTrue(actualLocation.contains(new Point(0,1)));
        assertTrue(actualLocation.contains(new Point(0,2)));

        List<Point> desiredLocation = submarineMover.getDesiredLocation();
        assertTrue(desiredLocation.contains(new Point(0,7)));
        assertTrue(desiredLocation.contains(new Point(0,6)));
        assertTrue(desiredLocation.contains(new Point(0,5)));

        List<Point> spacesCovered = submarineMover.getSpacesCovered();
        assertTrue(spacesCovered.contains(new Point(0,3)));
        assertTrue(spacesCovered.contains(new Point(0,4)));
    }

    @Test
    public void testGetSpacesCoveredNoMove() {
        submarine.setStartPoint(new Point(0,0), Direction.RIGHT);

        try {
            board.addShip(submarine);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(shipLocation.contains(new Point(0,2)));

        submarineMover.calculateDesiredLocation(new Point(0,3), board);

        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
        assertTrue(actualLocation.contains(new Point(0,0)));
        assertTrue(actualLocation.contains(new Point(0,1)));
        assertTrue(actualLocation.contains(new Point(0,2)));

        List<Point> desiredLocation = submarineMover.getDesiredLocation();
        assertTrue(desiredLocation.contains(new Point(0,0)));
        assertTrue(desiredLocation.contains(new Point(0,1)));
        assertTrue(desiredLocation.contains(new Point(0,2)));

        List<Point> spacesCovered = submarineMover.getSpacesCovered();
        assertEquals(0, spacesCovered.size());
    }

    @Test
    public void testGetShipType() {
        assertEquals(ShipType.CARRIER, carrierMover.getShipType());
    }

    @Test
    public void testObserverPattern() {
        Point target = new Point(0,0);
        carrierMover.setTarget(target);
        
        assertEquals(target, carrierMover.getTarget());
    }
}
