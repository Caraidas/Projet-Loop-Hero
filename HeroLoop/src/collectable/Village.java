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
	
	public void addToHealFactor(int i) {
		healFactor += i;
	}
	
}
