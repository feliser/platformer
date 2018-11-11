import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Levels 
{
	public static ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	public static ArrayList<String> infoList = new ArrayList<String>();
	public static final String IMAGES_PATH_PREFIX = "Images/";
	public static final String LEVELS_PATH_PREFIX = "/Levels/";
	public static String end;
	
	public static void addImage(String filename, int x, int y) throws IOException
	{
		BufferedImage img;
		img = ImageIO.read(Main.class.getResource(IMAGES_PATH_PREFIX + filename)); 
		imageList.add(img);
		infoList.add(x + "," + y);
	}
	
	public static void draw(Graphics g)
	{
		for(int i = 0; i < imageList.size(); i++)
		{
			String[] tokens = infoList.get(i).split(",");
			g.drawImage(imageList.get(i), ((Integer.parseInt(tokens[0]) * CollisionManager.TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * CollisionManager.TILE_SIZE)) - Main.yScroll + Main.yOffset, null);
			
			//g.drawStrrirrrrng(tokens[0] + ", " + tokens[1], ((Integer.parseInt(tokens[0]) * CollisionManager.TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * CollisionManager.TILE_SIZE)) - Main.yScroll + Main.yOffset);
		}
		
		String[] tokens = end.split(",");
		
		g.drawRect(((Integer.parseInt(tokens[0]) * CollisionManager.TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * CollisionManager.TILE_SIZE)) - Main.yScroll + Main.yOffset, CollisionManager.TILE_SIZE, CollisionManager.TILE_SIZE);
	}
	
	public static void loadLevel(String filename)
	{
		InputStream is = Main.class.getClass().getResourceAsStream(LEVELS_PATH_PREFIX + filename);
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    try 
	    {
			while ((line = br.readLine()) != null) 
			{
				String[] tokens = line.split(",");
			
				if(tokens[0].equals("P"))
				{
					CollisionManager.addPlatform(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), tokens[4]);
				}
				else if(tokens[0].equals("I"))
				{
					addImage(tokens[3], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
				}
				else if(tokens[0].equals("E"))
				{
					end = (tokens[1] + "," + tokens[2]);
				}
			}
			br.close();
			isr.close();
			is.close();
		} 
	    catch (IOException e) 
	    {
			e.printStackTrace();
		}
	   


//		try {
////			List<String> linesOfFile = Files.readAllLines(path);
////			if(linesOfFile.size() != 0)
////			{
////				for(int i = 0; i < linesOfFile.size(); i++)
////				{
////					
////				}
////			}
////			else
////			{
////				System.out.println("Level: " + filename + " is null!");
////			}
//		} 
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 

	}

}
