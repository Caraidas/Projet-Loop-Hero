package map;

import java.awt.Color;
import java.util.ArrayList;

import Cards.Card;
import entities.Entity;
import fr.umlv.zen5.ApplicationContext;

public class RoadCase extends Case {
	private boolean playerOn;
	private final ArrayList<Entity> entities;
	
	public RoadCase(boolean playerOn, Card card, ArrayList<Entity> entities) {
		super(card);
		this.entities = entities;
		this.playerOn = playerOn;
	}
	
	public RoadCase(boolean playerOn) {
		super();
		this.playerOn = playerOn;
		this.entities = new ArrayList<>();
	}
	
	public RoadCase() {
		this(false);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	public void draw(ApplicationContext context, int i, int j) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.yellow);
			graphics.fillRect(i, j, 60, 60);
			graphics.setColor(Color.black);
			graphics.drawRect(i, j, 60, 60);
		});
	}

}
