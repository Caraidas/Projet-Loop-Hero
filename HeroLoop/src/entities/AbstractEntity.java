package entities;

import java.util.HashMap;

abstract class AbstractEntity implements Entity {
	private final HashMap<String, Double> basicStats; // all the stat shared by the player and the monsters
	
	private final String sprite;

	public AbstractEntity(HashMap<String, Double> basicStats, String sprite) {
		this.basicStats = basicStats;
		this.sprite = sprite;
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
	
	public double getLifeSteal() {
		return basicStats.get("vampirism");
	}
	
}
