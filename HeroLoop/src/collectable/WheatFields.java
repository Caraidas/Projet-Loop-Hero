package collectable;

import java.util.ArrayList;
import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import time.TimeData;

public class WheatFields extends SpawnCard {

	public WheatFields(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
		
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		giveRessource(player);
	}
	
	/*@Override
	public boolean acceptCardState(Cell cell, GameData gameData) {
		if (cell.card() != null) {
			return false;
		}
	
		int lst[] = new int[2];
		lst[0] = -1;
		lst[1] = 1;
	}*/

}
