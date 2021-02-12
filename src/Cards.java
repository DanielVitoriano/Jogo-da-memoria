import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Cards {
	
	private Timer timer;// = new Timer();
	private static List<Position> positions;
	public static List<Color> list_color;
	public static List<Card> list_cards, cards_openned;
	private static int xx[], yy[];
	public int mx, my;
	public boolean mouseClick, canPlay, wait;
	Rectangle mouseArea;
 	
	private int max_cards = 8, distance = 75, x_in = 20, y_in = 20, color_index = 0, lines = 2;
	
	public Cards() {

		
		xx = new int[(max_cards)];
		yy = new int[(lines)];
		positions = new ArrayList<Position>();
		list_color = new ArrayList<Color>();
		cards_openned = new ArrayList<Card>();
		addColors();
		set_adresses();
		Collections.shuffle(list_color); //embaralhando as cores
		Collections.shuffle(positions); //embaralha as cartas
		
		list_cards = new ArrayList<Card>();
		for(int i = 0; i < max_cards; i++) { //instanciando as cartas dentro da lista de cartas
			if(color_index >= max_cards) color_index = 0;
			list_cards.add(new Card(list_color.get(color_index),  positions.get(i)));
			color_index++;
		}

		Collections.shuffle(list_color); //embaralhandoas cores novamente para o par das cartas

		for(int i = max_cards; i < max_cards * 2; i++) { //instanciando as cartas dentro da lista de cartas
			if(color_index >= max_cards) color_index = 0;
			list_cards.add(new Card(list_color.get(color_index),  positions.get(i)));
			color_index++;
		}
	}
	
	public void tick() {
		if(Main.ui.hits == max_cards || Main.ui.lose) {
			Main.gameState = "menu";
			
			Main.newGame();
			
			return;
		}
		if(Main.gameState == "normal") {
			if(cards_openned.size() < 2){
				click();
			}
			
			if(cards_openned.size() == 2) {
				canPlay = false;
				if(cardsOpennedEquals()) {
					Main.ui.hits();
					Main.ui.increaseTime();
					cards_openned.get(0).on = false;
					cards_openned.get(1).on = false;
					cards_openned.clear();
				}else if(!cardsOpennedEquals()) {
					
					if(!wait) {
						wait=true;
						timer = new Timer();
						
						
						TimerTask showOffCards = new TimerTask(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								cards_openned.get(0).showOff();
								cards_openned.get(1).showOff();
								cards_openned.clear();
								wait = false;
							}
							
						};
						
						timer.schedule(showOffCards, 1000);
						Main.ui.decreaseTime();
					}
					
				}else {
					System.out.println("avocados da  lei");
				}
			
			}else canPlay = true;
		}	
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < list_cards.size(); i++) { //renderizando as cartas
			list_cards.get(i).render(g);
		}
	}

	
	private void addColors() { //cores disponíveis deve ser >= que max_cards
		list_color.add(Color.red);
		list_color.add(Color.blue);
		list_color.add(Color.yellow);
		list_color.add(Color.green);
		list_color.add(Color.gray);
		list_color.add(Color.cyan);
		list_color.add(Color.ORANGE);
		list_color.add(Color.MAGENTA);
	}
	
	public boolean cardsOpennedEquals() {
		boolean equals = false;
		if(cards_openned.get(0).cor == cards_openned.get(1).cor) equals = true;
		
		return equals;
	}
	
	public void click() {
		if(mouseClick && canPlay) {
			mouseClick = false;
			mouseArea = new Rectangle(mx, my, 5, 5);
			for(int i = 0; i < list_cards.size(); i++) {
				if(list_cards.get(i).card_area.intersects(mouseArea) && list_cards.get(i).on) {
					list_cards.get(i).show();
					cards_openned.add(list_cards.get(i));
					
				}
			}
			mouseArea = null;
		}
		
	}
	
	private void set_adresses() { //setando os xx e yy disponiveis para as cartas

		for(int i = 0; i < yy.length; i++) {
			yy[i] = y_in + ((distance + Card.getHeight() * i +1));
			for(int c = 0; c < xx.length; c++) {
				xx[c] = x_in + (distance * c);
			}
		}
		int a = 0, b = 0;
		while(positions.size() <= (max_cards * 2) - 1) {
			if(b == yy.length) break;
			if(a == xx.length) {
				a = 0;
				b ++;
				yy[b] += distance;
			}
			positions.add(new Position(xx[a], yy[b]));
			a++;
		}
	}
}
