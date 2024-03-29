package map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import collectable.Card;
import collectable.CardState;
import collectable.ZoneCard;
import entities.Entity;
import entities.Monster;

public class RoadCell extends Cell implements Serializable {

	private static final long serialVersionUID = -8488929168450547343L;
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
			if (monster.getSprite().equals("FieldOfBlades")) {
				entitiesOn.add(monster);
			} else if (entitiesOn.size() < 4) {
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
	
	public void spawn(int dayCount, int loopCount, String quest) {
		if (quest.equals("Quest")) {
			spawn(Monster.createMonster("Slime", loopCount, quest));
		} else {
			if (this.card() == null) {
				Random rand = new Random();
				
				int n = rand.nextInt(100);
				
				if (n < 5) { // 5% chance
					spawn(Monster.createMonster("Slime", loopCount, quest));
				}
			}
		}
		
	}
	
	public void fill(String monster, int lvl) {
		if (entitiesOn.size() != 0) {
			int n = (5 - entitiesOn.size());
			for (int i = 0; i < n; i++) {
				spawn(Monster.createMonster(monster, lvl, ""));
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
		this.addToZone(-1);
	}
	
	public int monstersSize() {
		return entitiesOn.size();
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
