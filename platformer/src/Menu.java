import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class Menu 
{
	public static int state = 0;
	public static boolean inMenu = true;
	public static Font font;
	
	public static void draw(Graphics g)
	{
		if(state == 0)
		{
			g.setFont(font);
			g.setColor(Color.BLACK);
			drawCenteredString("ANCIENT EMPIRE", (int) (Main.screenSize.getWidth())+10, 210, g);
			g.setColor(Color.WHITE);
			drawCenteredString("ANCIENT EMPIRE", (int) (Main.screenSize.getWidth()), 200, g);
		}
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
	
	public static void drawCenteredString(String s, int w, int h, Graphics g) 
	{
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

}
