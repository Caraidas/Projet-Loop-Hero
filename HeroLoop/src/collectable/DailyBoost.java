package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import time.TimeData;

public class DailyBoost extends BoostCard {

	public DailyBoost(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven, HashMap<String, Double> boost) {
		super(cardStates, sprite, ressourcesGiven, boost);
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {		
		super.cardAction(player, gameData, timeData, pos);
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {
		// TODO Auto-generated method stub
	}
}