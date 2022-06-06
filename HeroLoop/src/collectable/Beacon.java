package collectable;

import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Player;
import inventory.Ressource;
import time.TimeData;

public class Beacon extends ZoneCard {

	private static final long serialVersionUID = -622325475620958931L;

	public Beacon(ArrayList<CardState> cardStates, String sprite, ArrayList<Ressource> ressourcesGiven,
			GridPosition position, ArrayList<GridPosition> zone, HashMap<String, Double> boost,
			ArrayList<String> spawnableMonsters, boolean diagonal, int dimension) {
		super(cardStates, sprite, ressourcesGiven, position, zone, boost, spawnableMonsters, diagonal, dimension);
	}
	
	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		
		if (zone().contains(gameData.map().getPlayerPos(player)) && player.getStat("speed") == 1) {
			player.boostStat("speed", 39.0);
		} else if (!zone().contains(gameData.map().getPlayerPos(player))) {
			player.setStat("speed", 1.0);
		}
	}
}
