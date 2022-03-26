package entities;

import data.Range;
import inventory.Inventory;

public class Player extends AbstractEntity{ // TODO : extends
	private int position;
	private Inventory inventory;
	
	private int hpMax;
	private double hp;
	private Range damage;
	
	// 0, inventory, 250, 250, (4, 6), 0, 0, 0, 0;
	public Player(int position, Inventory inventory, int hpMax, double hp, Range damage, double def, double regen, int damageToAll, 
			int vampirism, int evasivness) {
		
		super(def, regen, damageToAll, vampirism, evasivness, "ressources/Entities-Sprite/Player.png");
		this.position = position;
		this.inventory = inventory;
	}
	
	public int position() {
		return position;
	}
	
	public void updatePosition() {
		position++;
		if (position >= 34) {
			position = 0;
		}
	}
	
	
}
