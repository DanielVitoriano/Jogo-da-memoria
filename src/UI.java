import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class UI {
	
	public int hits;
	private int score, seconds, minutes;
	private boolean wait;
	public boolean lose;
	private Timer timer;
	
	public UI(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
		
	}
	
	public void tick() {
		
		if(!wait) {
			wait = true;
			timer = new Timer();
			
			
			TimerTask timer_tick = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					seconds--;
					wait = false;
				}
				
			};
			
			timer.schedule(timer_tick, 1000);
		}
		
		if(seconds <= 0) {
			minutes--;
			seconds = 60;
		}
		
		if(minutes < 0) {
			lose = true;
		}
		
	}
	
	public void render(Graphics g) {
		if(Main.gameState == "normal") {
			g.setColor(Color.white);
			g.setFont(new Font ("Arial", Font.BOLD, 12));
			g.drawString("SCORE: " + score, 10, 20);
			
			g.setColor(Color.white);
			g.setFont(new Font ("Arial", Font.BOLD, 12));
			g.drawString("TIME LEFT: " + minutes + ":" + seconds, 100, 20);
		}
	}
	
	public void hits() {
		hits++;
		score += hits * (((minutes * 60) + seconds) * 0.1f);
	}
	
	public void increaseTime() {
		seconds += 5;
	}
	
	public void decreaseTime() {
		seconds -= 8;
	}
	
}
