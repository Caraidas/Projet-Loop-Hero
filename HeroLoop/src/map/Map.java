package map;

import java.util.ArrayList;

import data.GridPosition;
import entities.Monster;
import entities.Player;

public class Map {
	private final Cell map[][];
	private final ArrayList<GridPosition> loop = new ArrayList<>();

	private Map(Cell[][] map) {
		this.map = map;
	}
	
	public Map() {
		this(new Cell[12][21]);
	}
	
	public int lines() {
		return map.length;
	}
	
	public int columns() {
		return map[0].length;
	}
	
	public ArrayList<GridPosition> loop() {
		return loop;
	}
	
	public Cell getCase(int i, int j) {
		return map[i][j];
	}
	
	public Cell getCase(GridPosition g) {
		return this.getCase(g.column(), g.line());
	}
	
	public GridPosition getPlayerPos(Player p) {
		return loop.get(p.position());
	}
	
	public void generateLoop() {
		
		for (int i = 2; i <= 11; i++) { 
			loop.add(new GridPosition(i, 2));
		}
		
		for (int i = 3; i <= 10; i++) {
			loop.add(new GridPosition(11, i));
		}
		
		for (int i = 10; i >= 2; i--) {
			loop.add(new GridPosition(i, 10));
		}
		
		for (int i = 9; i >= 3; i--) {
			loop.add(new GridPosition(2, i));
		}
	}
	
	public void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new Cell();
			}	
		}
		
		for (GridPosition pos : loop) {
			map[pos.column()][pos.line()] = new RoadCell();
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				s.append(map[i][j].toString());
				s.append(", ");
			}
			s.append("\n");
		}
		return s.toString();
	}
}
