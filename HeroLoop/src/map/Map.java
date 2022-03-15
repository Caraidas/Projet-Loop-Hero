package map;

import java.util.Arrays;

public class Map {
	private final Case map[][];

	public Map(Case[][] map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Map [map=" + Arrays.toString(map) + "]";
	}
	
	
	
}
