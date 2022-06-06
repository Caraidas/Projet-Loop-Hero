package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class Village extends EnteringBoost implements Serializable {
	
	/*
	 * heal the player when he walks on the tile and gives a quest to kill a special enemy then gives a reward when the quest is accomplished
	 */

	private static final long serialVersionUID = -2626588329519838811L;
	private int healFactor = 1;

	public Village(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			HashMap<String, Double> boost) {
		super(cardStates, sprite, ressourcesGiven, boost);
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		for (String stat : super.boost().keySet()) {
			player.globalheal(super.boost().get(stat) + healFactor * (5 * gameData.getLoopCount()));
		}
		
		if (player.accomplishedQuest()) {
			player.addAccomplishedQuest(-1);
			System.out.println("bravo t'as reussi la quete ! ");
			Item item = new Item();
			item.setStats(gameData.getLoopCount() + 3);
			player.addItemInInventory(item);
		}	
		gameData.spawnQuestMonster();
	}
	
	public void destroyFields(GameData gameData, GridPosition pos) { //When the village is removed with an oblivion, remove Wheatfield for Overgrown fields
		int lst[] = new int[2];
		lst[0] = -1;
		lst[1] = 1;
		
		for (int i : lst) {
			if (gameData.map().isValid(pos.line() + i, pos.column()) && gameData.map().getCell(pos.line() + i, pos.column()).card() 
					instanceof WheatFields) {
				gameData.map().getCell(pos.line() + i, pos.column()).addCard(Card.createCard("OverGrownField"));
			}
			
			if (gameData.map().isValid(pos.line(), pos.column() + i) && gameData.map().getCell(pos.line(), pos.column() + i).card() 
					instanceof WheatFields) {
				gameData.map().getCell(pos.line(), pos.column() + i).addCard(Card.createCard("OverGrownField"));
			}
		}
	}
	
	public void createFields(GameData gameData, GridPosition pos) { // When the village is placed next to a Overgrown field, remove Overgrown field for Wheat field
		int lst[] = new int[2];
		lst[0] = -1;
		lst[1] = 1;
		
		for (int i : lst) {
			if (gameData.map().isValid(pos.line() + i, pos.column()) && gameData.map().getCell(pos.line() + i, pos.column()).card() 
					instanceof OverGrownField) {
				gameData.map().getCell(pos.line() + i, pos.column()).addCard(Card.createCard("WheatFields"));
			}
			
			if (gameData.map().isValid(pos.line(), pos.column() + i) && gameData.map().getCell(pos.line(), pos.column() + i).card() 
					instanceof OverGrownField) {
				gameData.map().getCell(pos.line(), pos.column() + i).addCard(Card.createCard("WheatFields"));
			}
		}
	}
	
	public void addToHealFactor(int i) {
		healFactor += i;
	}
	
}
