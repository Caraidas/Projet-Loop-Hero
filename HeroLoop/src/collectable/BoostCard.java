package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class BoostCard extends AbstractCard {
	private final HashMap<String, Double> boost;

	public BoostCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven, HashMap<String, Double> boost) {
		super(cardStates, sprite, ressourcesGiven);
		this.boost = boost;
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		for (String stat : boost.keySet()) {
			player.boostStat(stat, boost.get(stat));
		}
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {
	}
	
	public HashMap<String, Double> boost() {
		return boost;
	}
}
