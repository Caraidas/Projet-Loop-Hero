package Battle;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import collectable.Item;
import data.GameData;
import entities.Entity;
import entities.Monster;
import entities.Player;
import graphics.View;
import inventory.Ressource;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class BattleData { // this class takes care of all the battle related operations.

	private final GameData gameData;
	private final TimeData timeData;
	private final View view;
	
	public BattleData(GameData gameData, TimeData timeData, View view) {
		Objects.requireNonNull(gameData);
		Objects.requireNonNull(timeData);
		Objects.requireNonNull(view);		
		this.gameData = gameData;
		this.timeData = timeData;
		this.view = view;
	}
	
	public void startBattle(Cell c, Player player) { 
		Objects.requireNonNull(c);
		Objects.requireNonNull(player);
		
		int hits = 0;
		ArrayList<Monster> monsters = ((RoadCell) (c)).getEntities(); 
		int size = monsters.size();
		Random rand = new Random();
		if (!((RoadCell)c).hasNoMonsters()) { // Checks if there are monster to fight
			timeData.accelerateTime(1);
			gameData.switchGameState();
			view.drawScreen();
			
			int n = rand.nextInt(size);
			Monster target = ((RoadCell)c).getEntities().get(n);
			
			while (!(player.isDead()) && size != 0) { // while all monsters and the player are alive
				waitSeconds(1); // time of each actions during a fight 
				dealDamage(target, player, c);
				hits++;
				view.drawScreen();
				
				if (target.isDead()) {
					
					if (target.getQuestMonster().equals("Quest")) {
						player.addAccomplishedQuest(1);
					}
					
					loot(target, player);
					((RoadCell)c).getEntities().remove(n);
					if (target.revive()) // see if the monster can revive
						if (target.getSprite().equals("Ghost")) // if the monster is a ghost than do spawn a ghost of a ghost
							((RoadCell)c).spawn(Monster.createMonster("GhostOfAGhost", gameData.getLoopCount(), ""));
						else if(target.getSprite().equals("GhostOfAGhost")) // if the monster is a ghost than do spawn a Prime matter
							((RoadCell)c).spawn(Monster.createMonster("PrimeMatter", gameData.getLoopCount(), ""));
						else // do spawn a ghost if the monster revive
							((RoadCell)c).spawn(Monster.createMonster("Ghost", gameData.getLoopCount(), ""));
					view.drawScreen();				
					size = ((RoadCell)c).getEntities().size();
					
					if (size == 0) {
						break;
					} 
					
					n = rand.nextInt(size);
					target = ((RoadCell)c).getEntities().get(n);	
				}
				
				for (int i = 0; i < ((RoadCell)c).getEntities().size(); i++) {
					Monster m = ((RoadCell)c).getEntities().get(i);
					if (m.isDead()) {
						((RoadCell)c).getEntities().remove(i);
					} else {
						waitSeconds(1);
						dealDamage(player, m, c);
						hits++;
						view.drawScreen();
					}
					m.setBattleState("Idle");
				}
			}
			
			gameData.switchGameState();
			timeData.addTime(hits * 1000);
			player.setBattleState("Idle");
		}
	}
	
	public void dealDamage(Entity victim, Entity attacker, Cell c) {
		Objects.requireNonNull(victim);
		Objects.requireNonNull(attacker);
		Objects.requireNonNull(c);
		/*
		 * method dealDamage that take in parameter two entity (the attacker and the victim) and the actual cell
		 */
		double base;
		Random rand = new Random();
		int veski = rand.nextInt(100);
		int gounter = rand.nextInt(100);
		if (attacker instanceof Monster) {
			
			if (veski > victim.getStat("evade")) { // if the player do not evade
				
				if (gounter > ((Player)victim).getStat("counter")) { // if the player do not counter
					
					attacker.lifeSteal(attacker.getStat("vampirism"), (int)((Monster)attacker).strength());
					base = ((Monster)attacker).strength();
					int lvl = gameData.getLoopCount();
					double damage = base * lvl * 0.95 * (1 + (lvl - 1) * 0.02); // calculation of the damage deal to the monster
					victim.takeDamage((int)(damage - victim.basicStats().get("def")));
					attacker.setBattleState("Attack");
				}
				else {
					dealDamage(attacker, victim, c);
					attacker.setBattleState("Attack");
					victim.setBattleState("Counter");
					System.out.println("oh le gounter mama");
				}
			}
			else {
				System.out.println("ouaaaah la veski du turfu 1");
				victim.setBattleState("Evade");
				attacker.setBattleState("Attack");
			}
			
		} else if (veski > victim.getStat("evade")){ // if the monster do not evade
			
			for (Monster m : ((RoadCell)c).getEntities()) {
				m.takeDamage((int)attacker.getStat("damageToAll"));
			}
			
			attacker.lifeSteal(attacker.getStat("vampirism"), (int)((Player)attacker).damage());
			System.out.println(attacker + " lifeSteal : " + attacker.getStat("vampirism"));
			victim.takeDamage((int)((((Player)attacker).damage() - victim.basicStats().get("def")) + ((Player)attacker).getStat("pureDamage")));
			attacker.setBattleState("Attack");
			return;
			
		} else {
			System.out.println("ouaaaah la veski du turfu 2");
			victim.setBattleState("Evade");
			attacker.setBattleState("Attack");
		}
		
	}
	
	private void waitSeconds(int s) {
		Objects.requireNonNull(s);
		try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void loot(Monster monster, Player player) {
		Objects.requireNonNull(monster);
		Objects.requireNonNull(player);
		
		Random rand = new Random();
		
		for (Ressource ressource : monster.dropableRessources()) { // Resource loots
			player.addRessource(ressource, 3);
		}
		
		int n = rand.nextInt(100);
		
		if (n <= monster.ressourceChance()) {
			Item item = new Item();
			item.setStats(gameData.getLoopCount()); // add an item drop from the monster to the inventory of the player
			player.addItemInInventory(item);
		} else {
			player.addCard(gameData.drawCard()); // add a card to the deck player
		}
	}
}
