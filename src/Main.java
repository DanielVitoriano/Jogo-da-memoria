import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable, KeyListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	public static int WIDTH = 320, HEIGHT = 240, SCALE = 2, fps_show;
	public static String gameState;
	
	private JFrame frame;
	private Thread thread;
	public static BufferedImage image;
	
	public static UI ui;
	public static Cards cards;
	public static Menu menu;
	
	public static void newGame() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		menu = new Menu();
		cards = new Cards();
		ui = new UI(1, 20);
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.start();
				
	}
	
	public Main() {
		addKeyListener(this);
		addMouseListener(this);
		newGame();
		gameState = "menu";
		
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //dimensoes da janela
		initFrame();
	}

	public void initFrame() {
		frame = new JFrame("Jogo da Memoria");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks = 60.0;
		double ns = 1000000000 / amoutOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		//looping do jogo
		while(isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				
				tick();
				render();
				
				frames++;
				delta--;
				}
			if(System.currentTimeMillis() - timer >= 1000) {
				fps_show = frames;
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}
	
	public void tick() {
		if(gameState == "menu") {
			menu.tick();
		}
		else if(gameState == "normal") {
			cards.tick();
			ui.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		if(gameState == "menu") menu.render(g);
		else if(gameState == "normal"){
			cards.render(g);
			ui.render(g);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font ("Arial", Font.BOLD, 12));
		g.drawString("FPS:" + fps_show, WIDTH * SCALE - 45, 20);
		
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) menu.up = true;
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) menu.down = true;
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) menu.enter = true; cards.canPlay = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(gameState == "normal") {
			cards.mouseClick = true;
			cards.mx = e.getX();
			cards.my = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
