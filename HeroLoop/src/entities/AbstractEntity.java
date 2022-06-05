package entities;

import java.util.HashMap;

abstract class AbstractEntity implements Entity {
	private final HashMap<String, Double> basicStats; // all the stat shared by the player and the monsters
	
	private String battleState = "Idle"; // Only for graphics
	
	private final String sprite;

	public AbstractEntity(HashMap<String, Double> basicStats, String sprite) {
		this.basicStats = basicStats;
		this.sprite = sprite;
	}
	
	@Override
	public String battleState() {
		return battleState;
	}
	
	@Override
	public void setBattleState(String s) {
		battleState = s;
	}
	
	// Getters :
	
	public HashMap<String, Double> basicStats() { // return all stats of the entity
		return basicStats;
	}
	
	public String getSprite() { // get the sprite of the entity 
		return sprite;
	}
	
	public double getHp() { // get the HP of the entity
		return basicStats.get("hp"); 
	}
	
	public double getHpMax() { // get the HP max of the entity
		return basicStats.get("hpMax");
	}
	
	public double getLifeSteal() { // get the percentage of vampirism of the entity 
		return basicStats.get("vampirism");
	}
	
}
