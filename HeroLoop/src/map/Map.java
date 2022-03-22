package map;

import java.util.ArrayList;

import data.GridPosition;

public class Map {
	private final Case map[][];
	private final ArrayList<GridPosition> loop = new ArrayList<>();

	private Map(Case[][] map) {
		this.map = map;
	}
	
	public Map() {
		this(new Case[12][21]);
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
	
	public Case getCase(int i, int j) {
		return map[i][j];
	}
	
	public void generateLoop() {
		
		for (int i = 2; i < 10; i++) { // columns
			loop.add(new GridPosition(i, 2));
		}
		
		for (int i = 2; i < 12; i++) { // lines
			loop.add(new GridPosition(10, i));
		}
		
		for (int i = 2; i < 10; i++) { // columns
			loop.add(new GridPosition(i, 11));
		}
		
		for (int i = 2; i < 12; i++) { // lines
			loop.add(new GridPosition(2, i));
		}
	}
	
	public void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new Case();
			}	
		}
		
		for (GridPosition pos : loop) {
			map[pos.line()][pos.column()] = new RoadCase(false);
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
