package entities;

import java.util.HashMap;

public interface Entity {
	
	HashMap<String, Double> basicStats();
	
	String getSprite();
	
	default double getHp() {
		return basicStats().get("hp"); 
	}
	
	default double getHpMax() {
		return basicStats().get("hpMax");
	}
	
	default void takeDamage(int damage) {
		basicStats().replace("hp", basicStats().get("hp") - damage);
	}
	
	default void heal(double percentage) { 
		basicStats().replace("hp", basicStats().get("hp") +  basicStats().get("hpMax") * percentage);
		
		if (basicStats().get("hp") >= basicStats().get("hpMax")) {
			basicStats().replace("hp", basicStats().get("hpMax"));
		}
 	}
	
	default boolean isDead() {
		return basicStats().get("hp") <= 0;
	}
	
	default void addStat(String name, double value) {
		basicStats().put(name, value);
	}
	
	default void boostStat(String stat, Double factor) { // for percentage based boosts
		basicStats().replace(stat, basicStats().get(stat) + basicStats().get(stat) * factor);
	}
	
	default void boostStat(String stat, int factor) { // for point based boosts
		basicStats().replace(stat, basicStats().get(stat) + factor);
	}
}
