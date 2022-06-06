package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import inventory.Ressource;

public class Monster extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6124488898763497165L;
	private double baseStrength;
	private final int ressourceChance;
	private final ArrayList<Ressource> dropableRessources;
	
	private String questMonster;

	public Monster(HashMap<String, Double> basicStats, ArrayList<Ressource> dropableRessources, double baseStrength, int ressourceChance, String sprite, 
			String questMonster) {	
		super(basicStats, sprite);
		this.baseStrength = baseStrength;
		this.ressourceChance = ressourceChance;
		this.dropableRessources = Objects.requireNonNull(dropableRessources);
		this.questMonster = Objects.requireNonNull(questMonster);
	}
	
	public static Monster createMonster(String name, int lvl, String quest) { 
	/*
	 * Create a monster with a switch case and in parameter the String of the monster and the actual level of the loop
	 * the monsters contains a List of dropable ressources with witch we can add ressources that the monster can drop according to the wiki of the game
	 * monster also have an HashMap that contains the stats of the monsters
	 * So with that stats we can initialize their HP and other stats like evade that can be different according to the wiki between monsters
	*/
		switch (name) {
		case "RatWolf" -> {
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Living Fabric", new Ressource("Orb of Evolution", null, -1, 100), 10, 15));
			Monster ratWolf = new Monster(new HashMap<>(), dropableRessources, 3.6, 40, "RatWolf", quest);
			ratWolf.addStat("hp", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ratWolf.addStat("hpMax", (int)(16 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ratWolf.addStat("def", 0);
			ratWolf.addStat("vampirism", 0);
			ratWolf.addStat("evade", 10);
			ratWolf.addStat("undead", 0);
			return ratWolf;
		}
		case "Slime" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("ShapeLess Mass", new Ressource("Orb of Unity", null, -1, 150), 10, 15));
			dropableRessources.add(new Ressource("Craft Fragment", new Ressource("Orb of Craft", null, -1, 100), 10, 10));
			Monster slime = new Monster(new HashMap<>(), dropableRessources, 3.3, 35, "Slime", quest);
			slime.addStat("hp", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("hpMax", (int)(13 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			slime.addStat("def", 0);
			slime.addStat("vampirism", 0);
			slime.addStat("evade", 0);
			slime.addStat("undead", -1);
			return slime;
		}
		case "Skeleton" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));
			Monster skeleton = new Monster(new HashMap<>(), dropableRessources, 9, 85, "Skeleton", quest);
			skeleton.addStat("hp", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("hpMax", (int)(12 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			skeleton.addStat("def", 0);
			skeleton.addStat("vampirism", 0);
			skeleton.addStat("evade", 5);
			skeleton.addStat("undead", 0);
			return skeleton;
		}
		case "Spider" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Living Fabric", new Ressource("Orb of Evolution", null, -1, 100), 10, 15));
			Monster spider = new Monster(new HashMap<>(), dropableRessources, 3.1, 45, "Spider", quest);
			spider.addStat("hp", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("hpMax", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			spider.addStat("def", 0);
			spider.addStat("vampirism", 0);
			spider.addStat("evade", 10);
			spider.addStat("undead", -1);
			return spider;
		}
		case "Chest" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Stable Branches", new Ressource("Stable Wood", null, -1, 25), 12, 3));		
			dropableRessources.add(new Ressource("Craft Fragment", new Ressource("Orb of Craft", null, -1, 100), 10, 10));
			Monster chest = new Monster(new HashMap<>(), dropableRessources, 0, 100, "Chest", quest);
			chest.addStat("hp", (int)(11 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			chest.addStat("hpMax", (int)(11 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			chest.addStat("def", 0);
			chest.addStat("vampirism", 0);
			chest.addStat("evade", 0);
			chest.addStat("undead", -1);
			return chest;
		}
		
		case "Mimic" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Stable Branches", new Ressource("Stable Wood", null, -1, 25), 12, 3));		
			dropableRessources.add(new Ressource("Living Fabric", new Ressource("Orb of Evolution", null, -1, 100), 10, 15));
			Monster mimic = new Monster(new HashMap<>(), dropableRessources, 7, 100, "Mimic", quest);
			mimic.addStat("hp", (int)(26 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			mimic.addStat("hpMax", (int)(26 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			mimic.addStat("def", 0);
			mimic.addStat("vampirism", 0);
			mimic.addStat("evade", 5);
			mimic.addStat("undead", -1);
			return mimic;
		}
		
		case "ScorchWorm" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Living Fabric", new Ressource("Orb of Evolution", null, -1, 100), 10, 15));
			Monster scorchWorm = new Monster(new HashMap<>(), dropableRessources, 2.7, 85, "ScorchWorm", quest);
			scorchWorm.addStat("hp", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("hpMax", (int)(10 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scorchWorm.addStat("def", 0);
			scorchWorm.addStat("vampirism", 0);
			scorchWorm.addStat("evade", 10);
			scorchWorm.addStat("undead", 0);
			return scorchWorm;
		}
		
		case "Scarecrow" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Stable Branches", new Ressource("Stable Wood", null, -1, 25), 12, 3));
			Monster scarecrow = new Monster(new HashMap<>(), dropableRessources, 8.25, 100, "Scarecrow", quest);
			scarecrow.addStat("hp", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scarecrow.addStat("hpMax", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			scarecrow.addStat("def", 0);
			scarecrow.addStat("vampirism", 0);
			scarecrow.addStat("evade", 10);
			scarecrow.addStat("undead", -1);
			return scarecrow;
		}
		
		case "Vampire" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));
			Monster vampire = new Monster(new HashMap<>(), dropableRessources, 5.8, 45, "Vampire", quest);
			vampire.addStat("hp", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			vampire.addStat("hpMax", (int)(18 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			vampire.addStat("def", 0);
			vampire.addStat("vampirism", 0);
			vampire.addStat("evade", 10);
			vampire.addStat("undead", 0);
			return vampire;
		}	
		
		case "Ghost" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));		
			Monster ghost = new Monster(new HashMap<>(), dropableRessources, 3, 0, "Ghost", quest);
			ghost.addStat("hp", (int)(3 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghost.addStat("hpMax", (int)(3 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghost.addStat("def", 0);
			ghost.addStat("vampirism", 0);
			ghost.addStat("evade", 30);
			ghost.addStat("undead", 50.0);
			return ghost;
		}
		
		case "GhostOfAGhost" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));
			dropableRessources.add(new Ressource("ShapeLess Mass", new Ressource("Orb of Unity", null, -1, 150), 10, 15));
			Monster ghostOfAGhost = new Monster(new HashMap<>(), dropableRessources, 4, 0, "GhostOfAGhost", quest);
			ghostOfAGhost.addStat("hp", (int)(6 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghostOfAGhost.addStat("hpMax", (int)(6 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			ghostOfAGhost.addStat("def", 0);
			ghostOfAGhost.addStat("vampirism", 0);
			ghostOfAGhost.addStat("evade", 20);
			ghostOfAGhost.addStat("undead", 50.0);
			return ghostOfAGhost;
		}
		
		case "PrimeMatter" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));
			dropableRessources.add(new Ressource("Time Shard", new Ressource("Astral Orb", null, -1, 100), 10, 15));
			Monster PrimeMatter = new Monster(new HashMap<>(), dropableRessources, 4.5, 0, "PrimeMatter", quest);
			PrimeMatter.addStat("hp", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			PrimeMatter.addStat("hpMax", (int)(8 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			PrimeMatter.addStat("def", 0);
			PrimeMatter.addStat("vampirism", 0);
			PrimeMatter.addStat("evade", 10);
			PrimeMatter.addStat("undead", 0);
			return PrimeMatter;
		}
		
		case "BloodClot" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Pitiful Remains", new Ressource("Orb of AfterLife", null, -1, 100), 10, 10));
			dropableRessources.add(new Ressource("ShapeLess Mass", new Ressource("Orb of Unity", null, -1, 150), 10, 15));
			Monster bloodClot = new Monster(new HashMap<>(), dropableRessources, 4.8, 64, "BloodClot", quest);
			bloodClot.addStat("hp", (int)(15 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			bloodClot.addStat("hpMax", (int)(15 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			bloodClot.addStat("def", 0);
			bloodClot.addStat("vampirism", 0);
			bloodClot.addStat("evade", 20);
			bloodClot.addStat("undead", 0);
			return bloodClot;
		}
		case "FieldOfBlades" ->{
			ArrayList<Ressource> dropableRessources = new ArrayList<>();
			dropableRessources.add(new Ressource("Ration", new Ressource("Food Supply", null, -1, 25), 12, 3));
			dropableRessources.add(new Ressource("Living Fabric", new Ressource("Orb of Evolution", null, -1, 100), 10, 15));
			dropableRessources.add(new Ressource("ShapeLess Mass", new Ressource("Orb of Unity", null, -1, 150), 10, 15));
			Monster fieldOfBlades = new Monster(new HashMap<>(), dropableRessources, 5, 80, "FieldOfBlades", quest);
			fieldOfBlades.addStat("hp", (int)(20 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			fieldOfBlades.addStat("hpMax", (int)(20 * lvl * 0.95 * (1 + (lvl - 1) * 0.02)));
			fieldOfBlades.addStat("def", 0);
			fieldOfBlades.addStat("vampirism", 0);
			fieldOfBlades.addStat("evade", 5);
			fieldOfBlades.addStat("undead", 0);
			return fieldOfBlades;
		}
		
		default ->
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
	}
	
	public void boostQuest() {
		for (String stat : basicStats().keySet()) {
			this.boostStat(stat, basicStats().get(stat) * 1.5);
		}
		
		baseStrength *= 1.5;
		questMonster = "Quest";
	}
	
	// Getter :
	
	public int ressourceChance() { // get the percentage for a monster to drop ressources
		return ressourceChance;
	}
	
	public ArrayList<Ressource> dropableRessources() { // get the ressources that can be drop by a monster
		return dropableRessources;
	}
	
	public double strength() { // get the damages of the monster
		return baseStrength;
	}

	public String getQuestMonster() {
		return questMonster;
	}

}
