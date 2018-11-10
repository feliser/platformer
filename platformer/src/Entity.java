import java.awt.Graphics;

public class Entity 
{
	public int x, y, nX, nY, pX, pY, width, height;
	public float xV, yV;
	public boolean onGround = false, pushesRight = false, pushesLeft = false, ceiling = false, isPlayer = false, lwallJump = false, rwallJump = false, canWallJump = false;
	public AABB left, right, top, bottom;
	
	public Entity(int _x, int _y, int _width, int _height)
	{
		x = _x;
		y = _y;
		nX = x;
		nY = y;
		width = _width;
		height = _height;
		
		left = new AABB(_x-2, _y, 2, _height);
		right = new AABB(_x+_width, _y, 2, _height);
		top = new AABB(_x-2, _y, _width, 2);
		bottom = new AABB(_x, _y + _height, _width, 2);
	
		CollisionManager.EntityList.add(this); 
	}
	
	public void draw(Graphics g)
	{
//		top.draw(g);
//		bottom.draw(g);
//		left.draw(g);
//		right.draw(g);
//		
		g.drawRect(x + -Main.xScroll + Main.xOffset, y + -Main.yScroll + Main.yOffset, width, height);	
	}
	
	public void update()
	{
		left = new AABB(x - 2, y, 2, height);
		right = new AABB(x + width, y, 2, height);
		top = new AABB(x, y-2, width, 2);
		bottom = new AABB(x, y + height, width, 2);
	}
}