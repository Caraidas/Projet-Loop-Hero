package collectable;

import java.util.ArrayList;
import java.util.Random;
import data.GameData;
import data.GridPosition;
import entities.Monster;
import entities.Player;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class SpawnCard extends AbstractCard {
	private final int spawnFrequency;
	private final String spawnableMonster;
	private int birthday;
	private final int moveWhenSpawn;
	
	
	public SpawnCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven, int spawnFrequency,
			String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven);
		this.spawnFrequency = spawnFrequency;
		this.spawnableMonster = spawnableMonster;
		this.birthday = birthday;
		this.moveWhenSpawn = moveWhenSpawn;
	}


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
	public void spawn(int i, int j, GameData gameData, int day) {	
		Cell c = move(gameData, i, j);
		
		if ((day - birthday) % spawnFrequency == 0) {
			c.spawn(Monster.createMonster(spawnableMonster, gameData.getLoopCount()));
		}
	}
	
	public void setBirthday(int b) {
		System.out.println("birthday set : " + b);
		birthday = b;
	}

}
