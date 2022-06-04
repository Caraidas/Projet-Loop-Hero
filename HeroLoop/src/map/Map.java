package map;

import java.util.ArrayList;

import collectable.CardState;
import data.GridPosition;
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
	
	public Cell getCell(int i, int j) {
		return map[i][j];
	}
	
	public Cell getCell(GridPosition g) {
		return this.getCell(g.column(), g.line());
	}
	
	public GridPosition getPlayerPos(Player p) {
		return loop.get(p.position());
	}
	
	public boolean isValid(int i, int j) {
		return i >= 0 && i < lines() && j >= 0 && j < columns();
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
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j].addCardState(determinateCardState(i, j));
				map[i][j].addDirection(determinateDirection(i, j));
			}	
		}
		
	}
	
	private String determinateDirection(int i, int j) {
		if (!(map[i][j] instanceof RoadCell)) {
			return "";
		} 
		
		String s = "";

		if (isValid(i, j + 1) && map[i][j + 1] instanceof RoadCell) {
			s += "Right";
		} 
		
		if (isValid(i, j - 1) && map[i][j - 1] instanceof RoadCell) {
			s += "Left";
		} 
		
		if (isValid(i - 1, j) && map[i - 1][j] instanceof RoadCell) {
			s += "Top";
		}
		
		if (isValid(i + 1, j) && map[i + 1][j] instanceof RoadCell) {
			s += "Bottom";
		}
		return s;
		
	}
	
	private CardState determinateCardState(int i, int j) {
		
		if (map[i][j] instanceof RoadCell) {
			return CardState.ROAD;
		}
		
		ArrayList<Integer> lst = new ArrayList<>();
		lst.add(1);
		lst.add(-1);
		
		if (i == 0 || j == 0 )  {
			lst.remove(1);
		}
		
		if (i == map.length - 1 || j == map[0].length - 1) {
			lst.remove(0);
		}
		
		
		for (int k : lst) {
			if (map[i][j + k] instanceof RoadCell) {
				return CardState.ROADSIDE; 
			} 
			
			if (map[i + k][j] instanceof RoadCell) {
				return CardState.ROADSIDE;
			}
		}
		return CardState.LANDSCAPE;
	}
	
	public Cell playerCell(Player player) {
		return this.getCell(this.getPlayerPos(player));
	}
	
	// Getters :
	
	public ArrayList<GridPosition> loop() {
		return loop;
	}
	
}
