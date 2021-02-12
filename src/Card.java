import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Card {

	private String card_state;
	public Color cor;
	public Rectangle card_area;
	public boolean on = true;
	
	//dimensoes
	private static int width = 70, height = 100;
	private int x, y;
	
	public Card(Color cor, Position xy) {
		this.card_state = "verso";
		this.cor = cor;
		this.x = xy.getX();
		this.y = xy.getY();
		
		card_area = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics g) {
		
		if(card_state == "frente") {
			g.setColor(cor);
			g.fillRect(x, y, width, height);
		}
		else if (card_state == "verso") {
			g.setColor(Color.white);
			g.fillRect(x, y, width, height);
		}
	}
	
	public void show() {
		card_state = "frente";
	}
	
	public void showOff() {
		card_state = "verso";
	}
	
	public static int getHeight() {
		return height;
	}

	
}
