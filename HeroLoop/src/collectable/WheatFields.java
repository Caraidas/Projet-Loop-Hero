package collectable;

import java.util.ArrayList;
import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import time.TimeData;

public class WheatFields extends SpawnCard {
	private GridPosition villagePos = new GridPosition(0, 0);

	public WheatFields(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
		
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		((Village)gameData.map().getCell(villagePos).card()).addToHealFactor(1);
		
		if (gameData.map().getCell(villagePos).card() instanceof Village != false) {
			gameData.map().getCell(pos).addCard(Card.createCard("OverGrownField"));
		}
	}
	
	@Override
	public boolean acceptCardState(GridPosition pos, GameData gameData) {
		System.out.println(pos);
		Cell cell = gameData.map().getCell(pos);
		if (cell.card() != null) {
			return false;
		}
		
		for (CardState c : super.cardStates()) {
			if (c != cell.acceptableCardState()) {
				return false;
			}
		}
	
		int lst[] = new int[2];
		lst[0] = -1;
		lst[1] = 1;
		
		for (int i : lst) {
			if (gameData.map().isValid(pos.line() + i, pos.column()) && gameData.map().getCell(pos.line() + i, pos.column()).card() instanceof Village) {
				villagePos = new GridPosition(pos.line() + i, pos.column());
				return true;
			}
			
			if (gameData.map().isValid(pos.line(), pos.column() + i) && gameData.map().getCell(pos.line(), pos.column() + i).card() instanceof Village) {
				villagePos = new GridPosition(pos.line(), pos.column() + i);
				return true;
			}
		}
		return false;
	}

}
