package map;

import java.util.ArrayList;
import java.util.HashMap;
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
		entitiesOn.add(monster);
	}
	
	public void spawn() {
		
		if (this.card() == null) {
			Random rand = new Random();
			
			int n = rand.nextInt(100);
			
			if (n < 5) { // 5% chance
				ArrayList<String> dropableRessources = new ArrayList<>();
				dropableRessources.add("Shapeless Mass");
				dropableRessources.add("Craft Fragment");
				Monster slime = new Monster(new HashMap<>(), dropableRessources, 3.3, 35, "ressources/Entities-Sprite/monsters/Slime.png");
				slime.addStat("hp", 13);
				slime.addStat("hpMax", 13);
				slime.addStat("def", 0);
				slime.addStat("evasiveness", 0);
				slime.addStat("vampirism", 0);
				entitiesOn.add(slime);
			}
		} else {
			// Spawn an other monster
		}
	}
	
	@Override 
	public String sprite() {
		if (super.card() != null) {
			return card().sprite();
		}
		
		return "horizontal-road.png"; // will be replaced by a method tha determinates the direction of the road
	}
	
	public void clear() {
		entitiesOn.clear();
	}
	
	// Getters :
	
	public ArrayList<Monster> getEntities() {
		return entitiesOn;
	}
	
}
