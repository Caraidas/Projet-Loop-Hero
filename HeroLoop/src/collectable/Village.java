package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class Village extends EnteringBoost {
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
	}
	
	public void destroyFields(GameData gameData, GridPosition pos) {
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
	
	public void addToHealFactor(int i) {
		healFactor += i;
	}
	
}
