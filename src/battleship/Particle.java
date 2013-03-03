package battleship;

public class Particle {
	private double x;
	private double y;
	private double weight;
	
	public Particle(double x, double y, double weight)
	{
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	public Double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public Double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}

}
