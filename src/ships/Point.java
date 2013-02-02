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
