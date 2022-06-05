package collectable;

import java.io.Serializable;
import java.util.ArrayList;

import data.GameData;
import map.Cell;
import map.RoadCell;

public class OverGrownField extends SpawnCard implements Serializable {

	private static final long serialVersionUID = 6269005917746217007L;

	public OverGrownField(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
	}
	
	public void fill(int i, int j, GameData gameData) { // fill the fight with FieldOfBlades
		Cell c = move(gameData, i, j);
		if (c instanceof RoadCell) {
			((RoadCell)c).fill("FieldOfBlades", gameData.getLoopCount());
		}
	}

}
