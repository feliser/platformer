import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class GameTimer 
{
	public static double time;
	
	public static void update()
	{
		time += 17;
	}
	
	public static void draw(Graphics g)
	{
		g.setColor(new Color(50, 50, 50));
        InputStream inputStream = Main.class.getResourceAsStream("Font/FFFFORWA.TTF");
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(70f);
            g.setFont(font);
            g.drawString(String.valueOf(time/1000), 20, 110);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void reset()
	{
		time = 0.000;
	}
}
