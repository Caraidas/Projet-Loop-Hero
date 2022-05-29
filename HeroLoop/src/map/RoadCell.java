package map;

import java.util.ArrayList;
import java.util.Random;
import collectable.Card;
import collectable.CardState;
import entities.Monster;

public class RoadCell extends Cell {
	private final ArrayList<Monster> entitiesOn;
	private String direction;
	
	public RoadCell(Card card, ArrayList<Monster> entitiesOn, String direction) {
		super(card, CardState.ROAD);
		this.entitiesOn = entitiesOn;
		this.direction = direction;
	}
	
	public RoadCell() {
		super();
		this.entitiesOn = new ArrayList<>();
		this.direction = "0";
	}
	
	@Override
	public void addDirection(String s) {
		direction = s;
	}
	
	@Override
	public void spawn(Monster monster) {
		if (entitiesOn.size() <= 4) {
			entitiesOn.add(monster);
		} 
		
		// else if (monster.getSprite().equals("Vampire")) {
			//entitiesOn.add(monster);
		//}
	}
	
	public void spawn(int dayCount, int loopCount) {
		if (this.card() == null) {
			Random rand = new Random();
			
			int n = rand.nextInt(100);
			
			if (n < 5) { // 5% chance
				spawn(Monster.createMonster("Slime", loopCount));
			}
		}
	}
	
	
	@Override
	public void clear() {
		entitiesOn.clear();
		this.addCard(null);
	}
	
	@Override
	public boolean hasNoMonsters() {
		return entitiesOn.isEmpty();
	}
	
	// Getters :
	
	public ArrayList<Monster> getEntities() {
		return entitiesOn;
	}
	
	public String direction() {
		return direction;
	}
	
}
