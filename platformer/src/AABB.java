import java.awt.Graphics;

public class AABB 
{
	int x, y, width, height;
	
	public AABB(int _x, int _y, int _width, int _height)
	{
		x = _x;
		y = _y;
		width = _width;
		height = _height;
	}
	
	public static boolean isTouching(AABB man, AABB other)
	{
		if(man.x < other.x + other.width && man.x + man.width > other.x && man.y < other.y + other.height && man.y + man.height > other.y)
		{
			return true;
		}
		return false; 
	}
	
	public void draw(Graphics g)
	{
		g.drawRect(x, y, width, height);
	}

}