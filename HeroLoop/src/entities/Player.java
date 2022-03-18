package entities;

import data.GridPosition;

public class Player {
	private GridPosition position;
	private GridPosition lastPosition;
	
	
	public Player(GridPosition position, GridPosition lastPosition) {
		super();
		this.position = position;
		this.lastPosition = lastPosition;
	}
}
