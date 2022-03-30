package collectable;

import java.util.ArrayList;

import data.GridPosition;
import entities.Monster;

public class Card {
	private final ArrayList<CardState> cardState;
	private final ArrayList<Monster> spawnableMonsters;
	private final String sprite;
	
	
	public Card(String sprite) {
		this.sprite = sprite;
		this.cardState = new ArrayList<>();
		this.spawnableMonsters = new ArrayList<>();
	}
	
	public void addCardState(CardState c) {
		cardState.add(c);
	}
	
	public void addSpawnableMonster(Monster m) {
		spawnableMonsters.add(m);
	}
	
	public String sprite() {
		return sprite;
	}
	
}
