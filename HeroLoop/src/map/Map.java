package map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import collectable.CardState;
import data.GridPosition;
import entities.Player;

public class Map implements Serializable {
	
	private static final long serialVersionUID = 1123429668512568060L;
	private final Cell map[][];
	private final ArrayList<GridPosition> loop = new ArrayList<>();

	private Map(Cell[][] map) { // map constructor
		Objects.requireNonNull(map);
		this.map = map;
	}
	
	public Map() { // Initialization of the map 
		this(new Cell[12][21]);
	}
	
	public int lines() { // get the number of lines
		return map.length;
	}
	
	public int columns() { // get the number of columns
		return map[0].length;
	}
	
	public Cell getCell(int i, int j) { // get a cell from a position gives in parameter
		Objects.requireNonNull(i);
		Objects.requireNonNull(j);
		return map[i][j];
	}
	
	public Cell getCell(GridPosition g) { // get a cell form the map with a GridPosition
		Objects.requireNonNull(g);
		return this.getCell(g.line(), g.column());
	}
	
	public boolean isValid(int i, int j) { // check if the coordinates are in bounds
		Objects.requireNonNull(i);
		Objects.requireNonNull(j);
		return i >= 0 && i < lines() && j >= 0 && j < columns();
	}
	
	public void generateLoop() throws IOException { // generate a loop randomly from already texts files in resources
        Random r = new Random();
        int x = r.nextInt(7) + 1;
        
        try(BufferedReader br = new BufferedReader(new FileReader("ressources/loops/loop"+ x +".txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) { // read the txt
                System.out.println(line);
                String[] parts = line.split(",");
                loop.add(new GridPosition(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]))); // add route on the map
                sb.append(line);
                line = br.readLine();
            }
        }
    }
	
	public void generateMap() { // generate the map
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
	
	private String determinateDirection(int i, int j) { // determinate the direction of a RoadCell 
		if (!(map[i][j] instanceof RoadCell)) {
			return "";
		} 
		
		String s = "";

		if (isValid(i, j + 1) && map[i][j + 1] instanceof RoadCell) { // if the road goes on the right
			s += "Right";
		} 
		
		if (isValid(i, j - 1) && map[i][j - 1] instanceof RoadCell) { // if the road goes on the left
			s += "Left";
		} 
		
		if (isValid(i - 1, j) && map[i - 1][j] instanceof RoadCell) { // if the road goes on the top
			s += "Top";
		}
		
		if (isValid(i + 1, j) && map[i + 1][j] instanceof RoadCell) { // if the road goes on the but
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
	
	public GridPosition getPlayerPos(Player p) { // get the player position with GripPosition
		Objects.requireNonNull(p);
		return loop.get(p.position());
	}
	
	public Cell playerCell(Player player) { // get the cell of the player
		return this.getCell(this.getPlayerPos(player));
	}
	
	// Getters :
	
	public ArrayList<GridPosition> loop() {
		return loop;
	}
	
}
