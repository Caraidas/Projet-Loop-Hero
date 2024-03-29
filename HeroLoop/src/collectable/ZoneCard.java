package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import data.GameData;
import data.GridPosition;
import entities.Monster;
import entities.Player;
import inventory.Ressource;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class ZoneCard extends AbstractCard implements Serializable {

	private static final long serialVersionUID = 6408565026952707721L;
	private GridPosition position;
	private final ArrayList<GridPosition> zone;
	private final HashMap<String, Double> boost;
	private final ArrayList<String> spawnableMonsters;
	private final boolean diagonal;
	private final int dimension;

	public ZoneCard(ArrayList<CardState> cardStates, String sprite, ArrayList<Ressource> ressourcesGiven, GridPosition position, 
			ArrayList<GridPosition> zone, HashMap<String, Double> boost, ArrayList<String> spawnableMonsters, boolean diagonal, 
			int dimension) {
		super(cardStates, sprite, ressourcesGiven);
		this.position = position;
		this.zone = zone;
		this.boost = boost;
		this.spawnableMonsters = spawnableMonsters;
		this.diagonal = diagonal;
		this.dimension = dimension;
	}

	@Override
	public void cardAction(Player player, GameData gameData, TimeData timeData, GridPosition pos) {
		
		for (GridPosition g : zone) {
			Cell c = gameData.map().getCell(g);
			if (c instanceof RoadCell) {
				for (String stat : boost.keySet()) {
					for (Monster m : ((RoadCell)c).getEntities()) {
						if (stat.equals("vampirism") && m.getStat(stat) == 0) {
							m.boostStat(stat, boost.get(stat));
						}
					}
				}
			}
		}
	}

	@Override
	public void spawn(int i, int j, GameData gameData, int day, String quest) {
		for (GridPosition g : zone) {
			if (gameData.map().getCell(g) instanceof RoadCell && ((RoadCell)gameData.map().getCell(g)).getEntities().size() != 0) {
				for (String m : spawnableMonsters) {
					gameData.map().getCell(g).spawn(Monster.createMonster(m, gameData.getLoopCount(), quest));
				}
			}
		}
	}
	
	public void setPosition(GridPosition g, GameData gameData) {
		System.out.println(g);
		position = g;
		setZone(gameData);
	}
	
	public void setZone(GameData gameData) {
		for (int i = -dimension; i <= dimension; i++) {
			for (int j = -dimension; j <= dimension; j++) {
				GridPosition pos = new GridPosition(position.line() + i, position.column() + j);
				if (gameData.map().isValid(pos.line(), pos.column())) {
					if ((i == j || -i == j)) {
						if (diagonal) {
							zone.add(pos);
							gameData.map().getCell(pos).setInZone(true);
						}
					} else {
						zone.add(pos);
						gameData.map().getCell(pos).setInZone(true);
					}
				}
			}
		}
		System.out.println(zone);
	}
	
	// Getters
	
	public HashMap<String, Double> boost() {
		return boost;
	}
	
	public ArrayList<String> spawnableMonsters() {
		return spawnableMonsters;
	}
	
	public ArrayList<GridPosition> zone() {
		return zone;
	}
	
	public int dimension() {
		return dimension;
	}
	
	public boolean diagonal() {
		return diagonal;
	}
	
	public GridPosition position() {
		return position;
	}

}
