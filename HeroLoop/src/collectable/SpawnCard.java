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
		int factor;
		
		if (n < 1) {
			factor = 1;
		} else {
			factor = -1;
		}
		
		if (gameData.map().getCell(i, j + 1) instanceof RoadCell) {
			return gameData.map().getCell(i, j + factor);
			
		} else if (gameData.map().getCell(i, j - factor) instanceof RoadCell) {
			return gameData.map().getCell(i, j - factor);
			
		} else if (gameData.map().getCell(i + factor, j) instanceof RoadCell) {
			return gameData.map().getCell(i + factor, j);
			
		} else if (gameData.map().getCell(i - factor, j) instanceof RoadCell) {
			return gameData.map().getCell(i - factor, j);
		} else {
			return gameData.map().getCell(i, j);
		}
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {	
		Cell c = move(gameData, i, j);
		
		if (day % spawnFrequency == birthday) {
			c.spawn(Monster.createMonster(spawnableMonster, gameData.getLoopCount()));
		}
	}
	
	public void setBirthday(int b) {
		birthday = b;
	}

}
