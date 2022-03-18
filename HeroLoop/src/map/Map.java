package map;

import java.util.Arrays;

import fr.umlv.zen5.ApplicationContext;

public class Map {
	private final Case map[][];

	private Map(Case[][] map) {
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
		
		for (int i = 2; i < 10; i++) { // columns
			map[i][2] = new RoadCase();
			map[i][12] = new RoadCase();
		}
		
		for (int i = 2; i < 12; i++) { // lines
			map[2][i] = new RoadCase();
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
	
	public void draw(ApplicationContext context) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j].draw(context, j * 60, i * 60 + 70);
			}
		}
	}
	
	
}
