package entities;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public interface Entity {
	
	HashMap<String, Double> basicStats();
	
	String getSprite();
	
	default double getStat(String s) { // get the stat of the entity with the String enter in parameter and check into the HashMap of the entity
		Objects.requireNonNull(s);
		return basicStats().get(s);
	}
	
	default void takeDamage(int damage) { // apply the damages of the attacker to the opponent
		basicStats().replace("hp", basicStats().get("hp") - damage); // get the actual health point of the target minus the damages apply to him
		if (damage != 0) {
			this.setBattleState("Hurt");
		} else {
			this.setBattleState("Idle");
		}
	}
	
	default void heal(double percentage) { // use the globalheal with percentage (for the fire camp)
		globalheal(getStat("hpMax") * percentage);
 	}
	
	default void lifeSteal(double percentage, int damage) { // heal the attacker with his percentage of lifesteal multiply with his damages deal
		globalheal(damage * (percentage / 100));
		
		if (getStat("hp") > getStat("hpMax")) { // if HP goes up than HPmax then set HP to HPmax
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
	
	default void globalheal(double value) {
		basicStats().replace("hp", getStat("hp") +  value); // get the HP of the entity plus the value enter
		
		if (getStat("hp") > getStat("hpMax")) { // if HP goes up than HPmax then set HP to HPmax
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
	
	default boolean revive() { // get the percentage of the undead of the entity and use a random to see if the entity respawn or not
		Random r = new Random();
		int x = r.nextInt(100);
		if (x < getStat("undead")) {
			return true;
		}
		return false;
	}
	
	default boolean isDead() { // if the HP of the HP of the entity fall to 0 or minus, then return true
		return getStat("hp") <= 0;
	}
	
	default void addStat(String name, double value) { // add a new stat to the entity followed with a String in parameter by his value
		basicStats().put(name, value);
	}
	
	String battleState();
	
	void setBattleState(String s);
	
	default void boostStat(String stat, Double factor) { // transform the value of the statistic in parameter the value in parameter
		if (factor < 1 && factor > -1) {
			basicStats().replace(stat, getStat(stat) + getStat(stat) * factor);
		}
		basicStats().replace(stat, getStat(stat) + factor);
		
		if (getStat("hp") > getStat("hpMax")) {
			basicStats().replace("hp", getStat("hpMax"));
		}
	}
}
