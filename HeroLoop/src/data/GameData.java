package data;

import map.Map;
import map.RoadCell;

public class GameData {
	private final Map map;
	private int loopCount = 0;
	
	public GameData(Map map) {
		this.map = map;
	}
	
	public void updateLoopCount() {
		loopCount++;
	}
	
	public int getLoopCount() {
		return loopCount;
	}
	
	public void spawn() {
		for (GridPosition g : map.loop()) {
			if (g != map.loop().get(0)) {
				((RoadCell) map.getCase(g.column(), g.line())).spawn();
			}	
		}
	}
	
	public Map map() {
		return map;
	}
	
	public void generateGameBoard() {
		map.generateLoop();
		map.generateMap();
	}

}
