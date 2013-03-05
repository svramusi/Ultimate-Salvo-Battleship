package tests.battleship;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ships.Point;

import battleship.BattleshipUtils;

public class UtilsTests {


	@Test
	public void testGetMinXDistance() {
		List<Point> points = new ArrayList<Point>();

		points.add(new Point(0,0));
		
		Point target;
		
		target = new Point(0,0);
		assertEquals(0, BattleshipUtils.getXDistance(BattleshipUtils.getClosestXPoint(points, target), target));

		
		points.clear();
		points.add(new Point(1,0));
		points.add(new Point(2,0));
		points.add(new Point(3,0));

		target = new Point(0,0);
		assertEquals(1, BattleshipUtils.getXDistance(BattleshipUtils.getClosestXPoint(points, target), target));

		target = new Point(4,0);
		assertEquals(1, BattleshipUtils.getXDistance(BattleshipUtils.getClosestXPoint(points, target), target));

		target = new Point(9,0);
		assertEquals(6, BattleshipUtils.getXDistance(BattleshipUtils.getClosestXPoint(points, target), target));
	}

	@Test
	public void testGetMinYDistance() {
		List<Point> points = new ArrayList<Point>();

		points.add(new Point(0,0));
		
		Point target;

		target = new Point(0,0);
		assertEquals(0, BattleshipUtils.getYDistance(BattleshipUtils.getClosestYPoint(points, target), target));

		
		points.clear();
		points.add(new Point(0,1));
		points.add(new Point(0,2));
		points.add(new Point(0,3));

		target = new Point(0,0);
		assertEquals(1, BattleshipUtils.getYDistance(BattleshipUtils.getClosestYPoint(points, target), target));
		
		target = new Point(0,4);
		assertEquals(1, BattleshipUtils.getYDistance(BattleshipUtils.getClosestYPoint(points, target), target));
		
		target = new Point(0,9);
		assertEquals(6, BattleshipUtils.getYDistance(BattleshipUtils.getClosestYPoint(points, target), target));
	}

	@Test
	public void tesGetClosestXPoint() {
		List<Point> points = new ArrayList<Point>();

		points.add(new Point(0,0));
		points.add(new Point(1,0));
		points.add(new Point(2,0));
		points.add(new Point(3,0));

		assertEquals(new Point(0,0), BattleshipUtils.getClosestXPoint(points, new Point(0,0)));
		assertEquals(new Point(0,0), BattleshipUtils.getClosestXPoint(points, new Point(0,1)));
		assertEquals(new Point(3,0), BattleshipUtils.getClosestXPoint(points, new Point(3,1)));
		assertEquals(new Point(3,0), BattleshipUtils.getClosestXPoint(points, new Point(9,9)));
		assertEquals(new Point(3,0), BattleshipUtils.getClosestXPoint(points, new Point(9,0)));
		assertEquals(new Point(0,0), BattleshipUtils.getClosestXPoint(points, new Point(0,9)));
	}

	@Test
	public void tesGetClosestYPoint() {
		List<Point> points = new ArrayList<Point>();

		points.add(new Point(0,0));
		points.add(new Point(0,1));
		points.add(new Point(0,2));
		points.add(new Point(0,3));
		
		assertEquals(new Point(0,0), BattleshipUtils.getClosestYPoint(points, new Point(0,0)));
		assertEquals(new Point(0,0), BattleshipUtils.getClosestYPoint(points, new Point(1,0)));
		assertEquals(new Point(0,3), BattleshipUtils.getClosestYPoint(points, new Point(1,3)));
		assertEquals(new Point(0,3), BattleshipUtils.getClosestYPoint(points, new Point(9,9)));
		assertEquals(new Point(0,0), BattleshipUtils.getClosestYPoint(points, new Point(9,0)));
		assertEquals(new Point(0,3), BattleshipUtils.getClosestYPoint(points, new Point(0,9)));
	}
}
