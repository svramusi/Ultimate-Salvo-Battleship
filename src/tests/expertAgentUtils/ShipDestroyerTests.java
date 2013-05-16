package tests.expertAgentUtils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import expertAgentUtils.ShipDestroyer;

import ships.Battleship;
import ships.Carrier;
import ships.Destroyer;
import ships.PatrolBoat;
import ships.Point;
import ships.Submarine;
import ships.Ship.Direction;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;

public class ShipDestroyerTests {

    private Board board;
    private ShipDestroyer shipDestroyer;
    
    @Before
    public void setUp() throws Exception {
        board = new Board();
        board.clearBoard();
        
        Carrier c = new Carrier();
        c.setStartPoint(new Point(0,0), Direction.DOWN);
        
        Battleship b = new Battleship();
        b.setStartPoint(new Point(0,9), Direction.DOWN);
        
        Destroyer d = new Destroyer();
        d.setStartPoint(new Point(9,0), Direction.UP);
        
        Submarine s = new Submarine();
        s.setStartPoint(new Point(9,9), Direction.UP);
        
        PatrolBoat pb = new PatrolBoat();
        pb.setStartPoint(new Point(5,5), Direction.LEFT);

        try {
            board.addShip(c);
            board.addShip(b);
            board.addShip(d);
            board.addShip(pb);
            board.addShip(s);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        shipDestroyer = new ShipDestroyer(board);
    }

    @Test
    public void testNotHotOnTrail() {
        assertFalse(shipDestroyer.hotOnTrail());
    }

    @Test
    public void testHotOnTrail() {
        assertFalse(shipDestroyer.hotOnTrail());
        shipDestroyer.hit(new Point(0,0));
        assertTrue(shipDestroyer.hotOnTrail());
    }

    @Test
    public void testNotHotOnTrailAfterReset() {
        assertFalse(shipDestroyer.hotOnTrail());
        shipDestroyer.hit(new Point(0,0));
        assertTrue(shipDestroyer.hotOnTrail());
        shipDestroyer.reset();
        assertFalse(shipDestroyer.hotOnTrail());
    }

    @Test
    public void testGetRandomNextShotCorner() {
        shipDestroyer.hit(new Point(0,0));
        
        Point nextShot = shipDestroyer.getNextShot();
        assertTrue(nextShot.equals(new Point(0,1)) || nextShot.equals(new Point(1,0)));
    }

    @Test
    public void testGetRandomNextShotCenter() {
        shipDestroyer.hit(new Point(5,5));
        
        Point nextShot = shipDestroyer.getNextShot();
        assertTrue(nextShot.equals(new Point(5,4)) || nextShot.equals(new Point(4,5)) || nextShot.equals(new Point(5,6)) || nextShot.equals(new Point(6,5)));
    }

    @Test
    public void testGetShotYInc() {
        shipDestroyer.hit(new Point(5,5));
        shipDestroyer.hit(new Point(5,6));
        
        assertEquals(new Point(5,7), shipDestroyer.getNextShot());
    }

    @Test
    public void testGetShotYDec() {
        shipDestroyer.hit(new Point(5,6));
        shipDestroyer.hit(new Point(5,5));
        
        assertEquals(new Point(5,4), shipDestroyer.getNextShot());
    }

    @Test
    public void testGetShotXInc() {
        shipDestroyer.hit(new Point(5,5));
        shipDestroyer.hit(new Point(6,5));
        
        assertEquals(new Point(7,5), shipDestroyer.getNextShot());
    }

    @Test
    public void testGetShotXDec() {
        shipDestroyer.hit(new Point(5,5));
        shipDestroyer.hit(new Point(4,5));
        
        assertEquals(new Point(3,5), shipDestroyer.getNextShot());
    }

    @Test
    public void testSwitchSides() {
        shipDestroyer.hit(new Point(5,5));
        shipDestroyer.hit(new Point(5,6));
        shipDestroyer.miss(new Point(5,7));
        
        assertEquals(new Point(5,4), shipDestroyer.getNextShot());
    }

    @Test
    public void testSwitchSidesAnKeepGoing() {
        shipDestroyer.hit(new Point(5,5));
        shipDestroyer.hit(new Point(5,6));
        shipDestroyer.miss(new Point(5,7));
        
        assertEquals(new Point(5,4), shipDestroyer.getNextShot());

        shipDestroyer.hit(new Point(5,4));
        assertEquals(new Point(5,3), shipDestroyer.getNextShot());
        
        shipDestroyer.hit(new Point(5,3));
        assertEquals(new Point(5,2), shipDestroyer.getNextShot());
    }

}
