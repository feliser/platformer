import java.awt.Graphics;

public class Entity 
{
	public int x, y, nX, nY, pX, pY, width, height;
	public float xV, yV;
	public boolean onGround = false, pushesRight = false, pushesLeft = false, ceiling = false, isPlayer = false, lwallJump = false, rwallJump = false, canWallJump = false;
	public AABB left, right, top, bottom, collisionBox, leftWallJumpBox, rightWallJumpBox, fullBox;
	
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
		
		leftWallJumpBox = new AABB(_x - 6, y, 6, _height);
		rightWallJumpBox = new AABB(_x + width, _y, 6, _height);
		
		collisionBox = new AABB(_x, _y, _width, _height);
		fullBox = new AABB(_x, _y, _width, _height);
	
		CollisionManager.EntityList.add(this); 
	}
	
	public void draw(Graphics g)
	{
//		top.draw(g);
//		bottom.draw(g);
//		left.draw(g);
//		right.draw(g);
//		
		g.drawRect((int) ((x + -Main.xScroll + Main.xOffset) * Main.zoom), (int) ((y + -Main.yScroll + Main.yOffset) * Main.zoom), (int) (width * Main.zoom), (int) (height * Main.zoom));	
	}
	
	public void update()
	{
		left.x = x - 2;
		left.y = y;
		
		right.x = x + width;
		right.y = y;
		
		top.x = x;
		top.y = y - 2;
		
		bottom.x = x;
		bottom.y = y + height;
		
		leftWallJumpBox.x = x - 6;
		leftWallJumpBox.y = y;
		
		rightWallJumpBox.x = x + width;
		rightWallJumpBox.y = y;
		
		fullBox.x = x;
		fullBox.y = y;
	}
}