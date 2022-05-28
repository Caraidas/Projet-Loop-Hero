package Battle;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import collectable.Item;
import data.GameData;
import entities.Entity;
import entities.Monster;
import entities.Player;
import graphics.View;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class BattleData { // this class takes care of all the battle related operations.
	private final GameData gameData;
	private final TimeData timeData;
	private final View view;
	
	public BattleData(GameData gameData, TimeData timeData, View view) {
		this.gameData = gameData;
		this.timeData = timeData;
		this.view = view;
	}
	
	public void startBattle(Cell c, Player player) { 
		
		int hits = 0;
		int size = ((RoadCell)c).getEntities().size();
		Random rand = new Random();
		if (size != 0) { // Checks if there are monster to fight
			
			gameData.switchGameState();
			view.drawScreen();
			
			int n = rand.nextInt(size);
			Monster target = ((RoadCell)c).getEntities().get(n);
			
			while (!(player.isDead()) && size != 0) {
				
				waitSeconds(1);
				dealDamage(target, player, c, n);
				hits++;
				view.drawScreen();
				
				if (target.isDead()) {
					
					loot(target, player);
					((RoadCell)c).getEntities().remove(n);
					view.drawScreen();					
					size = ((RoadCell)c).getEntities().size();
					
					if (size == 0) {
						break;
					} 
					
					n = rand.nextInt(size);
					target = ((RoadCell)c).getEntities().get(n);	
				}
				
				for (Monster m : ((RoadCell)c).getEntities()) {
					waitSeconds(1);
					dealDamage(player, m, c, -1);
					hits++;
					view.drawScreen();
				}
			}
			
			timeData.addTime(hits * 1000);
			gameData.switchGameState();
		}
	}
	
	public void dealDamage(Entity victim, Entity attacker, Cell c, int mNum) {
		double base;
		Random rand = new Random();
		int veski = rand.nextInt(100);
		int gounter = rand.nextInt(100);
		if (attacker instanceof Monster) {
			
			if (veski > victim.getEvade()) { // si le player esquive pas 
				
				if (gounter > ((Player)victim).counter()) { // si en gros le player il contre pas
					
					attacker.lifeSteal(attacker.getLifeSteal(), (int)((Monster)attacker).strength());
					base = ((Monster)attacker).strength();
					int lvl = gameData.getLoopCount();
					double damage = base * lvl * 0.95 * (1 + (lvl - 1) * 0.02);
					victim.takeDamage((int)(damage - victim.basicStats().get("def")));
				}
				else {
					dealDamage(attacker, victim, c, mNum);
					System.out.println("oh le gounter mama");
				}
			}
			else {
				System.out.println("ouaaaah la veski du turfu");
			}
			
		} else if (veski > victim.getEvade()){ // si le monstre esquive pas 
			
			for (Monster m : ((RoadCell)c).getEntities()) {
				m.takeDamage((int)attacker.getStat("damageToAll"));
			}
			
			attacker.lifeSteal(attacker.getLifeSteal(), (int)((Player)attacker).damage());
			victim.takeDamage((int)((((Player)attacker).damage() - victim.basicStats().get("def")) + ((Player)attacker).pureDamage()));
			return;
			
		} else {
			System.out.println("ouaaaah la veski du turfu");
		}
		
	}
	
	private void waitSeconds(int s) {
		try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void loot(Monster monster, Player player) {
		
		Random rand = new Random();
		
		for (String ressource : monster.dropableRessources()) { // ressource loots
			player.addRessource(ressource, 3);
		}
		
		int n = rand.nextInt(100);
		
		if (n <= monster.ressourceChance()) {
			Item item = new Item();
			item.setStats(gameData.getLoopCount());
			player.addItemInInventory(item);
		} else {
			player.addCard(gameData.drawCard(), 1);
		}
	}
}
