package Battle;

import entities.Entity;
import entities.Monster;
import entities.Player;
import map.Cell;
import map.RoadCell;

public class BattleData {
	
	public void startBattle(Cell c, Player player) {
		for (Monster monster : ((RoadCell)c).getEntities()) {
			dealDamage(player, monster);
		}
		System.out.println(player.getHp());
	}
	
	public static void dealDamage(Entity victim, Entity attacker) {
		victim.takeDamage(6);
	}
}
