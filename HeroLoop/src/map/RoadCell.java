package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import collectable.Card;
import collectable.CardState;
import collectable.ZoneCard;
import entities.Entity;
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
	public void spawn(Monster monster) { // vampire bug a regler
		if (monster.getSprite().equals("Vampire")) {
			if (entitiesOn.size() <= 4 && containsMonster("Vampire") == false) {
				entitiesOn.add(monster);
			}
		} else {
			if (entitiesOn.size() < 4) {
				entitiesOn.add(monster);
			}
		}
	}
	
	public boolean containsMonster(String s) {
		for (Monster m : entitiesOn) {
			if (s.equals(m.getSprite())) {
				return true;
			}
		}
		return false;
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
		if (card() instanceof ZoneCard) {
			HashMap<String, Double> boost = ((ZoneCard)card()).boost();
			for (String stat : boost.keySet()) {
				for (Entity m : getEntities()) {
					m.boostStat(stat, -boost.get(stat));
				}
			}
		}
		entitiesOn.clear();
		this.addCard(null);
		this.setZone(false);
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
