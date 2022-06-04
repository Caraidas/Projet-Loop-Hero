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
			BoostCard rock = new BoostCard(c, "Rock.png", lst, stats);
			
			return rock;
			
		case "Meadow":
			c = new ArrayList<>();
			stats = new HashMap<>();
			lst = new ArrayList<>();
			c.add(CardState.LANDSCAPE);
			stats.put("hp", 2.0);
			DailyBoost meadow = new DailyBoost(c, "Meadow.png", lst, stats);
			
			return meadow;
			
		case "Cemetery":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			lst.add("Preserved Pebbles");
			lst.add("Memory Fragment");
			SpawnCard cemetery = new SpawnCard(c, "Cemetery.png", lst, 3, "Skeleton", 0, 0);
			
			return cemetery;
			
		case "Grove":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			lst.add("Stable Branches");
			SpawnCard grove = new SpawnCard(c, "Grove.png", lst, 2, "RatWolf", 0, 1);
			
			return grove;
			
		case "Ruins":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			SpawnCard ruins = new SpawnCard(c, "Ruins.png", lst, 2, "ScorchWorm", 0, 0);
			
			return ruins;
			
		case "SpiderCocoon":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROADSIDE);
			SpawnCard spiderCocoon = new SpawnCard(c, "SpiderCocoon.png", lst, 1, "Spider", 0, 1);
			
			return spiderCocoon;
		
		case "Oblivion":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			c.add(CardState.LANDSCAPE);
			c.add(CardState.ROADSIDE);
			Oblivion oblivion = new Oblivion(c, "Oblivion.png", lst);
			
			return oblivion;
			
		case "VampireMansion":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			lst.add("Vampire");
			c.add(CardState.ROADSIDE);
			stats = new HashMap<>();
			stats.put("vampirism", 10.0);
			ZoneCard vampireMansion = new ZoneCard(c, "VampireMansion.png", lst, new GridPosition(0, 0), new ArrayList<>(), 
					stats, lst, true, 1);
			
			return vampireMansion;
			
		case "Battlefield":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			lst.add("Chest");
			lst.add("Mimic");
			c.add(CardState.ROADSIDE);
			stats = new HashMap<>();
			stats.put("undead", 50.0);
			BattleField battleField = new BattleField(c, "Battlefield.png", lst, new GridPosition(0, 0), new ArrayList<>(), 
					stats, lst, true, 1, "BloodPath");
			
			return battleField;
			
		case "BloodPath":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			SpawnCard bloodPath = new SpawnCard(c, "BloodPath.png", lst, 4, "BloodClot", 0, 0);
			
			return bloodPath;
			
		case "WheatFields":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			WheatFields wheatFields = new WheatFields(c, "WheatFields.png", lst, 4, "Scarecrow", 0, 0);
		
			return wheatFields;
			
		case "OverGrownField":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			OverGrownField overGrownField = new OverGrownField(c, "WheatFields.png", lst, 4, "Scarecrow", 0, 0);
		
			return wheatFields;
			
		case "Village":
			c = new ArrayList<>();
			lst = new ArrayList<>();
			c.add(CardState.ROAD);
			stats = new HashMap<>();
			stats.put("hp", 15.0);
			Village village = new Village(c, "Village.png", lst, stats);
		
			return village;

		default:
			return null;
		}
	}

	void giveRessource(Player player);
	
	boolean acceptCardState(GridPosition pos, GameData gameData);

}
