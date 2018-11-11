import java.awt.Graphics;
import java.util.ArrayList;

public class CollisionManager 
{ 
	public static ArrayList<Entity> EntityList;
	public static ArrayList<AABB> PlatformList;
	public static ArrayList<Integer> PlatformDataList;
	public static ArrayList<AABB> newPlatformList;
	public static final int TILE_SIZE = 64;
	
	public static void init()
	{
		EntityList = new ArrayList<Entity>();
		PlatformList = new ArrayList<AABB>();
		PlatformDataList = new ArrayList<Integer>();
		newPlatformList = new ArrayList<AABB>();
	}
	
	public static void addPlatform(int x, int y, int type)
	{
		PlatformList.add(new AABB(x * TILE_SIZE, -(y * TILE_SIZE), TILE_SIZE, TILE_SIZE));
		PlatformDataList.add(type);
		
//		if(AABB.isTouching(new AABB(EntityList.get(i).x - 6, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
//		{
//			//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
//			if(tokens[2].equals("1"))
//			{
//				EntityList.get(i).lwallJump = true;
//			}
//		try 
//		{
//			Levels.addImage(filename, x, y);
//		} 
//		catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void update()
	{
		for(int i = 0; i < EntityList.size(); i++)
		{
			newPlatformList.clear();

			boolean xCancel = false, yCancel = false;
			
			if(!EntityList.get(i).onGround)
			{
				EntityList.get(i).yV += 0.5;
			}
			else
			{
				EntityList.get(i).yV = 0;
			}
			
			if(EntityList.get(i).ceiling)
			{
				EntityList.get(i).yV = 1;
			}
		    
			EntityList.get(i).nY = (int) (EntityList.get(i).y + EntityList.get(i).yV);
			EntityList.get(i).nX = (int) (EntityList.get(i).x + EntityList.get(i).xV);
			
			if(EntityList.get(i).xV > 0)
			{
				if(EntityList.get(i).yV > 0)
				{
					//AABB box = new AABB(EntityList.get(i).x - 2, EntityList.get(i).y - 2, Math.abs((EntityList.get(i).x) - (EntityList.get(i).nX + EntityList.get(i).width)) + 4, Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4);
					EntityList.get(i).collisionBox.x = EntityList.get(i).x - 2;
					EntityList.get(i).collisionBox.y = EntityList.get(i).y - 2;
					EntityList.get(i).collisionBox.width = Math.abs((EntityList.get(i).x) - (EntityList.get(i).nX + EntityList.get(i).width)) + 4;
					EntityList.get(i).collisionBox.height = Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4;

					for(int j = 0; j < PlatformList.size(); j++)
					{
						//Testing for walljump should probably be done at the end, dombo.
						if(AABB.isTouching(EntityList.get(i).leftWallJumpBox, PlatformList.get(j)))
						{
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).rightWallJumpBox, PlatformList.get(j)))
						{
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).collisionBox, PlatformList.get(j)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
				else
				{
					EntityList.get(i).collisionBox.x = EntityList.get(i).x - 2;
					EntityList.get(i).collisionBox.y = EntityList.get(i).nY - 2;
					EntityList.get(i).collisionBox.width = Math.abs((EntityList.get(i).x) - (EntityList.get(i).nX + EntityList.get(i).width)) + 4;
					EntityList.get(i).collisionBox.height = Math.abs((EntityList.get(i).nY) - (EntityList.get(i).y + EntityList.get(i).height)) + 4;

					for(int j = 0; j < PlatformList.size(); j++)
					{
						if(AABB.isTouching(EntityList.get(i).leftWallJumpBox, PlatformList.get(j)))
						{
							
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).rightWallJumpBox, PlatformList.get(j)))
						{
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).collisionBox, PlatformList.get(j)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
			}
			else
			{
				if(EntityList.get(i).yV > 0)
				{
					EntityList.get(i).collisionBox.x = EntityList.get(i).nX - 2;
					EntityList.get(i).collisionBox.y = EntityList.get(i).y - 2;
					EntityList.get(i).collisionBox.width = Math.abs((EntityList.get(i).nX) - (EntityList.get(i).x + EntityList.get(i).width)) + 4;
					EntityList.get(i).collisionBox.height = Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4;
					
					for(int j = 0; j < PlatformList.size(); j++)
					{
						if(AABB.isTouching(EntityList.get(i).leftWallJumpBox, PlatformList.get(j)))
						{
							
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).rightWallJumpBox, PlatformList.get(j)))
						{
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).collisionBox, PlatformList.get(j)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
				else
				{
					EntityList.get(i).collisionBox.x = EntityList.get(i).nX - 2;
					EntityList.get(i).collisionBox.y = EntityList.get(i).nY - 2;
					EntityList.get(i).collisionBox.width = Math.abs((EntityList.get(i).nX) - (EntityList.get(i).x + EntityList.get(i).width)) + 4;
					EntityList.get(i).collisionBox.height = Math.abs((EntityList.get(i).nY) - (EntityList.get(i).y + EntityList.get(i).height)) + 4;

					for(int j = 0; j < PlatformList.size(); j++)
					{
						if(AABB.isTouching(EntityList.get(i).leftWallJumpBox, PlatformList.get(j)))
						{
							
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).rightWallJumpBox, PlatformList.get(j)))
						{
							if(PlatformDataList.get(j) == 1)
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(EntityList.get(i).collisionBox, PlatformList.get(j)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
			}
			
			int oldX = EntityList.get(i).x;
			int oldY = EntityList.get(i).y;
			
			int yDif = EntityList.get(i).nY - EntityList.get(i).y;
			int xDif = EntityList.get(i).nX - EntityList.get(i).x;

			int xAdd = 0;
			int yAdd = 0;
			
			if(EntityList.get(i).pushesRight && EntityList.get(i).xV > 0)
			{
				EntityList.get(i).xV = 0;
			}
			
			if(EntityList.get(i).pushesLeft && EntityList.get(i).xV < 0)
			{
				EntityList.get(i).xV = 0;
			}
		
			EntityList.get(i).pushesLeft = false;
			EntityList.get(i).pushesRight = false;
			EntityList.get(i).onGround = false;
			EntityList.get(i).ceiling = false;
			
			if(Math.abs(EntityList.get(i).xV) > Math.abs(EntityList.get(i).yV))
			{
				for(int f = 0; f < Math.abs(xDif) + 1; f++)
				{
					if(f > 0)
					{
						if(EntityList.get(i).xV > 0)
						{
							xAdd++;
						}
						else
						{
							xAdd--;
						}
					}
					
					float alpha = Math.min((Math.round(((xAdd / EntityList.get(i).xV) * 1000)) / 1000.0f), 1);
					
					if(!xCancel)
					{
						EntityList.get(i).x = oldX + xAdd;
					}
					if(!yCancel)
					{
						EntityList.get(i).y = (int) (oldY + EntityList.get(i).yV * alpha);
					}
					
					if(EntityList.get(i).xV > 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).right, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
					}
					
					else if(EntityList.get(i).xV < 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).left, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesLeft = true;
							}
						}
					}
					else
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).right, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).left, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesLeft = true;
							}
						}
					}
					if(EntityList.get(i).yV > 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).bottom, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
					}
					else if(EntityList.get(i).yV < 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).top, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).ceiling = true;
							}
						}
					}
					else
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).bottom, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).top, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).ceiling = true;
							}
						}
					}
				}
			}
			
			else
			{
				for(int f = 0; f < Math.abs(yDif) + 1; f++)
				{
					if(f > 0)
					{
						if(EntityList.get(i).yV > 0)
						{
							yAdd++;
						}
						else
						{
							yAdd--;
						}
					}
					
					float alpha = Math.min((Math.round(((yAdd / EntityList.get(i).yV) * 1000)) / 1000.0f), 1);
					
					if(!xCancel)
					{
						EntityList.get(i).x = (int) (oldX + EntityList.get(i).xV * alpha);
					}
					if(!yCancel)
					{
						EntityList.get(i).y = oldY + yAdd;
					}
					
					if(EntityList.get(i).xV > 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{	
							if(AABB.isTouching(EntityList.get(i).right, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
					}
					
					else if(EntityList.get(i).xV < 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).left, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesLeft = true;
							}
						}
					}
					
					else
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).right, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).left, newPlatformList.get(j)))
							{
								xCancel = true;
								EntityList.get(i).pushesLeft = true;
							}
						}
					}
					
					if(EntityList.get(i).yV > 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).bottom, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
					}
					else if(EntityList.get(i).yV < 0)
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).top, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).ceiling = true;
							}
						}
					}
					else
					{
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							if(AABB.isTouching(EntityList.get(i).bottom, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{			
							if(AABB.isTouching(EntityList.get(i).top, newPlatformList.get(j)))
							{
								yCancel = true;
								EntityList.get(i).ceiling = true;
							}
						}
					}
				}
			}
			
			if(EntityList.get(i).isPlayer)
			{		
				if(AABB.isTouching(EntityList.get(i).fullBox, Levels.end))
				{
					Main.timerStarted = false;
					Main.active = false;
					
					Main.right = false;
					Main.left = false;
					Main.space = false;
					
					EntityList.get(i).xV = 0;
				}
			}
		}
	}
}