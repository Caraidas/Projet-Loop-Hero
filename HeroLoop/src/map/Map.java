package map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import collectable.CardState;
import data.GridPosition;
import entities.Player;

public class Map implements Serializable {
	
	private static final long serialVersionUID = 1123429668512568060L;
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
		return this.getCell(g.line(), g.column());
	}
	
	public GridPosition getPlayerPos(Player p) {
		return loop.get(p.position());
	}
	
	public boolean isValid(int i, int j) {
		return i >= 0 && i < lines() && j >= 0 && j < columns();
	}
	
	public void generateLoop() throws IOException {
        Random r = new Random();
        int x = r.nextInt(3) + 1;
        
        try(BufferedReader br = new BufferedReader(new FileReader("ressources/loops/loop"+ x +".txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                String[] parts = line.split(",");
                loop.add(new GridPosition(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])));
                sb.append(line);
                line = br.readLine();
            }
        }
    }
	
	public void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new Cell();
			}	
		}
		
		for (GridPosition pos : loop) {
			map[pos.line()][pos.column()] = new RoadCell();
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
		
		for (int k : lst) {
			if (isValid(i, j + k) && map[i][j + k] instanceof RoadCell) {
				return CardState.ROADSIDE; 
			} 
			
			if (isValid(i + k, j) && map[i + k][j] instanceof RoadCell) {
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
