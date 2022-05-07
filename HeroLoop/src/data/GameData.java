package data;

import collectable.Card;
import entities.Player;
import java.util.ArrayList;
import java.util.Random;
import map.Map;
import map.RoadCell;
import time.TimeData;

public class GameData { // Takes care of all the data in the game that should be memorized
	private final Map map;
	private int loopCount = 1;
	private int selectedCard = -1; // -1 means no card selected
	private int selectedItem = -1;
	private ArrayList<Card> drawPile = new ArrayList<>();
	private final TimeData timeData;
	
	private boolean inBattle = false;
	
	public GameData(Map map, TimeData timeData) {
		this.map = map;
		this.timeData = timeData;
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
				((RoadCell) map.getCell(map.loop().get(i).column(), map.loop().get(i).line())).spawn(timeData.dayCount(), loopCount);
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
	
	public void selectItem(int i) {
		selectedItem = i;
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
	
	public void switchGameState() { 
		if (inBattle) {
			inBattle = false;
			timeData.start();
		} else {
			inBattle = true;
			timeData.stop();
		}
	}
	
	// Getters :
	
	public boolean inBattle() {
		return inBattle;
	}
	
	public int getLoopCount() {
		return loopCount;
	}
	
	public Map map() {
		return map;
	}
	
	public int selectedCard() {
		return selectedCard;
	}
	
	public int selectedItem() {
		return selectedItem;
	}

}
