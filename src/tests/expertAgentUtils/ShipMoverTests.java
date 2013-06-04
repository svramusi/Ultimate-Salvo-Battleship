//package tests.expertAgentUtils;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import expertAgentUtils.ShipMover;
//
//import ships.Battleship;
//import ships.Carrier;
//import ships.Destroyer;
//import ships.Mover;
//import ships.PatrolBoat;
//import ships.Point;
//import ships.Submarine;
//import ships.Ship.Direction;
//import ships.Ship.ShipType;
//
//import battleshipExceptions.InvalidShipPositionException;
//import board.Board;
//
//
//public class ShipMoverTests {
//
//    private ShipMover shipMover;
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
//    public void setUp() throws Exception {
//        shipMover = new ShipMover();
//
//        board = new Board();
//        board.clearBoard();
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
//
//        shipMover.add(carrierMover);
//        shipMover.add(battleshipMover);
//        shipMover.add(destroyerMover);
//        shipMover.add(patrolboatMover);
//        shipMover.add(submarineMover);
//    }
//
//    @Test
//    public void testMovesSingleShip() {
//        submarine.setStartPoint(new Point(0,0), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        shipMover.setTargetDestination(ShipType.SUBMARINE, new Point(9,0), board);
//        shipMover.moveShips(board);
//
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(7,0)));
//        assertTrue(shipLocation.contains(new Point(6,0)));
//        assertTrue(shipLocation.contains(new Point(5,0)));
//    }
//
//    @Test
//    public void testMovesTwoShipsNoIntersection() {
//        submarine.setStartPoint(new Point(0,0), Direction.DOWN);
//        patrolboat.setStartPoint(new Point(0,9), Direction.DOWN);
//
//        try {
//            board.addShip(submarine);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        shipMover.setTargetDestination(ShipType.SUBMARINE, new Point(9,0), board);
//        shipMover.setTargetDestination(ShipType.PATROLBOAT, new Point(9,9), board);
//        shipMover.moveShips(board);
//
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(7,0)));
//        assertTrue(shipLocation.contains(new Point(6,0)));
//        assertTrue(shipLocation.contains(new Point(5,0)));
//
//        shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
//        assertTrue(shipLocation.contains(new Point(4,9)));
//        assertTrue(shipLocation.contains(new Point(5,9)));
//    }
//
//    @Test
//    public void testMovesTwoShipsPathsIntersect() {
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
//        shipMover.setTargetDestination(ShipType.SUBMARINE, new Point(5,8), board);
//        shipMover.setTargetDestination(ShipType.PATROLBOAT, new Point(9,3), board);
//        shipMover.moveShips(board);
//
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(5,7)));
//        assertTrue(shipLocation.contains(new Point(5,6)));
//        assertTrue(shipLocation.contains(new Point(5,5)));
//
//        shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
//        assertTrue(shipLocation.contains(new Point(6,3)));
//        assertTrue(shipLocation.contains(new Point(7,3)));
//    }
//
//    @Test
//    public void testMovesTwoShipsOneStopsInThePathOfTheOther() {
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
//        shipMover.setTargetDestination(ShipType.SUBMARINE, new Point(5,8), board);
//        shipMover.setTargetDestination(ShipType.PATROLBOAT, new Point(7,3), board);
//        shipMover.moveShips(board);
//
//        List<Point> shipLocation = board.getShipLocation(ShipType.SUBMARINE);
//        assertTrue(shipLocation.contains(new Point(5,7)));
//        assertTrue(shipLocation.contains(new Point(5,6)));
//        assertTrue(shipLocation.contains(new Point(5,5)));
//
//        shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
//        assertTrue(shipLocation.contains(new Point(5,3)));
//        assertTrue(shipLocation.contains(new Point(4,3)));
//    }
//
//    @Test
//    public void testMovesTwoShipsToSameDestination() {
//        destroyer.setStartPoint(new Point(5,2), Direction.LEFT);
//        patrolboat.setStartPoint(new Point(4,4), Direction.UP);
//
//        try {
//            board.addShip(destroyer);
//            board.addShip(patrolboat);
//        } catch (InvalidShipPositionException e) {
//            fail("caught InvalidShipPositionException when I shouldn't have");
//        }
//
//        shipMover.setTargetDestination(ShipType.DESTROYER, new Point(5,9), board);
//        shipMover.setTargetDestination(ShipType.PATROLBOAT, new Point(7,4), board);
//        shipMover.moveShips(board);
//
//        List<Point> shipLocation = board.getShipLocation(ShipType.DESTROYER);
//        assertTrue(shipLocation.contains(new Point(5,3)));
//        assertTrue(shipLocation.contains(new Point(5,2)));
//        assertTrue(shipLocation.contains(new Point(5,1)));
//
//        shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
//        assertTrue(shipLocation.contains(new Point(4,4)));
//        assertTrue(shipLocation.contains(new Point(5,4)));
//    }
//
////    @Test
////    //This error goes far beyond the scope of this project
////    //The problem is that the first move is to pivot to get around the ship
////    //Not move close and wait for it to move
////    public void testMovesThreeShipsToSameDestination() {
////        battleship.setStartPoint(new Point(4,6), Direction.RIGHT);
////        destroyer.setStartPoint(new Point(5,2), Direction.LEFT);
////        patrolboat.setStartPoint(new Point(4,4), Direction.UP);
////
////        try {
////            board.addShip(battleship);
////            board.addShip(destroyer);
////            board.addShip(patrolboat);
////        } catch (InvalidShipPositionException e) {
////            fail("caught InvalidShipPositionException when I shouldn't have");
////        }
////
////        shipMover.setTargetDestination(ShipType.BATTLESHIP, new Point(4,0), board);
////        shipMover.setTargetDestination(ShipType.DESTROYER, new Point(5,9), board);
////        shipMover.setTargetDestination(ShipType.PATROLBOAT, new Point(7,4), board);
////        shipMover.moveShips(board);
////
////        List<Point> shipLocation = board.getShipLocation(ShipType.DESTROYER);
////        assertTrue(shipLocation.contains(new Point(5,3)));
////        assertTrue(shipLocation.contains(new Point(5,2)));
////        assertTrue(shipLocation.contains(new Point(5,1)));
////
////        List<Point> battleShipLocation = board.getShipLocation(ShipType.BATTLESHIP);
////        for (Point p : battleShipLocation)
////            System.out.println(p);
////        assertTrue(battleShipLocation.contains(new Point(4,5)));
////        assertTrue(battleShipLocation.contains(new Point(4,6)));
////        assertTrue(battleShipLocation.contains(new Point(4,7)));
////        assertTrue(battleShipLocation.contains(new Point(4,8)));
////
////        shipLocation = board.getShipLocation(ShipType.PATROLBOAT);
////        assertTrue(shipLocation.contains(new Point(4,4)));
////        assertTrue(shipLocation.contains(new Point(5,4)));
////    }
//
//    @Test
//    public void testGetDistance() {
//        assertEquals(1, shipMover.getDistance(new Point(0,0), new Point(0,1)));
//        assertEquals(2, shipMover.getDistance(new Point(0,0), new Point(0,2)));
//        assertEquals(1, shipMover.getDistance(new Point(0,0), new Point(1,0)));
//        assertEquals(2, shipMover.getDistance(new Point(0,0), new Point(2,0)));
//        assertEquals(4, shipMover.getDistance(new Point(0,0), new Point(2,2)));
//
//        assertEquals(1, shipMover.getDistance(new Point(1,0), new Point(1,1)));
//        assertEquals(2, shipMover.getDistance(new Point(1,0), new Point(1,2)));
//        assertEquals(1, shipMover.getDistance(new Point(1,0), new Point(1,1)));
//        assertEquals(2, shipMover.getDistance(new Point(1,0), new Point(0,1)));
//    }
//
//    @Test
//    public void testGetMinDistance() {
//        List<Point> points = new ArrayList<Point>();
//        points.add(new Point(0,0));
//        points.add(new Point(0,1));
//        points.add(new Point(0,2));
//
//        assertEquals(0, shipMover.getMinDistance(points, new Point(0,0)));
//        assertEquals(1, shipMover.getMinDistance(points, new Point(1,0)));
//        assertEquals(2, shipMover.getMinDistance(points, new Point(2,0)));
//        assertEquals(1, shipMover.getMinDistance(points, new Point(0,3)));
//    }
//}
