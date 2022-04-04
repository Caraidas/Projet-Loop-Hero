package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Monster extends AbstractEntity {
	private final double baseStrength;
	private final int ressourceChance;
	private final ArrayList<String> dropableRessources;

	public Monster(HashMap<String, Double> basicStats, ArrayList<String> dropableRessources, double baseStrength, int ressourceChance, String sprite) {	
		super(basicStats, sprite);
		this.baseStrength = baseStrength;
		this.ressourceChance = ressourceChance;
		this.dropableRessources = dropableRessources;
	}
	
	// Getter :
	
	public int ressourceChance() {
		return ressourceChance;
	}
	
	public ArrayList<String> dropableRessources() {
		return dropableRessources;
	}

}
