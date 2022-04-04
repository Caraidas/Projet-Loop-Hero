package data;

import collectable.Card;
import entities.Player;
import java.util.ArrayList;
import java.util.Random;
import map.Map;
import map.RoadCell;

public class GameData { // Takes care of all the data in the game that should be memorized
	private final Map map;
	private int loopCount = 0;
	private int selectedCard = -1; // -1 means no card selected
	private ArrayList<Card> drawPile = new ArrayList<>();
	
	public GameData(Map map) {
		this.map = map;
		this.addCardToDrawPile(Card.createCard("Rock"), 12);
		this.addCardToDrawPile(Card.createCard("Grove"), 4);
		this.addCardToDrawPile(Card.createCard("Meadow"), 14);
	}
	
	public void addCardToDrawPile(Card c, int n) {
		for (int i = 0; i < n; i++) {
			drawPile.add(c);
		}
	}
	
	public void updateLoopCount() {
		loopCount++;
	}
	
	public void spawn() {
		for (int i = 0; i < map.loop().size();i++) {
			if (i != 0) {
				((RoadCell) map.getCell(map.loop().get(i).column(), map.loop().get(i).line())).spawn();
			}
		}
	}
	
	public void generateGameBoard() {
		map.generateLoop();
		map.generateMap();
	}
	
	public void selectCard(int i) {
		selectedCard = i;
	}
	
	public Card drawCard() { // draws a random card
		Random rand = new Random();
		
		int n = rand.nextInt(drawPile.size());
		
		return drawPile.get(n);	
	}
	
	public void depositCard(Card c, GridPosition pos) { // deposits the card c on the cell of position pos
		map.getCell(pos).addCard(c);
		drawPile.add(c);
	}
	
	public void applyDailyBoosts(Player player) {
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				if (map.getCell(i, j).card() != null) {
					player.dailyStatBoost(map.getCell(i, j).card());
				}
			}
		}
	}
	
	// Getters :
	
	public int getLoopCount() {
		return loopCount;
	}
	
	public Map map() {
		return map;
	}
	
	public int selectedCard() {
		return selectedCard;
	}

}
