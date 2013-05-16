package tests.display;

import static org.junit.Assert.fail;

import org.junit.Test;

import ships.*;
import display.*;
import battleshipExceptions.InvalidShipPositionException;
import board.Board;

public class FileTests {

    @Test
    public void testFile() {
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
        
        Display display = new FileDisplay(board,"computer player", "output-file.txt");
        display.printBoard();
    }
}
