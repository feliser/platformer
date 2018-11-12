import java.awt.Graphics;

public class Player extends Entity
{
	public final int MAX_SPEED = 10;
	public final int MAX_SPEED_AIR = 12;
	
	public final float MAX_ACCELERATION = 2.5f;
	public float acceleration = (float) 0.3;
	public int rJump, lJump, rX, rY, groundGenerosity;
	public boolean wallJump = false, bypass = false;

	public Player(int _x, int _y, int _width, int _height) 
	{
		super(_x, _y, _width, _height);
		super.isPlayer = true;
		rX = _x;
		rY = _y;
	}
	
	public void keyCheck()
	{
		if(onGround)
		{
			wallJump = false;
		}
		
		if(ceiling)
		{
			yV = 3;
		}
		
		if(Main.right && !pushesRight && !Main.left)
		{
			if (xV < MAX_SPEED && onGround)
			{
				if(xV < MAX_SPEED)
				{
					xV += acceleration;
					if(acceleration < MAX_ACCELERATION)
					{
						acceleration += 0.3;
					}
				}	
			}
			else
			{
				if(xV < MAX_SPEED_AIR && !onGround)
				{
					xV += acceleration / 3;
					if(acceleration < MAX_ACCELERATION)
					{
						acceleration += 0.3;
					}
				}
			}
		}
		else
		{
			if(xV > 0)
			{
				
				if(onGround)
				{
					if(xV > 1)
					{
						xV -= 1;
					}
					else
					{
						xV = 0;
					}
				}
				else
				{
					if(!Main.right)
					{
						xV -= 0.2;
					}
				}
			}
		}
		if(Main.left && !pushesLeft && !Main.right)
		{
			if (xV > -MAX_SPEED && onGround)
			{
				if(xV > -MAX_SPEED)
				{
					xV -= acceleration;
					if(acceleration < MAX_ACCELERATION)
					{
						acceleration += 0.3;
					}
				}	
			}
			else
			{
				if(xV > -MAX_SPEED_AIR && !onGround)
				{
					xV -= acceleration / 3;
					if(acceleration < MAX_ACCELERATION)
					{
						acceleration += 0.3;
					}
				}
			}
		}
		else
		{
			if(xV < 0)
			{
				if(onGround)
				{
					if(xV < 1)
					{
						xV += 1;
					}
					else
					{
						xV = 0;
					}
				}
				else
				{
					if(!Main.left)
					{
						xV += 0.2;
					}
				}
			}
		}
		if(onGround)
		{
			if(xV > MAX_SPEED)
			{
				xV = MAX_SPEED;
			}
			if(xV < -MAX_SPEED)
			{
				xV = -MAX_SPEED;
			}
		}
		
		if(rJump > 0)
		{
			rJump--;
			xV = -16;
		}
		if(lJump > 0)
		{
			lJump--;
			xV = 16;
		}
	
		if(onGround)
		{
			bypass = false;
		}
		
		if((xV >= -1 && xV <= 1) && !Main.right && !Main.left)
		{
			xV = 0;
		}
		
		if(pushesRight && Main.right)
		{
			if(yV > 2)
			{
				yV *= 0.8;
			}
		}
		if(pushesLeft && Main.left)
		{
			if(yV > 2)
			{
				yV *= 0.8;
			}
		}

		if(Main.space && Main.jump)
		{
			if(onGround || groundGenerosity > 0)
			{
				yV = -14;
				onGround = false;
				Main.jump = false;
			}
			else if(((pushesRight && !onGround) && rwallJump))
			{
				if(yV < 3)
				{
					yV = 3;
				}
				yV = -17;
				xV = -16;
				wallJump = true;
				rJump = 4;
				bypass = true;
				Main.jump = false;
			}
			else if(((pushesLeft && !onGround) && lwallJump))
			{
				if(yV > -3)
				{
					yV = -3;
				}
				yV = -17;
				xV = 16;
				wallJump = true;
				lJump = 4;
				bypass = true;
				Main.jump = false;
			}
		}
		
		//System.out.println(canWallJump);
		
		rwallJump = false;
		lwallJump = false;
		canWallJump = false;
		
		if(onGround)
		{
			groundGenerosity = 2;
		}
		else
		{
			if(groundGenerosity > 0)
			{
				groundGenerosity--;
			}
		}
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
//		g.setColor(Color.red);
//		g.drawRect(CollisionManager.rX + -Main.xScroll + Main.xOffset, CollisionManager.rY + -Main.yScroll + Main.yOffset, CollisionManager.rW, CollisionManager.rH);
//		g.setColor(Color.black);
//	
//		
//		g.drawRect(nX + (int) xV, nY + (int) yV, width, height);
	}
	
	public void reset()
	{
		xV = 0;
		yV = 0;
		x = rX;
		y = rY;
		
		acceleration = 0.3f;
		
		Main.right = false;
		Main.left = false;
		Main.space = false;
		
		Main.timerStarted = false;
		
		GameTimer.reset();
	}
}