package collectable;

import java.util.ArrayList;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class Oblivion extends AbstractCard {

	public Oblivion(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven) {
		super(cardStates, sprite, ressourcesGiven);
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {
		return;
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		gameData.map().getCell(pos).clear();
	}
	
	@Override 
	public boolean acceptCardState(Cell c) {
		if (c.hasNoBuilding() && c.hasNoMonsters()) {
			return false;
		}
		return true;
	}

}
