package entities;

import java.util.HashMap;
import java.util.Objects;

public interface Entity {
	
	HashMap<String, Double> basicStats();
	
	String getSprite();
	
	default double getStat(String s) {
		Objects.requireNonNull(s);
		return basicStats().get(s);
	}
	
	default double getHp() {
		return basicStats().get("hp"); 
	}
	
	default double getHpMax() {
		return basicStats().get("hpMax");
	}
	
	default double getEvade() {
		return basicStats().get("evade");
	}
	
	default double getLifeSteal() {
		return basicStats().get("vampirism");
	}
	
	default void takeDamage(int damage) {
		basicStats().replace("hp", basicStats().get("hp") - damage);
		if (damage != 0) {
			this.setBattleState("Hurt");
		}
	}
	
	default void heal(double percentage) { 
		globalheal(basicStats().get("hpMax") * percentage);
 	}
	
	default void lifeSteal(double percentage, int damage) {
		globalheal(damage * (percentage / 100));
		
		if (basicStats().get("hp") > basicStats().get("hpMax")) {
			basicStats().replace("hp", basicStats().get("hpMax"));
		}
	}
	
	default void globalheal(double value) {
		basicStats().replace("hp", basicStats().get("hp") +  value);
		
		if (basicStats().get("hp") > basicStats().get("hpMax")) {
			basicStats().replace("hp", basicStats().get("hpMax"));
		}
	}
	
	default boolean isDead() {
		return basicStats().get("hp") <= 0;
	}
	
	default void addStat(String name, double value) {
		basicStats().put(name, value);
	}
	
	String battleState();
	
	void setBattleState(String s);
	
	default void boostStat(String stat, Double factor) {
		if (factor < 1 && factor > -1) {
			basicStats().replace(stat, basicStats().get(stat) + basicStats().get(stat) * factor);
		}
		basicStats().replace(stat, basicStats().get(stat) + factor);
		
		if (basicStats().get("hp") > basicStats().get("hpMax")) {
			basicStats().replace("hp", basicStats().get("hpMax"));
		}
	}
}
