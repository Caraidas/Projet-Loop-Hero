package entities;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public interface Entity {
	
	HashMap<String, Double> basicStats();
	
	String getSprite();
	
	default double getStat(String s) {
		Objects.requireNonNull(s);
		return basicStats().get(s);
	}
	
	default void takeDamage(int damage) {
		basicStats().replace("hp", basicStats().get("hp") - damage);
		if (damage != 0) {
			this.setBattleState("Hurt");
		} else {
			this.setBattleState("Idle");
		}
	}
	
	default void heal(double percentage) { 
		globalheal(getStat("hpMax") * percentage);
 	}
	
	default void lifeSteal(double percentage, int damage) {
		globalheal(damage * (percentage / 100));
		
		if (getStat("hp") > getStat("hpMax")) {
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
	
	default void globalheal(double value) {
		basicStats().replace("hp", getStat("hp") +  value);
		
		if (getStat("hp") > getStat("hpMax")) {
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
	
	default boolean revive() {
		Random r = new Random();
		int x = r.nextInt(100);
		if (x < getStat("undead")) {
			return true;
		}
		return false;
	}
	
	default boolean isDead() {
		return getStat("hp") <= 0;
	}
	
	default void addStat(String name, double value) {
		basicStats().put(name, value);
	}
	
	String battleState();
	
	void setBattleState(String s);
	
	default void boostStat(String stat, Double factor) {
		if (factor < 1 && factor > -1) {
			basicStats().replace(stat, getStat(stat) + getStat(stat) * factor);
		}
		basicStats().replace(stat, getStat(stat) + factor);
		
		if (getStat("hp") > getStat("hpMax")) {
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
}
