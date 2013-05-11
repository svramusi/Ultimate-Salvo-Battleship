package tests.ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ships.Carrier;
import ships.Battleship;
import ships.Destroyer;
import ships.PatrolBoat;
import ships.Submarine;
import ships.Mover;
import ships.Point;

public class MoverTests {

    private Mover carrierMover;
    private Mover battleshipMover;
    private Mover destroyerMover;
    private Mover patrolboatMover;
    private Mover submarineMover;
    
    private Carrier c;
    private Battleship b;
    private Destroyer d;
    private PatrolBoat pb;
    private Submarine s;
    
    @Before
    public void setUp() {
        c = new Carrier();
        b = new Battleship();
        d = new Destroyer();
        pb = new PatrolBoat();
        s = new Submarine();

        carrierMover = new Mover(c);
        battleshipMover = new Mover(b);
        destroyerMover = new Mover(d);
        patrolboatMover = new Mover(pb);
        submarineMover = new Mover(s);
    }

	@Test
	public void testMoverTakesTargetPosition() {
		Point target = new Point(0,0);
		carrierMover.setTarget(target);
		
		assertEquals(target, carrierMover.getTarget());
	}
}
