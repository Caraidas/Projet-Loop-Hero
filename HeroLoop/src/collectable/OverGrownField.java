package collectable;

import java.util.ArrayList;

import data.GameData;
import entities.Monster;
import map.Cell;
import map.RoadCell;

public class OverGrownField extends SpawnCard {

	public OverGrownField(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
	}
	
	@Override
	public void spawn(int i, int j, GameData gameData, int day) {	
		Cell c = move(gameData, i, j);
		
		if (c instanceof RoadCell) {
			((RoadCell)c).fill(Monster.createMonster(super.spawnableMonster(), gameData.getLoopCount()));
		}
	}

}
