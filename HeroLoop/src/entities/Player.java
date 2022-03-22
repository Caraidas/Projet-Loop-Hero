package entities;

import inventory.Inventory;

public class Player { // TODO : extends
	private int position;
	private Inventory inventory;
	
	
	public Player(int position) {
		this.position = position;
	}
	
	public int position() {
		return position;
	}
	
	public void updatePosition() {
		position++;
		if (position >= 34) {
			position = 0;
		}
		System.out.println(position);
	}
}
