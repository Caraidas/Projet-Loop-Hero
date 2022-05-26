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
			Monster ratWolf = new Monster(new HashMap<>(), dropableRessources, 3.6, 40, "RatWolf.png");
			ratWolf.addStat("hp", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ratWolf.addStat("hpMax", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ratWolf.addStat("def", 0);
			ratWolf.addStat("lifeSteal", 0);
			ratWolf.addStat("evade", 10);
			return ratWolf;
		}
		case "Slime" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Shapeless Mass");
			dropableRessources.add("Craft Fragment");
			Monster slime = new Monster(new HashMap<>(), dropableRessources, 3.3, 35, "Slime.png");
			slime.addStat("hp", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("hpMax", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("def", 0);
			slime.addStat("lifeSteal", 0);
			slime.addStat("evade", 0);
			return slime;
		}
		case "Skeleton" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Pitful Remains");
			Monster skeleton = new Monster(new HashMap<>(), dropableRessources, 9, 85, "Skeleton.png");
			skeleton.addStat("hp", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("hpMax", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("def", 0);
			skeleton.addStat("lifeSteal", 0);
			skeleton.addStat("evade", 5);
			return skeleton;
		}
		case "Spider" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster spider = new Monster(new HashMap<>(), dropableRessources, 3.1, 45, "Spider.png");
			spider.addStat("hp", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("hpMax", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("def", 0);
			spider.addStat("lifeSteal", 0);
			spider.addStat("evade", 10);
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
			chest.addStat("lifeSteal", 0);
			chest.addStat("evade", 0);
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
			mimic.addStat("lifeSteal", 0);
			mimic.addStat("evade", 5);
			return mimic;
		}
		case "Scorch Worm" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster scorchWorm = new Monster(new HashMap<>(), dropableRessources, 2.7, 85, "ScorchWorm.png");
			scorchWorm.addStat("hp", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("hpMax", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("def", 0);
			scorchWorm.addStat("lifeSteal", 0);
			scorchWorm.addStat("evade", 10);
			return scorchWorm;
		}
		case "Scarecrow" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Stable branches");
			dropableRessources.add("Scare away");
			dropableRessources.add("Countermeasure");
			Monster scarecrow = new Monster(new HashMap<>(), dropableRessources, 8.25, 100, "Scarecrow.png");
			scarecrow.addStat("hp", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scarecrow.addStat("hpMax", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scarecrow.addStat("def", 0);
			scarecrow.addStat("lifeSteal", 0);
			scarecrow.addStat("evade", 10);
			return scarecrow;
		}
		case "Vampire" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster vampire = new Monster(new HashMap<>(), dropableRessources, 5.8, 45, "Vampire.png");
			vampire.addStat("hp", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			vampire.addStat("hpMax", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			vampire.addStat("def", 0);
			vampire.addStat("lifeSteal", 0);
			vampire.addStat("evade", 10);
			return vampire;
		}	
		case "Ghost" ->{
			ArrayList<String> dropableRessources = new ArrayList<>();
			dropableRessources.add("Living Fabric");
			Monster ghost = new Monster(new HashMap<>(), dropableRessources, 3, 0, "Ghost.png");
			ghost.addStat("hp", (int)(3 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghost.addStat("hpMax", (int)(3 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghost.addStat("def", 0);
			ghost.addStat("lifeSteal", 0);
			ghost.addStat("evade", 30);
			return ghost;
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
