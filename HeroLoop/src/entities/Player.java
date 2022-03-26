package entities;

import data.Range;
import inventory.Inventory;

public class Player extends AbstractEntity {
	private int position;
	private Inventory inventory;
	private Range damage;

	public Player(int position, Inventory inventory, int hpMax, double hp, Range damage, double def, double regen, int damageToAll, 
			int vampirism, int evasivness) {
		
		super(hpMax, hp, def, regen, damageToAll, vampirism, evasivness, "ressources/Entities-Sprite/Player.png");
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
	
	public double getHp() {
		return hp;
	}
	
	
}
