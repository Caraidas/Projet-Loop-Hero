package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class BoostCard extends AbstractCard implements Serializable {

	private static final long serialVersionUID = 1901361545726339994L;
	private final HashMap<String, Double> boost;

	public BoostCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven, HashMap<String, Double> boost) {

		super(cardStates, sprite, ressourcesGiven);
		this.boost = boost;
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) { // apply a boost to the player
		Objects.requireNonNull(player);
		Objects.requireNonNull(gameData);
		Objects.requireNonNull(timeData);
		Objects.requireNonNull(pos);
		
		for (String stat : boost.keySet()) {
			player.boostStat(stat, boost.get(stat));
		}
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day, String quest) {}
	
	public HashMap<String, Double> boost() { // get the statistic of the boost
		return boost;
	}
}
