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
		case "Skeleton" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Pitful Remains");
			Monster skeleton = new Monster(new HashMap<>(), dropableRessources, 9, 85, "Skeleton.png");
			skeleton.addStat("hp", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("hpMax", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("def", 0);
			return skeleton;
		}
		case "Spider" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster spider = new Monster(new HashMap<>(), dropableRessources, 3.1, 45, "Spider.png");
			spider.addStat("hp", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("hpMax", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("def", 0);
			return spider;
		}
		case "Chest" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Stable Branches");		
			dropableRessources.add("Craft Fragment");
			Monster chest = new Monster(new HashMap<>(), dropableRessources, 0, 100, "Chest.png");
			chest.addStat("hp", (int)(11 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			chest.addStat("hpMax", (int)(11 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			chest.addStat("def", 0);
			return chest;
		}
		case "Mimic" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Stable Branches");		
			dropableRessources.add("Living Fabric");
			Monster mimic = new Monster(new HashMap<>(), dropableRessources, 7, 100, "Mimic.png");
			mimic.addStat("hp", (int)(26 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			mimic.addStat("hpMax", (int)(26 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			mimic.addStat("def", 0);
			return mimic;
		}
		case "Scorch Worm" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster scorchWorm = new Monster(new HashMap<>(), dropableRessources, 2.7, 85, "ScorchWorm.png");
			scorchWorm.addStat("hp", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("hpMax", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("def", 0);
			return scorchWorm;
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
