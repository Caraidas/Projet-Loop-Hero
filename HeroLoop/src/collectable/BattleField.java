package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.RoadCell;
import time.TimeData;

public class BattleField extends ZoneCard implements Serializable {

	private static final long serialVersionUID = 6243177915460869491L;
	private final String card;
	
	public BattleField(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven,
			GridPosition position, ArrayList<GridPosition> zone, HashMap<String, Double> boost,
			ArrayList<String> spawnableMonsters, boolean diagonal, int dimension, String card) {
		super(cardStates, sprite, ressourcesGiven, position, zone, boost, spawnableMonsters, diagonal, dimension);
		this.card = card;
	}
	
	/*
	 * Creation of Battlefield class 
	 */
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) { // Method use for the synergy of battlefield when two Battlefield's zones enter in contact, create a BloodPath on the road
		Objects.requireNonNull(player);
		Objects.requireNonNull(gameData);
		Objects.requireNonNull(timeData);
		Objects.requireNonNull(pos);
		
		super.cardAction(player, gameData, timeData, pos);
		
		for (GridPosition g : super.zone()) {
			if (gameData.map().getCell(g).zones() > 1 && gameData.map().getCell(g) instanceof RoadCell 
					&& gameData.map().getCell(g).card() == null) {
				gameData.map().getCell(g).addCard(Card.createCard(card));
			}
		}
	}
	
	@Override
	public void setZone(GameData gameData) { // set the zone effect around the tile placed
		Objects.requireNonNull(gameData);
		
		for (int i = -super.dimension(); i <= super.dimension(); i++) {
			for (int j = -super.dimension(); j <= super.dimension(); j++) {
				GridPosition pos = new GridPosition(super.position().line() + i, super.position().column() + j);
				if (gameData.map().isValid(pos.line(), pos.column())) {
					if ((i == j || -i == j)) {
						if (super.diagonal()) {
							super.zone().add(pos);
							gameData.map().getCell(pos).addToZone(1);
							gameData.map().getCell(pos).setInZone(true);
						}
					} else {
						super.zone().add(pos);
						gameData.map().getCell(pos).addToZone(1);
						gameData.map().getCell(pos).setInZone(true);
					}
				}
			}
		}
	}
}
