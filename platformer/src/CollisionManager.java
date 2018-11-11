import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

public class CollisionManager 
{ 
	public static ArrayList<Entity> EntityList;
	public static ArrayList<String> PlatformList;
	public static ArrayList<String> newPlatformList;
	public static int rX, rY, rW, rH;
	public static final int TILE_SIZE = 64;
	
	public static void init()
	{
		EntityList = new ArrayList<Entity>();
		PlatformList = new ArrayList<String>();
		newPlatformList = new ArrayList<String>();
	}
	
	public static void addPlatform(int x, int y, int type, String filename)
	{
		PlatformList.add(x + "," + y + "," + type);
		try 
		{
			Levels.addImage(filename, x, y);
		} 
		catch (IOException e) { e.printStackTrace(); }
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
					AABB box = new AABB(EntityList.get(i).x - 2, EntityList.get(i).y - 2, Math.abs((EntityList.get(i).x) - (EntityList.get(i).nX + EntityList.get(i).width)) + 4, Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4);

					rX = box.x;
					rY = box.y;
					rW = box.width;
					rH = box.height;
					
					for(int j = 0; j < PlatformList.size(); j++)
					{
						String[] tokens = PlatformList.get(j).split(",");
						
						if(AABB.isTouching(new AABB(EntityList.get(i).x - 6, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(new AABB(EntityList.get(i).x + EntityList.get(i).width, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(box, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
				else
				{
					AABB box = new AABB(EntityList.get(i).x - 2, EntityList.get(i).nY - 2, Math.abs((EntityList.get(i).x) - (EntityList.get(i).nX + EntityList.get(i).width)) + 4, Math.abs((EntityList.get(i).nY) - (EntityList.get(i).y + EntityList.get(i).height)) + 4);

					rX = box.x;
					rY = box.y;
					rW = box.width;
					rH = box.height;
					
					for(int j = 0; j < PlatformList.size(); j++)
					{
						String[] tokens = PlatformList.get(j).split(",");
						if(AABB.isTouching(new AABB(EntityList.get(i).x - 6, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(new AABB(EntityList.get(i).x + EntityList.get(i).width, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(box, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
					AABB box = new AABB(EntityList.get(i).nX - 2, EntityList.get(i).y - 2, Math.abs((EntityList.get(i).nX) - (EntityList.get(i).x + EntityList.get(i).width)) + 4, Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4);

					rX = box.x;
					rY = box.y;
					rW = box.width;
					rH = box.height;
					
					for(int j = 0; j < PlatformList.size(); j++)
					{
						String[] tokens = PlatformList.get(j).split(",");
						if(AABB.isTouching(new AABB(EntityList.get(i).x - 6, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
						//	System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(new AABB(EntityList.get(i).x + EntityList.get(i).width, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(box, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							newPlatformList.add(PlatformList.get(j));
						}
					}
				}
				else
				{
					AABB box = new AABB(EntityList.get(i).nX - 2, EntityList.get(i).nY - 2, Math.abs((EntityList.get(i).nX) - (EntityList.get(i).x + EntityList.get(i).width)) + 4, Math.abs((EntityList.get(i).y) - (EntityList.get(i).nY + EntityList.get(i).height)) + 4);

					rX = box.x;
					rY = box.y;
					rW = box.width;
					rH = box.height;
					
					for(int j = 0; j < PlatformList.size(); j++)
					{
						String[] tokens = PlatformList.get(j).split(",");
						if(AABB.isTouching(new AABB(EntityList.get(i).x - 6, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).lwallJump = true;
							}
						}
						if(AABB.isTouching(new AABB(EntityList.get(i).x + EntityList.get(i).width, EntityList.get(i).y, 6, EntityList.get(i).height), new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
						{
							//System.out.println(tokens[2] + " ? " + tokens[2].equals("1"));
							if(tokens[2].equals("1"))
							{
								EntityList.get(i).rwallJump = true;
							}
						}
						if(AABB.isTouching(box, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).right, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).left, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).right, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).left, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).bottom, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).top, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).bottom, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).top, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).right, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).left, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).right, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
							{
								xCancel = true;
								EntityList.get(i).pushesRight = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							String[] tokens = newPlatformList.get(j).split(",");
							if(AABB.isTouching(EntityList.get(i).left, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							if(AABB.isTouching(EntityList.get(i).bottom, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).top, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
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
							String[] tokens = newPlatformList.get(j).split(",");
							if(AABB.isTouching(EntityList.get(i).bottom, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
							{
								yCancel = true;
								EntityList.get(i).onGround = true;
							}
						}
						
						EntityList.get(i).update();
						
						for(int j = 0; j < newPlatformList.size(); j++)
						{
							String[] tokens = newPlatformList.get(j).split(",");
							
							if(AABB.isTouching(EntityList.get(i).top, new AABB(((Integer.parseInt(tokens[0]) * TILE_SIZE)), (-(Integer.parseInt(tokens[1]) * TILE_SIZE)), TILE_SIZE, TILE_SIZE)))
							{
								yCancel = true;
								EntityList.get(i).ceiling = true;
							}
						}
					}
				}
			}
		}
	}
	
	public static void draw(Graphics g)
	{
//		for(int i = 0; i < PlatformList.size(); i++)
//		{
//			String[] tokens = PlatformList.get(i).split(",");
//
//			if(tokens[2].equals("1"))
//			{
//				g.setColor(Color.GRAY);
//				g.drawRect(((Integer.parseInt(tokens[0]) * TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * TILE_SIZE)) - Main.yScroll + Main.yOffset, TILE_SIZE, TILE_SIZE);
//			}
//			else
//			{
//				g.setColor(Color.BLACK);
//				g.drawRect(((Integer.parseInt(tokens[0]) * TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * TILE_SIZE)) - Main.yScroll + Main.yOffset, TILE_SIZE, TILE_SIZE);
//			}
//		}
//		g.setColor(Color.BLACK);
//		
		GameTimer.draw(g);
	}
}