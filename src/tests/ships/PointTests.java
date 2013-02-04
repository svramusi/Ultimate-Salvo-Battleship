package tests.ships;

import static org.junit.Assert.*;

import org.junit.Test;

import ships.Point;


public class PointTests {

	@Test
	public void testPoint() {
		Point p = new Point(0,0);
		Point p1 = new Point(2,7);
		Point p2 = new Point(9,9);
		
		assertEquals(0, p.getX());
		assertEquals(0, p.getY());
		
		assertEquals(2, p1.getX());
		assertEquals(7, p1.getY());
		
		assertEquals(9, p2.getX());
		assertEquals(9, p2.getY());
	}
	
	@Test
	public void testEquals() {
		Point p = new Point(0,0);
		Point p1 = new Point(0,0);
		
		assertEquals(p, p1);
	}
	
	@Test
	public void testNotEquals() {
		Point p = new Point(0,0);
		Point p1 = new Point(0,1);
		
		assertFalse(p.equals(p1));
	}

	@Test
	public void testGetDistanceFrom() {
		Point p = new Point(0,0);
		Point p1 = new Point(0,1);
		assertEquals(1, p.getDistanceFrom(p1));

		p = new Point(0,0);
		p1 = new Point(1,0);
		assertEquals(1, p.getDistanceFrom(p1));

		assertEquals(0, p.getDistanceFrom(p));

		p1 = new Point(4,2);
		assertEquals(4, p.getDistanceFrom(p1));
	}
}
