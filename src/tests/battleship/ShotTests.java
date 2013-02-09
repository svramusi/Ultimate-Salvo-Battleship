package tests.battleship;

import static org.junit.Assert.*;

import org.junit.Test;

import ships.Point;
import ships.Ship.ShipType;
import battleship.Shot;

public class ShotTests {

	@Test
	public void testShot() {
		Point p = new Point(0,0);
		Shot s = new Shot(p, ShipType.CARRIER);
		
		assertEquals(p, s.getPoint());
		assertEquals(ShipType.CARRIER, s.getShipType());
	}
}
