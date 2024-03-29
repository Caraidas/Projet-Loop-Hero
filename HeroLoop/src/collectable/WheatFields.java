package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import data.GameData;
import data.GridPosition;
import entities.Player;
import inventory.Ressource;
import map.Cell;
import time.TimeData;

public class WheatFields extends SpawnCard implements Serializable {

	private static final long serialVersionUID = 8318679728612632913L;
	private GridPosition villagePos = new GridPosition(0, 0);

	public WheatFields(ArrayList<CardState> cardStates, String sprite, ArrayList<Ressource> ressourcesGiven,
			int spawnFrequency, String spawnableMonster, int birthday, int moveWhenSpawn) {
		super(cardStates, sprite, ressourcesGiven, spawnFrequency, spawnableMonster, birthday, moveWhenSpawn);
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		((Village)gameData.map().getCell(villagePos).card()).addToHealFactor(1);
		
		System.out.println(gameData.map().getCell(villagePos).card());
		System.out.println(villagePos);
		if (gameData.map().getCell(villagePos).card() instanceof Village == false) {
			System.out.println("in if");
			gameData.map().getCell(pos).addCard(Card.createCard("OverGrownField"));
		}
	}
	
	@Override
	public boolean acceptCardState(GridPosition pos, GameData gameData) {
		Cell cell = gameData.map().getCell(pos); 
		if (cell.card() != null) {
			return false;
		}
		
		for (CardState c : super.cardState()) {
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
