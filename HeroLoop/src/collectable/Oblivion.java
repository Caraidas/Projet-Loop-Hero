package collectable;

import java.io.Serializable;
import java.util.ArrayList;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import time.TimeData;

/*
 * remove a placed tile on the map
 */

public class Oblivion extends AbstractCard implements Serializable {

	private static final long serialVersionUID = 5974450512998092940L;

	public Oblivion(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven) {
		super(cardStates, sprite, ressourcesGiven);
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) { // effect of oblivion
		Cell c = gameData.map().getCell(pos);
		System.out.println(c.card());
		
		if (c.card() instanceof ZoneCard) {
			ZoneCard card = ((ZoneCard)c.card());
			for (GridPosition g : card.zone()) {
				System.out.println(g);
				gameData.map().getCell(g).addToZone(-1);
			}
		}
		
		if (c.card() instanceof Village) {
			((Village)c.card()).destroyFields(gameData, pos);
		}
		
		c.clear();
	}
	
	@Override 
	public boolean acceptCardState(GridPosition pos, GameData gameData) { // check if oblivion can be placed on a cell
		Cell c = gameData.map().getCell(pos);
		if (c.hasNoBuilding() && c.hasNoMonsters()) {
			return false;
		}
		return true;
	}

}
