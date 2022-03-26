package map;


import java.util.ArrayList;
import java.util.Random;
import Cards.Card;
import entities.Entity;

public class RoadCase extends Cell {
	private final ArrayList<Entity> entitiesOn;
	
	public RoadCase(Card card, ArrayList<Entity> entitiesOn) {
		super(card);
		this.entitiesOn = entitiesOn;
	}
	
	public RoadCase() {
		super();
		this.entitiesOn = new ArrayList<>();
	}
	
	public void spawn() {
		Random rand = new Random();
		int n = rand.nextInt(100);
		
		if (n < 5) {
			entitiesOn.add(null);
		}
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
