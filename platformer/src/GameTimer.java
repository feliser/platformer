import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class GameTimer 
{
	public static double time;
	public static Color gray = new Color(50, 50, 50);
	public static Font font;
	
	public static void update()
	{
		time += 17;
	}
	
	public static void init()
	{
		InputStream inputStream = Main.class.getResourceAsStream("Font/FFFFORWA.TTF");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(70f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void draw(Graphics g)
	{
		  
		g.setFont(font);
		g.setColor(Color.GRAY);
        g.drawString(String.valueOf(time/1000), 22, 112);
		g.setColor(gray);
		g.drawString(String.valueOf(time/1000), 20, 110);
	}
	
	public static void reset()
	{
		time = 0.000;
	}
}
