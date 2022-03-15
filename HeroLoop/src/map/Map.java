package map;

import java.util.Arrays;

public class Map {
	private final Case map[][];

	public Map(Case[][] map) {
		this.map = map;
	}
	
	public Map() {
		this(new Case[12][21]);
	}
	
	
	public void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new Case();
			}	
		}
		
		for (int i = 3; i < 9; i++) {
			map[i][4] = new RoadCase();
			map[i][14] = new RoadCase();
		}
		
		for (int i = 4; i < 15; i++) {
			map[3][i] = new RoadCase();
			map[9][i] = new RoadCase();
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
