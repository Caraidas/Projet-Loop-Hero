package collectable;

import java.io.Serializable;
import java.util.ArrayList;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class OverGrownField extends SpawnCard implements Serializable {

	private static final long serialVersionUID = 6269005917746217007L;

	public OverGrownField(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) { // fill the fight with FieldOfBlades
		Cell c = move(gameData, pos.line(), pos.column());
		if (c instanceof RoadCell) {
			((RoadCell)c).fill("FieldOfBlades", gameData.getLoopCount());
		}
	}

}
