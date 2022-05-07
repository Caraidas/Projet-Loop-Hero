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
	
	public static Monster createMonster(String name, int lvl) {
		switch (name) {
		case "RatWolf" -> {
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster m = new Monster(new HashMap<>(), dropableRessources, 3.6, 40, "RatWolf.png");
			m.addStat("hp", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			m.addStat("hpMax", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			m.addStat("def", 0);		
			return m;
		}
		case "Slime" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Shapeless Mass");
			dropableRessources.add("Craft Fragment");
			Monster slime = new Monster(new HashMap<>(), dropableRessources, 3.3, 35, "Slime.png");
			slime.addStat("hp", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("hpMax", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("def", 0);
			return slime;
		}
		
		default ->
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
	}
	
	// Getter :
	
	public int ressourceChance() {
		return ressourceChance;
	}
	
	public ArrayList<String> dropableRessources() {
		return dropableRessources;
	}
	
	public double strength() {
		return baseStrength;
	}

}
