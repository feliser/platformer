import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main implements KeyListener {
	
	public JFrame frame;
	public JPanel panel;
	public Timer timer;
	public ActionListener updateListener;
	public Player player;
	
	public boolean initializing = true;
	public static boolean right, left, space, down, jump = true;
	public static int xScroll, yScroll, xOffset, yOffset;
	public static final double zoom = 2.5;
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		init();
		player = new Player(-58, 223, 52, 96);
		CollisionManager.addPlatform(1, -5, 1, "blueBrick-rightEnd.png");
		CollisionManager.addPlatform(0, -5, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(-1, -5, 1, "blueBrick-leftEnd.png");
		CollisionManager.addPlatform(-2, -4, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(-2, -3, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(-2, -4, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(2, -1, 1, "blueBrick-rightEnd.png");
		CollisionManager.addPlatform(12, 3, 1, "blueBrick-horizontal.png");
		
		CollisionManager.addPlatform(-1, -1, 1, "blueBrick-leftEnd.png");
		CollisionManager.addPlatform(0, -1, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(1, -1, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(2, 0, 1, "blueBrick-full.png");
		
		CollisionManager.addPlatform(2, -6, 1, "blueBrick-leftEnd.png");
		CollisionManager.addPlatform(3, -6, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(4, -6, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(5, -6, 1, "blueBrick-rightEnd.png");
		
		CollisionManager.addPlatform(12, -5, 1, "blueBrick-leftEnd.png");
		CollisionManager.addPlatform(13, -5, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(14, -5, 1, "blueBrick-rightEnd.png");
		
		CollisionManager.addPlatform(18, -2, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(18, -1, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(18, 0, 1, "blueBrick-horizontal.png");
		
		CollisionManager.addPlatform(13, 2, 1, "blueBrick-horizontal.png");
		CollisionManager.addPlatform(14, 2, 1, "blueBrick-horizontal.png");
		
		initializing = false;
		timer.start();
	}
	
	public void init()
	{
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		
		frame = new JFrame("Ancient Empire");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(screenSize);
		frame.setUndecorated(true);
		frame.setResizable(false);
		
		xOffset = frame.getWidth() / 2;
		yOffset = frame.getHeight() / 2;
		
		panel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(!initializing)
				{
					tick();
					draw(g);
				}
			}
		};
		
		panel.addKeyListener(this);
		panel.setFocusable(true);
		panel.grabFocus();
		panel.setBackground(new Color(128, 128, 128));
		
		frame.add(panel);
		
		updateListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.repaint();
			}
		};
		timer = new Timer(16, updateListener);
		
		frame.setVisible(true);
		
		CollisionManager.init();
	}
	
	public void draw(Graphics g)
	{
		player.draw(g);
		CollisionManager.draw(g);
		
		Levels.draw(g);
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
		xScroll -= ((xScroll - player.x - player.width / 2) * 0.2);
		yScroll -= ((yScroll - player.y - player.height / 2) * 0.2);
		
		CollisionManager.update();
		
		GameTimer.update();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
		{
			space = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			player.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
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
	
	@Override
	public void keyTyped(KeyEvent e) {}
}