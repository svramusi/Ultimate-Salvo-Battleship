package ships;

public class Point {

	private int x;
	private int y;
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public int getDistanceFrom(Point otherPoint)
	{
		int otherX = otherPoint.getX();
		int otherY = otherPoint.getY();
		
		int deltaX = Math.abs(this.x - otherX);
		int deltaY = Math.abs(this.y - otherY);

		return deltaX + deltaY;
	}
	
	@Override
	public String toString()
	{
		return x + "," + y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!o.getClass().equals(this.getClass()))
			return false;
		else
		{
			Point newPoint = (Point)o;
			if(this.x == newPoint.getX() && this.y == newPoint.getY())
				return true;
			else
				return false;
		}
	}
}
