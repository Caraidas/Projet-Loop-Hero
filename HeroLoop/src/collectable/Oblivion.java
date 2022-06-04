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
		Cell c = gameData.map().getCell(pos);
		System.out.println(c.card());
		
		if (c.card() instanceof ZoneCard) {
			ZoneCard card = ((ZoneCard)c.card());
			for (GridPosition g : card.zone()) {
				System.out.println(g);
				gameData.map().getCell(g).addToZone(-1);
			}
		}
		
		c.clear();
	}
	
	@Override 
	public boolean acceptCardState(GridPosition pos, GameData gameData) {
		Cell c = gameData.map().getCell(pos);
		if (c.hasNoBuilding() && c.hasNoMonsters()) {
			return false;
		}
		return true;
	}

}
