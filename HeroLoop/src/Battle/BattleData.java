package Battle;

import java.util.Random;

import data.GameData;
import entities.Entity;
import entities.Monster;
import entities.Player;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class BattleData { // this class takes care of all the battle related operations.
	private final GameData gameData;
	private final TimeData timeData;
	
	public BattleData(GameData gameData, TimeData timeData) {
		this.gameData = gameData;
		this.timeData = timeData;
	}
	
	public void startBattle(Cell c, Player player) {
		if (((RoadCell)c).getEntities().size() != 0) { // Checks if there are monster to fight
			for (Monster monster : ((RoadCell)c).getEntities()) {
				dealDamage(player, monster);
				loot(monster, player);
			}
			
			timeData.addTime(2000);
		}
	}
	
	public void dealDamage(Entity victim, Entity attacker) {
		victim.takeDamage(6);
	}
	
	public void loot(Monster monster, Player player) {
		
		Random rand = new Random();
		
		int n = rand.nextInt(100);
		
		if (n < monster.ressourceChance()) {
			for (String ressource : monster.dropableRessources()) {
				player.addRessource(ressource, 3);
			}		
		} else {	
			player.addCard(gameData.drawCard(), 1); 
		}
	}
}
