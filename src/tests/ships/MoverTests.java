//package tests.ships;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import battleshipExceptions.InvalidShipPositionException;
//import board.Board;
//
//import ships.Carrier;
//import ships.Battleship;
//import ships.Destroyer;
//import ships.PatrolBoat;
//import ships.Ship.Direction;
//import ships.Ship.ShipType;
//import ships.Submarine;
//import ships.Mover;
//import ships.Point;
//
//public class MoverTests {
//
//    private Board board;
//    
//    private Mover carrierMover;
//    private Mover battleshipMover;
//    private Mover destroyerMover;
//    private Mover patrolboatMover;
//    private Mover submarineMover;
//    
//    private Carrier carrier;
//    private Battleship battleship;
//    private Destroyer destroyer;
//    private PatrolBoat patrolboat;
//    private Submarine submarine;
//    
//    @Before
//    public void setUp() {
//        board = new Board();
//
//        carrier = new Carrier();
//        battleship = new Battleship();
//        destroyer = new Destroyer();
//        patrolboat = new PatrolBoat();
//        submarine = new Submarine();
//
//        carrierMover = new Mover(carrier);
//        battleshipMover = new Mover(battleship);
//        destroyerMover = new Mover(destroyer);
//        patrolboatMover = new Mover(patrolboat);
//        submarineMover = new Mover(submarine);
//
//        carrierMover.register(battleshipMover);
//        carrierMover.register(destroyerMover);
//        carrierMover.register(patrolboatMover);
//        carrierMover.register(submarineMover);
//
//        battleshipMover.register(carrierMover);
//        battleshipMover.register(destroyerMover);
//        battleshipMover.register(patrolboatMover);
//        battleshipMover.register(submarineMover);
//
//        destroyerMover.register(carrierMover);
//        destroyerMover.register(battleshipMover);
//        destroyerMover.register(patrolboatMover);
//        destroyerMover.register(submarineMover);
//
//        patrolboatMover.register(carrierMover);
//        patrolboatMover.register(battleshipMover);
//        patrolboatMover.register(destroyerMover);
//        patrolboatMover.register(submarineMover);
//
//        submarineMover.register(carrierMover);
//        submarineMover.register(battleshipMover);
//        submarineMover.register(destroyerMover);
//        submarineMover.register(patrolboatMover);
//    }
//
//    @Test
//    public void testGetDesiredLocation() {
//        submarine.setStartPoint(new Point(3,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//        
//        List<Point> shipLocation;
//        shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(3,0)));
//
//        submarineMover.calculateDesiredLocation(new Point(0,1), board);
//
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(actualLocation.contains(new Point(3,0)));
//        assertTrue(actualLocation.contains(new Point(4,0)));
//        assertTrue(actualLocation.contains(new Point(5,0)));
//
//        List<Point> desiredLocation = submarineMover.getDesiredLocation();
//        assertFalse(desiredLocation.contains(new Point(3,0)));
//        
//        assertTrue(desiredLocation.contains(new Point(0,0)));
//        assertTrue(desiredLocation.contains(new Point(1,0)));
//        assertTrue(desiredLocation.contains(new Point(2,0)));
//    }
//
//    @Test
//    public void testGetDesiredLocationNoMove() {
//        submarine.setStartPoint(new Point(3,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        assertTrue(board.getShipLocation(ShipType.SUBMARINE).contains(new Point(3,0)));
//
//        submarineMover.calculateDesiredLocation(new Point(3,1), board);
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//
//        assertTrue(actualLocation.contains(new Point(3,0)));
//        assertTrue(actualLocation.contains(new Point(4,0)));
//        assertTrue(actualLocation.contains(new Point(5,0)));
//
//        List<Point> desiredLocation = submarineMover.getDesiredLocation();
//        assertTrue(desiredLocation.contains(new Point(3,0)));
//        assertTrue(desiredLocation.contains(new Point(4,0)));
//        assertTrue(desiredLocation.contains(new Point(5,0)));
//    }
//
//    @Test
//    public void testGetDesiredPathVertical() {
//        submarine.setStartPoint(new Point(6,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//        
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(6,0)));
//
//        submarineMover.calculateDesiredLocation(new Point(0,1), board);
//
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(actualLocation.contains(new Point(6,0)));
//        assertTrue(actualLocation.contains(new Point(7,0)));
//        assertTrue(actualLocation.contains(new Point(8,0)));
//
//        List<Point> desiredLocation = submarineMover.getDesiredLocation();
//        assertTrue(desiredLocation.contains(new Point(1,0)));
//        assertTrue(desiredLocation.contains(new Point(2,0)));
//        assertTrue(desiredLocation.contains(new Point(3,0)));
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertTrue(desiredPath.contains(new Point(4,0)));
//        assertTrue(desiredPath.contains(new Point(5,0)));
//    }
//
//    @Test
//    public void testGetDesiredPathHorizontal() {
//        submarine.setStartPoint(new Point(0,0), Direction.RIGHT);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//        
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(0,2)));
//
//        submarineMover.calculateDesiredLocation(new Point(0,8), board);
//
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(actualLocation.contains(new Point(0,0)));
//        assertTrue(actualLocation.contains(new Point(0,1)));
//        assertTrue(actualLocation.contains(new Point(0,2)));
//
//        List<Point> desiredLocation = submarineMover.getDesiredLocation();
//        assertTrue(desiredLocation.contains(new Point(0,7)));
//        assertTrue(desiredLocation.contains(new Point(0,6)));
//        assertTrue(desiredLocation.contains(new Point(0,5)));
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertTrue(desiredPath.contains(new Point(0,3)));
//        assertTrue(desiredPath.contains(new Point(0,4)));
//    }
//
//    @Test
//    public void testGetDesiredPathNoMove() {
//        submarine.setStartPoint(new Point(0,0), Direction.RIGHT);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//        
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(0,2)));
//
//        submarineMover.calculateDesiredLocation(new Point(0,3), board);
//
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(actualLocation.contains(new Point(0,0)));
//        assertTrue(actualLocation.contains(new Point(0,1)));
//        assertTrue(actualLocation.contains(new Point(0,2)));
//
//        List<Point> desiredLocation = submarineMover.getDesiredLocation();
//        assertTrue(desiredLocation.contains(new Point(0,0)));
//        assertTrue(desiredLocation.contains(new Point(0,1)));
//        assertTrue(desiredLocation.contains(new Point(0,2)));
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertEquals(0, desiredPath.size());
//    }
//
//    @Test
//    public void testGetShipType() {
//        assertEquals(ShipType.CARRIER, carrierMover.getShipType());
//    }
//
//    @Test
//    public void testObserverPattern() {
//        assertEquals(4, carrierMover.getNumberOfObservers());
//        
//        carrierMover.unregister(submarineMover);
//        assertEquals(3, carrierMover.getNumberOfObservers());
//    }
//
//    @Test
//    public void testIsPathIntersections() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,8), board);
//        patrolboatMover.calculateDesiredLocation(new Point(9,3), board);
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertTrue(desiredPath.contains(new Point(5,3)));
//        assertTrue(desiredPath.contains(new Point(5,4)));
//
//        desiredPath = patrolboatMover.getDesiredPath();
//        assertTrue(desiredPath.contains(new Point(5,3)));
//        
//        assertTrue(submarineMover.isPathIntersection());
//        assertTrue(patrolboatMover.isPathIntersection());
//    }
//
//    @Test
//    public void testIsPathIntersectionWithDestination() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,8), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,3), board);
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertTrue(desiredPath.contains(new Point(5,3)));
//        assertTrue(desiredPath.contains(new Point(5,4)));
//
//        desiredPath = patrolboatMover.getDesiredPath();
//        assertEquals(0, desiredPath.size());
//        
//        assertTrue(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//    }
//
//    @Test
//    public void testIsDesiredLocationIntersection() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,4), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,3), board);
//
//        List<Point> desiredPath = submarineMover.getDesiredPath();
//        assertEquals(0, desiredPath.size());
//
//        desiredPath = patrolboatMover.getDesiredPath();
//        assertEquals(0, desiredPath.size());
//        
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(submarineMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//    }
//
//    @Test
//    public void testShouldDelayMoveWithPathIntersection() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,8), board);
//        patrolboatMover.calculateDesiredLocation(new Point(9,3), board);
//
//        assertTrue(submarineMover.isPathIntersection());
//        assertTrue(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.shouldDelayMove());
//        assertTrue(patrolboatMover.shouldDelayMove());
//    }
//
//    @Test
//    public void testShouldDelayMoveNoPathIntersection() {
//        submarine.setStartPoint(new Point(9,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(0,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(9,9), board);
//        patrolboatMover.calculateDesiredLocation(new Point(5,0), board);
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.shouldDelayMove());
//        assertFalse(patrolboatMover.shouldDelayMove());
//    }
//
//    @Test
//    public void testShouldICalculateNewPositionWithDestIntersection() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,4), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,3), board);
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(submarineMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertFalse(submarineMover.shouldCalcNewPosition());
//        assertTrue(patrolboatMover.shouldCalcNewPosition());
//    }
//
//    @Test
//    public void testShouldICalculateNewPositionNoDestIntersection() {
//        submarine.setStartPoint(new Point(9,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(0,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(9,9), board);
//        patrolboatMover.calculateDesiredLocation(new Point(5,0), board);
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.isDestinationIntersection());
//        assertFalse(patrolboatMover.isDestinationIntersection());
//
//        assertFalse(submarineMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//    }
//
//    @Test
//    public void testCanMoveShip() {
//        submarine.setStartPoint(new Point(9,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(0,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(9,9), board);
//        patrolboatMover.calculateDesiredLocation(new Point(5,0), board);
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.isDestinationIntersection());
//        assertFalse(patrolboatMover.isDestinationIntersection());
//
//        assertFalse(submarineMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//        
//        try {
//            submarineMover.move(board);
//            patrolboatMover.move(board);
//        } catch (Exception e) {
//            fail("caught exception when I shouldn't have");
//        }
//
//        List<Point> actualLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(actualLocation.contains(new Point(9,7)));
//        assertTrue(actualLocation.contains(new Point(9,6)));
//        assertTrue(actualLocation.contains(new Point(9,5)));
//
//        actualLocation = board.getShipLocation(ShipType.PATROLBOAT);
//        assertTrue(actualLocation.contains(new Point(3,0)));
//        assertTrue(actualLocation.contains(new Point(2,0)));
//    }
//
//    @Test
//    public void testShouldDelayThenMove() {
//        submarine.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(5,8), board);
//        patrolboatMover.calculateDesiredLocation(new Point(9,3), board);
//
//        assertTrue(submarineMover.isPathIntersection());
//        assertTrue(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.shouldDelayMove());
//        assertTrue(patrolboatMover.shouldDelayMove());
//        
//        try {
//            submarineMover.move(board);
//        } catch (Exception e) {
//            fail("caught exception when I shouldn't have");
//        }
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertFalse(submarineMover.shouldDelayMove());
//        assertFalse(patrolboatMover.shouldDelayMove());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromTheRightNoMove() {
//        destroyer.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,3), Direction.UP);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(5,9), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,3), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(5,2)));
//        assertTrue(newPosition.contains(new Point(5,1)));
//        assertTrue(newPosition.contains(new Point(5,0)));
//        
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromTheRightMove() {
//        destroyer.setStartPoint(new Point(5,0), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,4), Direction.UP);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(5,9), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,4), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(5,3)));
//        assertTrue(newPosition.contains(new Point(5,2)));
//        assertTrue(newPosition.contains(new Point(5,1)));
//        
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromTheLeftNoMove() {
//        destroyer.setStartPoint(new Point(5,7), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,6), Direction.UP);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(5,0), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,6), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(5,7)));
//        assertTrue(newPosition.contains(new Point(5,8)));
//        assertTrue(newPosition.contains(new Point(5,9)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromTheLeftMove() {
//        destroyer.setStartPoint(new Point(5,7), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(4,5), Direction.UP);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(5,0), board);
//        patrolboatMover.calculateDesiredLocation(new Point(7,5), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(5,6)));
//        assertTrue(newPosition.contains(new Point(5,7)));
//        assertTrue(newPosition.contains(new Point(5,8)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromDownNoMove() {
//        destroyer.setStartPoint(new Point(2,2), Direction.UP);
//        patrolboat.setStartPoint(new Point(3,1), Direction.LEFT);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(8,2), board);
//        patrolboatMover.calculateDesiredLocation(new Point(3,5), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(2,2)));
//        assertTrue(newPosition.contains(new Point(1,2)));
//        assertTrue(newPosition.contains(new Point(0,2)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testRecalculateDesiredLocationFromDownMove() {
//        destroyer.setStartPoint(new Point(2,2), Direction.UP);
//        patrolboat.setStartPoint(new Point(4,1), Direction.LEFT);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(8,2), board);
//        patrolboatMover.calculateDesiredLocation(new Point(4,5), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(3,2)));
//        assertTrue(newPosition.contains(new Point(2,2)));
//        assertTrue(newPosition.contains(new Point(1,2)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//        }
//    
//    @Test
//    public void testRecalculateDesiredLocationFromUpNoMove() {
//        destroyer.setStartPoint(new Point(7,2), Direction.DOWN);
//        patrolboat.setStartPoint(new Point(6,1), Direction.LEFT);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(1,2), board);
//        patrolboatMover.calculateDesiredLocation(new Point(6,5), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(7,2)));
//        assertTrue(newPosition.contains(new Point(8,2)));
//        assertTrue(newPosition.contains(new Point(9,2)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//    
//    @Test
//    public void testRecalculateDesiredLocationFromUpMove() {
//        destroyer.setStartPoint(new Point(7,2), Direction.DOWN);
//        patrolboat.setStartPoint(new Point(5,1), Direction.LEFT);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        destroyerMover.calculateDesiredLocation(new Point(1,2), board);
//        patrolboatMover.calculateDesiredLocation(new Point(5,5), board);
//
//        assertFalse(destroyerMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(destroyerMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertTrue(destroyerMover.shouldCalcNewPosition());
//        assertFalse(patrolboatMover.shouldCalcNewPosition());
//
//        destroyerMover.recalculateDesiredLocation();
//        List<Point> newPosition = destroyerMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(6,2)));
//        assertTrue(newPosition.contains(new Point(7,2)));
//        assertTrue(newPosition.contains(new Point(8,2)));
//
//        List<Point> newPath = destroyerMover.getDesiredPath();
//        assertEquals(0, newPath.size());
//    }
//
//    @Test
//    public void testNewPathIsCalculatedAfterPositionRecalc() {
//        submarine.setStartPoint(new Point(7,2), Direction.RIGHT);
//        patrolboat.setStartPoint(new Point(3,3), Direction.UP);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        submarineMover.calculateDesiredLocation(new Point(7,4), board);
//        patrolboatMover.calculateDesiredLocation(new Point(9,3), board);
//
//        assertFalse(submarineMover.isPathIntersection());
//        assertFalse(patrolboatMover.isPathIntersection());
//
//        assertTrue(submarineMover.isDestinationIntersection());
//        assertTrue(patrolboatMover.isDestinationIntersection());
//
//        assertFalse(submarineMover.shouldCalcNewPosition());
//        assertTrue(patrolboatMover.shouldCalcNewPosition());
//
//        patrolboatMover.recalculateDesiredLocation();
//        List<Point> newPosition = patrolboatMover.getDesiredLocation();
//        assertTrue(newPosition.contains(new Point(6,3)));
//        assertTrue(newPosition.contains(new Point(5,3)));
//
//        List<Point> newPath = patrolboatMover.getDesiredPath();
//        assertTrue(newPath.contains(new Point(4,3)));
//    }
//}
