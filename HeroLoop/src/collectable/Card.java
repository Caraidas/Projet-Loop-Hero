package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import time.TimeData;

public interface Card {
	
	String sprite();
	
	ArrayList<CardState> cardState();
	
	void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos);
	
	void spawn(int i, int j, GameData gameData, int day);
	
	default boolean contains(CardState c) {
		return cardState().contains(c);
	}
	
	static Card createCard(String name) {
		switch (name) {
		case "Rock":
			ArrayList<CardState> c = new ArrayList<>();
			HashMap<String, Double> stats = new HashMap<>();
			ArrayList<String> lst = new ArrayList<>();
			c.add(CardState.LANDSCAPE);
			stats.put("hpMax", 0.01);
			lst.add("Pebble");
			
			return new BoostCard(c, "Rock.png", lst, stats);
			
		case "Meadow":
			c = new ArrayList<>();
			stats = new HashMap<>();
			lst = new ArrayList<>();
			c.add(CardState.LANDSCAPE);
			stats.put("hp", 2.0);
			
			return new DailyBoost(c, "Meadow.png", lst, stats);
			
		case "Grove":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			lst.add("Stable Branches");
			
			return new SpawnCard(c, "Grove.png", lst, 2, "RatWolf", 0, 1);
			
		case "Ruins":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			
			return new SpawnCard(c, "Ruins.png", lst, 2, "ScorchWorm", 0, 0);
		
		case "Oblivion":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			c.add(CardState.LANDSCAPE);
			c.add(CardState.ROADSIDE);
			
			return new Oblivion(c, "Oblivion.png", lst);

		default:
			return null;
		}
	}

	void giveRessource(Player player);
	
	boolean acceptCardState(Cell c);

}
