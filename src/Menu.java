import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {

	private String[] options = {"Jogar", "Sair"};
	public int currentOption = 0, maxOptions = options.length - 1;
	public boolean up, down, enter, somar = false;
	
	public void tick() {
		if(Main.gameState == "menu") {
			if(up) {
				up = false;
				currentOption --;
				if(currentOption < 0) currentOption = maxOptions;
			}
			if(down) {
				down = false;
				currentOption ++;
				if(currentOption > maxOptions) currentOption = 0;
			}

		}	
		if(enter) {
			enter = false;
			if(options[currentOption] == "Jogar") {
				Main.gameState = "normal";
			}
			else if(options[currentOption] == "Sair") System.exit(1);
		}
	}
	
	public void render(Graphics g) {
		g.setFont(new Font ("Arial", Font.BOLD, 28));
		if(options[currentOption] == "Jogar")g.setColor(Color.green); 
		else g.setColor(Color.white);
		g.drawString("JOGAR", Main.WIDTH - 50, Main.HEIGHT - 5);
		
		g.setFont(new Font ("Arial", Font.BOLD, 28));
		if(options[currentOption] == "Sair")g.setColor(Color.green);
		else g.setColor(Color.white);
		g.drawString("SAIR", Main.WIDTH - 33, Main.HEIGHT + 35);
		
		g.setFont(new Font ("Arial", Font.BOLD, 12));
		g.setColor(Color.BLUE);
		g.drawString("Código por: Daniel Vitoriano", 10, 20);
		
	}
	
}
