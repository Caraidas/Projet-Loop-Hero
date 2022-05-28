package collectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Carte { 
	private final ArrayList<CardState> cardState; // A card can be ROAD, LANSCAPE and / or ROADSIDE
	private final HashMap<String, Integer> spawnableMonster; //String is the monster that can spawn, Integer is the rate of spawn in days
	
	private final HashMap<String, Double> boostedStats; // The stats that the card boosts upon being placed
	private final HashMap<String, Integer> dailyStatBoost; // The stats that the card boosts every day
	
	private final String ressourceGiven; // ressources given when the card is placed
	private final ArrayList<String> ressourceGivenWhenCrossed; // ressources given when the tile is crossed
	private final String sprite; 
	
	public Carte(String sprite, String ressourceGiven, ArrayList<String> ressourceGivenWhenCrossed, int moveWhenSpawn) {
		this.sprite = sprite;
		this.ressourceGiven = ressourceGiven;
		this.ressourceGivenWhenCrossed = ressourceGivenWhenCrossed;
		
		this.cardState = new ArrayList<>();
		this.boostedStats = new HashMap<>();
		this.spawnableMonster = new HashMap<>();
		this.dailyStatBoost = new HashMap<>();
	}
	
	public static Carte createCard(String name) { // this method is used to easily create cards
		switch (name) {
		case "Rock" -> {
			ArrayList<String> ressources = new ArrayList<String>();
			Carte card = new Carte("Rock.png", "Pebbles", ressources, 0);
			card.addCardState(CardState.LANDSCAPE);
			card.addBoostedStat("hpMax", 0.01);
			return card;
		}
		
		case "Grove" -> {
			ArrayList<String> ressources = new ArrayList<String>();
			ressources.add("Stick");
			Carte card = new Carte("Grove.png", "", ressources, 1);
			card.addCardState(CardState.ROAD);
			card.addSpawnableMonster("RatWolf", 2);
			return card;
		}
		
		case "Meadow" -> {
			ArrayList<String> ressources = new ArrayList<String>();
			Carte card = new Carte("Meadow.png", "Ration", ressources, 0);
			card.addCardState(CardState.LANDSCAPE);
			card.addDailyStatBoost("hp", 2);
			return card;
		}
		case "Cemetery" -> {
			ArrayList<String> ressources = new ArrayList<String>();
			ressources.add("Preserved Pebbles");
			ressources.add("Memory Fragment");
			Carte card = new Carte("Cemetery.png", "", ressources, 0);
			card.addCardState(CardState.ROADSIDE);
			card.addSpawnableMonster("Skeleton", 3);
			return card;
		}
		
		default ->
		throw new IllegalArgumentException("Unexpected value: " + name);
		}
	}
	
	public boolean contains(CardState c) {
		return cardState.contains(c);
	}
	
	public void addDailyStatBoost(String name, int value) {
		dailyStatBoost.put(name, value);
	}
	
	public void addCardState(CardState c) {
		cardState.add(c);
	}
	
	public void addSpawnableMonster(String m, int rate) {
		spawnableMonster.put(m, rate);
	}
	
	public void addBoostedStat(String name, double value) {
		boostedStats.put(name, value);
	}
	
	// Getters :
	
	public String sprite() {
		return sprite;
	}
	
	public String toString() {
		return sprite;
	}
	
	public HashMap<String, Double> boostedStats() {
		return boostedStats;
	}
	
	public HashMap<String, Integer> dailyStatBoost() {
		return dailyStatBoost;
	}
	
	public ArrayList<CardState> cardState() {
		return cardState;
	}
	
	public String ressourceGiven() {
		return ressourceGiven;
	}
	
	public String ressourceGivenWhenCrossed() {
		Random random = new Random();
		int randomIndex = random.nextInt(ressourceGivenWhenCrossed.size());
		return ressourceGivenWhenCrossed.get(randomIndex);
	}
	
	public HashMap<String, Integer> spawnableMonster() {
		return spawnableMonster;
	}
	
}
