package map;


import java.util.ArrayList;
import java.util.Random;
import Cards.Card;
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
		
		if (entitiesOn.size() < 4) 
		{
			Random rand = new Random();
			int n = rand.nextInt(100);
			
			if (n < 5) {
				entitiesOn.add(monster);
				System.out.println("CA SPAWN !!!!!!!!!!!!!!!!!!!!!!");
			}
			return;
		}
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
