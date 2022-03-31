package map;


import java.util.ArrayList;
import java.util.Random;

import collectable.Card;
import entities.Monster;

public class RoadCell extends Cell {
	private final ArrayList<Monster> entitiesOn;
	
	public RoadCell(Card card, ArrayList<Monster> entitiesOn) {
		super(card);
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
		
		Random rand = new Random();
		
		int n = rand.nextInt(100);
		
		if (n < 5) {
			entitiesOn.add(new Monster(13, 13, 3.3, 0.0, 0.0, 0, 0, 0, "ressources/Entities-Sprite/monsters/Slime.png"));
		}
	}
	
	@Override 
	public String sprite() {
		if (super.card() != null) {
			return card().sprite();
		} 
		
		return "ressources/Map-Sprite/horizontal-road.png";
	}
	
	public void clear() {
		entitiesOn.clear();
	}
	
	public ArrayList<Monster> getEntities() {
		return entitiesOn;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	

}
