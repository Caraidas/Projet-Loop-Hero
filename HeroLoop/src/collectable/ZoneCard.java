package collectable;

import java.util.ArrayList;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;
import time.TimeData;

public class ZoneCard extends AbstractCard {
	private final int position;
	private final ArrayList<GridPosition> zone;

	public ZoneCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven, int position, 
			ArrayList<GridPosition> zone) {
		super(cardStates, sprite, ressourcesGiven);
		this.position = position;
		this.zone = zone;
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		
	}

	@Override
	public void giveRessource(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day) {
		// TODO Auto-generated method stub	
	}

}
