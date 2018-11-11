import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Levels 
{
	public static ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	public static ArrayList<String> infoList = new ArrayList<String>();
	public static final String PATH_PREFIX = "Images/";
	
	public static void addImage(String filename, int x, int y) throws IOException
	{
		BufferedImage img;
		img = ImageIO.read(Main.class.getResource(PATH_PREFIX + filename)); 
		imageList.add(img);
		infoList.add(x + "," + y);
	}
	
	public static void draw(Graphics g)
	{
		for(int i = 0; i < imageList.size(); i++)
		{
			String[] tokens = infoList.get(i).split(",");
			g.drawImage(imageList.get(i), ((Integer.parseInt(tokens[0]) * CollisionManager.TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * CollisionManager.TILE_SIZE)) - Main.yScroll + Main.yOffset, null);
			g.drawString(tokens[0] + ", " + tokens[1], ((Integer.parseInt(tokens[0]) * CollisionManager.TILE_SIZE)) - Main.xScroll + Main.xOffset, (-(Integer.parseInt(tokens[1]) * CollisionManager.TILE_SIZE)) - Main.yScroll + Main.yOffset);
		}
	}

}
