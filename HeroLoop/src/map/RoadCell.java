package map;

import java.util.ArrayList;
import java.util.Random;
import collectable.Card;
import collectable.CardState;
import entities.Monster;

public class RoadCell extends Cell {
	private final ArrayList<Monster> entitiesOn;
	
	public RoadCell(Card card, ArrayList<Monster> entitiesOn) {
		super(card, CardState.ROAD);
		this.entitiesOn = entitiesOn;
	}
	
	public RoadCell() {
		super();
		this.entitiesOn = new ArrayList<>();
	}
	
	@Override
	public void spawn(Monster monster) {
		if (entitiesOn.size() != 4) {
			entitiesOn.add(monster);
		}	
	}
	
	public void spawn(int dayCount, int loopCount) {
		if (this.card() == null) {
			Random rand = new Random();
			
			int n = rand.nextInt(100);
			
			if (n < 5) { // 5% chance
				spawn(Monster.createMonster("Slime", loopCount));
			}
		} else {
			for (String m : this.card().spawnableMonster().keySet()) {
				if (dayCount % this.card().spawnableMonster().get(m) == 0) {
					spawn(Monster.createMonster(m, loopCount));
				}
			}
		}
	}
	
	@Override 
	public String sprite() {
		if (super.card() != null) {
			return card().sprite();
		}
		
		return "horizontal-road.png"; // will be replaced by a method that determinates the direction of the road
	}
	
	public void clear() {
		entitiesOn.clear();
	}
	
	// Getters :
	
	public ArrayList<Monster> getEntities() {
		return entitiesOn;
	}
	
}
