package entities;

import java.util.HashMap;

abstract class AbstractEntity implements Entity {
	private final HashMap<String, Double> basicStats; // all the stat shared by the player and the monsters
	
	private final String sprite;

	public AbstractEntity(HashMap<String, Double> basicStats, String sprite) {
		this.basicStats = basicStats;
		this.sprite = sprite;
	}
	
	@Override 
	public void takeDamage(int damage) {
		basicStats.replace("hp", basicStats.get("hp") - damage);
	}
	
	@Override 
	public void heal(double percentage) { 
		basicStats.replace("hp", basicStats.get("hp") +  basicStats.get("hpMax") * percentage);
		
		if (basicStats.get("hp") >= basicStats.get("hpMax")) {
			basicStats.replace("hp", basicStats.get("hpMax"));
		}
 	}
	
	@Override
	public void addStat(String name, double value) {
		basicStats.put(name, value);
	}
	
	@Override
	public void boostStat(String stat, Double factor) { // for percentage based boosts
		basicStats.replace(stat, basicStats.get(stat) + basicStats.get(stat) * factor);
	}
	
	@Override
	public void boostStat(String stat, int factor) { // for point based boosts
		basicStats.replace(stat, basicStats.get(stat) + factor);
	}
	
	// Getters :
	
	public HashMap<String, Double> basicStats() {
		return basicStats;
	}
	
	public String getSprite() {
		return sprite;
	}
	
	public double getHp() {
		return basicStats.get("hp"); 
	}
	
	public double getHpMax() {
		return basicStats.get("hpMax");
	}
	
}
