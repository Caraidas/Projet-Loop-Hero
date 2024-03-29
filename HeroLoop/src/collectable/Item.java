package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 7677443371031898904L;
	private final String sprite;
	private final String type;
	private HashMap<String, Double> stats = new HashMap<>();
	
	public Item() {		
		Random rand = new Random();
		int n = rand.nextInt(4);
		
		ArrayList<String> lstType = new ArrayList<>();
		lstType.add("Weapon");
		lstType.add("Shield");
		lstType.add("Armor");
		lstType.add("Ring");
	
		this.type = lstType.get(n);
		this.sprite = "ressources/item-Sprite/"+lstType.get(n)+".png";
	}
	
	private int setRarity(int lvl) {
		Objects.requireNonNull(lvl);
		Random rand = new Random();
		int r = rand.nextInt(100);
		/*
		 * give a random rarity to every equipments
		 */
		if (lvl < 3) {
			if (r < 35)
				return 0; // gray
			else if (r < 65)
				return 1; // blue
			else
				return 2; // yellow
			
		} else {
			if (r < 35)
				return 0; // gray
			else if (r < 65)
				return 1; // blue
			else if (r < 85)
				return 2; // yellow 
			
			return 3; // orange
		}
	}
	
	private double setOriginalStat(String type, int rarity, int lvl, ArrayList<String> tab) { // create a new equipment 
		Objects.requireNonNull(type);
		Objects.requireNonNull(rarity);
		Objects.requireNonNull(lvl);
		Objects.requireNonNull(tab);
		double stat = 0;
		Random rand = new Random();
		int n;
		/*
		 * switch case for the creation of an equipment
		 */
		
		switch (type) {
		case "Weapon":
			n = rand.nextInt(2);
			stat = lvl * (4 + n); // between 4 and 6
			stats.put("damage", stat);
			break;
		
		case "Shield":
			stat = 4 * lvl;
			stats.put("def", stat);
			break;
			 
		case "Armor":
			rand = new Random();
			n = rand.nextInt(20);
			stat = lvl * (80 + n); // between 80 and 100
			stats.put("hpMax", stat);
			break;
			
		case "Ring":
			n = rand.nextInt(tab.size());
			stat = ringStat(tab.get(n), lvl);
			stats.put(tab.get(n), stat);
			break;

		default:
			break;
		}
		
		return stat;
	}
	
	private double ringStat(String stat, int lvl) {
		Objects.requireNonNull(stat);
		Objects.requireNonNull(lvl);
		// switch case for the ring random first statistic
		switch (stat) {
		case "def":
			return lvl * 1.5;
			
		case "vampirism":
			return 8 + (lvl - 1) * 1.5;
			
		case "counter":
			return  8 + (lvl - 1) * 4;
			
		case "evade":
			return 8 + (lvl - 1) * 2;
			
		case "regen":
			return lvl * 0.6;
			
		case "pureDamage":
			return 3 * lvl;
			
		case "damageToAll":
			return lvl;
			
		default:
			return 0;
		}

	}
	
	public void setStats(int lvl) {
		Objects.requireNonNull(lvl);
		int rarity = setRarity(lvl);
		Random rand = new Random();
		int n;
		ArrayList<String> tab = new ArrayList<>();
		tab.add("regen");
		tab.add("counter");
		tab.add("pureDamage");
		tab.add("evade");
		tab.add("def");
		tab.add("vampirism");
		tab.add("damageToAll");
		
		double stat = setOriginalStat(type, rarity, lvl, tab);
		// switch case to add statistics in terms of his rarity
		for (int i = 0; i < rarity; i++) {
			n = rand.nextInt(tab.size());
			
			switch (tab.get(n)) {
			case "def":
				stat = 4 * lvl;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
					} else {
						if (i == 2) {
							stat = 4 * (lvl - 2);
						} else {
							stat = stat / 2;
						}
					}
					
				}
			
				if (stats.containsKey("def")) {
					stats.replace("def", stats.get("def") + stat);
				} else {
					stats.put("def", stat);
				}
				break;
				
			case "counter":
				stat = 8 + (lvl - 1) * 4;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
					} else {
						if (i == 2) {
							stat = 8 + ((lvl - 2) - 1) * 4;
						} else {
							stat = stat / 2;
						}
					}
				}
				
				stats.put("counter", stat);
				break;
				
			case "vampirism":
				stat = 8 + (lvl - 1) * 1.5;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
					} else {
						if (i == 2) {
							stat = 8 + ((lvl - 2) - 1) * 1.5;
						} else {
							stat = stat / 2;
						}
					}
				}
					
				stats.put("vampirism", stat);
				break;
				
			case "regen":
				stat = lvl * 0.6;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
					} else {
						if (i == 2) {
							stat = (lvl - 2) * 0.6;
						} else {
							stat = stat / 2;
						}
					}
				}
				
				stats.put("regen", stat);
				break;
				
			case "damageToAll":
				stat = lvl;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
						if (stat < 1) 
							stat = 1;
					} else {
						if (i == 2) 
							stat = lvl - 2;
						else {
							stat = stat / 2;
							if (stat < 1) 
								stat = 1;
						}
					}
				}
				
				stats.put("damageToAll", stat);
				break;
				
			case "evade":
				stat = 8 + (lvl - 1) * 2;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
						if (stat < 1)
							stat = 1;
					} else {
						if (i == 2) {
							stat = 8 + ((lvl - 2) - 1) * 2;
						} else {
							stat = stat / 2;
						}
					}
				}
				
				stats.put("evade", stat);
				break;
				
			case "pureDamage":
				stat = 3 * lvl;
				
				if (type.equals("Ring")) {
					stat = stat / 2;
				} else {
					if (rarity == 1) {
						stat = stat / 3;
						if (stat < 1)
							stat = 1;
					} else {
						if (i == 2) {
							stat = 3 * (lvl - 2);
						} else {
							stat = stat / 2;
						}
					}
				}
				
				stats.put("pureDamage", stat);
				break;

			default:
				break;
			}
		}
	}
	
	public boolean isWeapon() {
		return type.equals("Weapon");
	}
	
	public boolean isArmor() {
		return type.equals("Armor");
	}
	
	public boolean isShield() {
		return type.equals("Shield");
	}
	
	public boolean isRing() {
		return type.equals("Ring");
	}
	
	public static Item debugItem(int lvl) {
		Item item = new Item();
		item.setStats(lvl);
		
		return item;
	}
	
	// getters :
	
	public String sprite() {
		return sprite;
	}
	
	public HashMap<String, Double> stats() {
		return stats;
	}
}
