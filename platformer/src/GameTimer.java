import java.awt.Graphics;

public class GameTimer 
{
	public static double time;
	
	public static void update()
	{
		time += 16.66;
	}
	
	public static void draw(Graphics g)
	{
		g.drawString(Double.toString(time), 10, 10);
	}
	
	public static void reset()
	{
		time = 0;
	}

}
