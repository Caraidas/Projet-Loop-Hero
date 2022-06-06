package data;

import collectable.BattleField;
import collectable.Card;
import collectable.DailyBoost;
import collectable.Oblivion;
import collectable.Village;
import collectable.ZoneCard;
import entities.Player;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import map.Map;
import map.RoadCell;
import time.TimeData;

public class GameData implements Serializable{ // Takes care of all the data in the game that should be memorized

	private static final long serialVersionUID = 5334584766800637562L;
	private final Map map;
	private int loopCount = 1;
	private int selectedCard = -1; // -1 means no card selected
	private int selectedItem = -1;
	private ArrayList<Card> drawPile = new ArrayList<>();
	private TimeData timeData; // not final because of the save
	
	private boolean inBattle = false; 
	private boolean inGame = false;
	private boolean inRessourceMenu = false;
	private boolean inStudio = false; // to create new loops easily
	
	public GameData(Map map, TimeData timeData) {
		this.map = map;
		this.timeData = timeData;
		
		// add cards to the deck pile with the number of cards
		
		this.addCardToDrawPile(Card.createCard("Rock"), 12);
		this.addCardToDrawPile(Card.createCard("Meadow"), 14);
		this.addCardToDrawPile(Card.createCard("Meadow"), 3);
		this.addCardToDrawPile(Card.createCard("Oblivion"), 2);
		this.addCardToDrawPile(Card.createCard("Ruins"), 3);
		this.addCardToDrawPile(Card.createCard("SpiderCocoon"), 6);
		this.addCardToDrawPile(Card.createCard("WheatFields"), 6);
		this.addCardToDrawPile(Card.createCard("Village"), 3);
		this.addCardToDrawPile(Card.createCard("Beacon"), 3);
	}
	
	public void addCardToDrawPile(Card c, int n) {
		for (int i = 0; i < n; i++) {
			drawPile.add(c);
		}
	}
	
	public void updateLoopCount() {
		loopCount++;
		
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				if (map.getCell(i, j).card() instanceof BattleField) {
					map.getCell(i, j).card().spawn(i, j, this, timeData.dayCount(), "");
				}
			}
		}
	}
	
	public void spawn() {
		for (int i = 0; i < map.loop().size(); i++) {
			if (i != 0) {
				((RoadCell)map.getCell(map.loop().get(i).line(), map.loop().get(i).column())).spawn(timeData.dayCount(), loopCount, "");
			}
		}
		
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				if (map.getCell(i, j).card() != null && (map.getCell(i, j).card() instanceof BattleField == false)) {
					System.out.println("day in gamedata from timeData.dayCount() = " + timeData.dayCount());
					map.getCell(i, j).card().spawn(i, j, this, timeData.dayCount(), "");
				}
			}
		}
	}
	
	public void dailyCardAction(Player player) {
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				if (map.getCell(i, j).card() instanceof DailyBoost) {
					map.getCell(i, j).card().cardAction(player, this, timeData, new GridPosition(i, j));
				}
			}
		}
	}
	
	public void cardAction(Player player) {
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				if (map.getCell(i, j).card() instanceof ZoneCard) {
					map.getCell(i, j).card().cardAction(player, this, timeData, new GridPosition(i, j));
				}
			}
		}
	}
	
	public void chooseQuestMonster() {
		Random rand = new Random();
		int n = rand.nextInt(33) + 1;
		
		GridPosition g = map().loop().get(n);
		RoadCell cell = ((RoadCell)map().getCell(g));
		
		while (cell.card() instanceof Village) {
			n = rand.nextInt(33) + 1;
			g = map().loop().get(n);
			cell = ((RoadCell)map().getCell(g));
		}
		
		if (cell.hasNoMonsters()) {
			System.out.println("in has no monsters");
			cell.spawn(timeData.dayCount(), loopCount, "Quest");
		}
		
		n = rand.nextInt(cell.getEntities().size());
		cell.getEntities().get(n).boostQuest();
	}
	
	public void generateGameBoard() throws IOException {
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
		if (!(c instanceof Oblivion)) {
			map.getCell(pos).addCard(c);
			drawPile.add(c);
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
	
	public void startGame() {
		inGame = true;
	}
	
	public void endGame() {
		inGame = false;
	}
	 
	public void updateRessourceState() {
		inRessourceMenu = !inRessourceMenu;
	}
	
	public void updateRessourceState(boolean b) {
		inRessourceMenu = false;
	}
	
	public void setTimeData(TimeData td) {
		timeData = td;
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
	
	public boolean inGame() {
		return inGame;
	}
	
	public boolean inRessourceMenu() {
		return inRessourceMenu;
	}

	public boolean isInStudio() {
		return inStudio;
	}

	public void setInStudio(boolean inStudio) {
		this.inStudio = inStudio;
	}

}
