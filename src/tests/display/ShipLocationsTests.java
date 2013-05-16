package tests.display;

import static org.junit.Assert.*;

import org.junit.*;

import ships.Battleship;
import ships.Carrier;
import ships.Destroyer;
import ships.PatrolBoat;
import ships.Point;
import ships.Ship;
import ships.Submarine;

import battleshipExceptions.InvalidShipPositionException;
import board.Board;
import display.ShipLocations;

public class ShipLocationsTests {


    @Test
    public void testShipLocation() {
        Board board = new Board();
        board.clearBoard();
        
        Carrier c = new Carrier();
        c.setStartPoint(new Point(0,0), Ship.Direction.DOWN);
        
        Battleship b = new Battleship();
        b.setStartPoint(new Point(0,9), Ship.Direction.DOWN);
        
        Destroyer d = new Destroyer();
        d.setStartPoint(new Point(9,0), Ship.Direction.UP);
        
        Submarine s = new Submarine();
        s.setStartPoint(new Point(9,9), Ship.Direction.UP);
        
        PatrolBoat pb = new PatrolBoat();
        pb.setStartPoint(new Point(5,5), Ship.Direction.LEFT);

        try {
            board.addShip(c);
            board.addShip(b);
            board.addShip(d);
            board.addShip(pb);
            board.addShip(s);
        } catch (InvalidShipPositionException e) {
            fail("caught InvalidShipPositionException when I shouldn't have");
        }
        
        ShipLocations locations = new ShipLocations(board);

        assertEquals(Ship.ShipType.CARRIER, locations.getShip(0,0).getShipType());
        assertEquals(Ship.ShipType.SUBMARINE, locations.getShip(9,9).getShipType());
        assertNull(locations.getShip(2,2));
    }
}
