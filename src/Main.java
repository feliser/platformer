import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.UUID;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main implements KeyListener {
	
	public JFrame frame;
	public JPanel panel;
	public Timer timer;
	public Timer variableTimer;
	public ActionListener updateListener;
	public ActionListener variableListener;
	public Player player;
	
	public static UUID localUUID;
	public static boolean initializing = true, timerStarted = false, active = true;
	public static boolean right, left, space, down, jump = true;
	public static int xScroll, yScroll, xOffset, yOffset;
	public static double zoom;
	
	public static void main(String[] args)
	{
		try {
            new Main();
        } 
		catch (Throwable t) {
            JOptionPane.showMessageDialog(
            null, t.getClass().getSimpleName() + ": " + t.getMessage() + "\n" + Arrays.toString(t.getStackTrace()));
            throw t;
        }
	}
	
	public Main()
	{
		init();
		
		Levels.loadLevel("1.txt");
		
		player = new Player(-58, 223, 52, 96);
		
		initializing = false;
		timer.start();
		variableTimer.start();
	}
	
	public void getUUIDOrAdd()
	{
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		
		String uuid = prefs.get("UUID", "");
		
		if (uuid.length() > 1)
		{
			localUUID = UUID.fromString(uuid);
		}
		
		if (localUUID == null)
		{
			localUUID = UUID.randomUUID();
			prefs.put("UUID", localUUID.toString());
		}
	}
	
	public void init()
	{
		getUUIDOrAdd();
		
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		
		if(screenSize.getWidth() == 1920)
		{
			zoom = 1.5;
		}
		else
		{
			zoom = screenSize.getWidth() / 1366;
		}

		frame = new JFrame("Ancient Empire");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(screenSize);
		frame.setUndecorated(true);
		frame.setResizable(false);
		
		xOffset = (int)((frame.getWidth() / 2) / Main.zoom);
		yOffset = (int)((frame.getHeight() / 2) / Main.zoom);
		
		panel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(!initializing)
				{
					draw(g);
				}
			}
		};
		
		panel.addKeyListener(this);
		panel.setFocusable(true);
		panel.grabFocus();
		panel.setBackground(new Color(135, 206, 235));
		
		frame.add(panel);
		
		updateListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.repaint();
			}
		};
		variableListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!initializing)
				{
					tick();
				}
			}
		};
		timer = new Timer(0, updateListener);
		
		variableTimer = new Timer(16, variableListener);
		
		frame.setVisible(true);
		
		CollisionManager.init();
		
		GameTimer.init();
	}
	
	public void draw(Graphics g)
	{
		Levels.draw(g);
		player.draw(g);
		GameTimer.draw(g);
	}
	
	public void tick()
	{
		if(left && player.xV > 0)
		{
			player.xV -= 1.5;
		}
		if(right && player.xV < 0)
		{
			player.xV += 1.5;
		}
		player.keyCheck();
		
		xScroll -= (((xScroll - player.x - player.width / 2) * Main.zoom) * 0.10);
		yScroll -= (((yScroll - player.y - player.height / 2) * Main.zoom) * 0.10);
			
		if(Levels.leftScroll > xScroll)
		{
			xScroll = Levels.leftScroll;
		}
		if(Levels.rightScroll < xScroll)
		{
			xScroll = Levels.rightScroll;
		}
		if(Levels.topScroll < yScroll)
		{
			yScroll = Levels.topScroll;
		}
		if(Levels.bottomScroll > yScroll)
		{
			yScroll = Levels.bottomScroll;
		}
		
		CollisionManager.update();
		
		if(timerStarted)
		{
			GameTimer.update();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(active)
		{
			if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			{
				space = true;
				
				if(!timerStarted)
				{
					timerStarted = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				right = true;
				
				if(!timerStarted)
				{
					timerStarted = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				left = true;
				
				if(!timerStarted)
				{
					timerStarted = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				down = true;
				
				if(!timerStarted)
				{
					timerStarted = true;
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			player.reset();
			active = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(active)
		{
			if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(player.yV < -2 && !player.wallJump)
				{
					player.yV *= 0.5;
				}

				space = false;
				jump = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				right = false;
				player.acceleration = 0.3f;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				left = false;	
				player.acceleration = 0.3f;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				down = false;
			}
		}
	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}