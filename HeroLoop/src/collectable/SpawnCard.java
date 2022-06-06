package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import data.GameData;
import data.GridPosition;
import entities.Monster;
import entities.Player;
import inventory.Ressource;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class SpawnCard extends AbstractCard implements Serializable {

	private static final long serialVersionUID = 2249683186183910521L;
	private final int spawnFrequency;
	private final String spawnableMonster;
	private int birthday;
	private final int moveWhenSpawn;
	
	
	public SpawnCard(ArrayList<CardState> cardStates, String sprite, ArrayList<Ressource> ressourcesGiven, int spawnFrequency,
			String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven);
		this.spawnFrequency = spawnFrequency;
		this.spawnableMonster = spawnableMonster;
		this.birthday = birthday;
		this.moveWhenSpawn = moveWhenSpawn;
	}

	/*
	 * Make spawn enemies on the road
	 */

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		giveRessource(player);
	}

	public Cell move(GameData gameData, int i, int j) {		
		if (moveWhenSpawn == 0) {
			return gameData.map().getCell(i, j);
		}
		
		Random rand = new Random();
		int n = rand.nextInt(2);
		
		int[] factors = new int[2];
		factors[0] = -1;
		factors[1] = 1;
		int factor = factors[n];
		
		ArrayList<Cell> possibleSpawnCells = new ArrayList<>();
		
		if (gameData.map().isValid(i, j + factor) && gameData.map().getCell(i, j + factor) instanceof RoadCell) {
			possibleSpawnCells.add(gameData.map().getCell(i, j + factor));
			
		} 
		
		if (gameData.map().isValid(i, j - factor) && gameData.map().getCell(i, j - factor) instanceof RoadCell) {
			possibleSpawnCells.add(gameData.map().getCell(i, j - factor));
			
		}
		
		if (gameData.map().isValid(i + factor, j) && gameData.map().getCell(i + factor, j) instanceof RoadCell) {
			possibleSpawnCells.add(gameData.map().getCell(i + factor, j));
			
		}
		
		if (gameData.map().isValid(i - factor, j) && gameData.map().getCell(i - factor, j) instanceof RoadCell) {
			possibleSpawnCells.add(gameData.map().getCell(i - factor, j));
		}
		
		n = rand.nextInt(possibleSpawnCells.size());
		return possibleSpawnCells.get(n);
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day, String quest) {	
		Cell c = move(gameData, i, j);
		if (quest.equals("Quest")) {
			c.spawn(Monster.createMonster(spawnableMonster, gameData.getLoopCount() + 2, quest));
		} else {
			if ((day - birthday) % spawnFrequency == 0) {
				c.spawn(Monster.createMonster(spawnableMonster, gameData.getLoopCount(), quest));
			}
		}
		
		
	}
	
	public void setBirthday(int b) { // set when the ennemy spawn
		Objects.requireNonNull(b);
		System.out.println("birthday set : " + b);
		birthday = b;
	}
	
	public String spawnableMonster() { // get the spawnable monster;
		return spawnableMonster;
	}

}
