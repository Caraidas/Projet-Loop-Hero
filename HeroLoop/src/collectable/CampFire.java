package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class CampFire extends EnteringBoost {

	private static final long serialVersionUID = 1707661259165489194L;

	public CampFire(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			HashMap<String, Double> boost) {
		super(cardStates, sprite, ressourcesGiven, boost);
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		player.campFireHeal(20);
	}

}
