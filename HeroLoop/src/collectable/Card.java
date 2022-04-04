package collectable;

import java.util.ArrayList;
import java.util.HashMap;
import entities.Monster;

public class Card { 
	private final ArrayList<CardState> cardState; // A card can be ROAD, LANSCAPE and / or ROADSIDE
	private final HashMap<Monster, Integer> spawnableMonsters; // Monster is the monster that can spawn, Integer is the rate of spawn in days
	private final int moveWhenspawn; // the distance the monster moves when it spawns
	
	private final HashMap<String, Double> boostedStats; // The stats that the card boosts upon being placed
	private final HashMap<String, Integer> dailyStatBoost; // The stats that the card boosts every day
	
	private final String ressourceGiven; // ressources given when the card is placed
	private final String ressourceGivenWhenCrossed; // ressources given when the tile is crossed
	private final String sprite; 
	
	public Card(String sprite, String ressourceGiven, String ressourceGivenWhenCrossed, int moveWhenSpawn) {
		this.sprite = sprite;
		this.ressourceGiven = ressourceGiven;
		this.ressourceGivenWhenCrossed = ressourceGivenWhenCrossed;
		this.moveWhenspawn = moveWhenSpawn;
		
		this.cardState = new ArrayList<>();
		this.spawnableMonsters = new HashMap<>();
		this.boostedStats = new HashMap<>();
		this.dailyStatBoost = new HashMap<>();
	}
	
	public static Card createCard(String name) { // this method is used to easily create cards
		switch (name) {
		case "Rock" -> {
			Card card = new Card("Rock.png", "Pebble", "", 0);
			card.addCardState(CardState.LANDSCAPE);
			card.addBoostedStat("hpMax", 0.01);
			return card;
		}
		
		case "Grove" -> {
			Card card = new Card("Grove.png", "", "Stick", 1);
			card.addCardState(CardState.ROAD);
			return card;
		}
		
		case "Meadow" -> {
			Card card = new Card("Meadow.png", "Ration", "", 0);
			card.addCardState(CardState.LANDSCAPE);
			card.addDailyStatBoost("hp", 2);
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
	
	public void addSpawnableMonster(Monster m, int rate) {
		spawnableMonsters.put(m, rate);
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
		return ressourceGivenWhenCrossed;
	}
	
	public HashMap<Monster, Integer> spawnableMonsters() {
		return spawnableMonsters;
	}
	
}
