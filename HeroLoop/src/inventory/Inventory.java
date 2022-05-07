package inventory;

import collectable.Item;

public class Inventory { 
	private Item armor;
	private Item shield;
	private Item weapon;
	// private Item ring;
	// private Item amulet;
	
	public Inventory() {
		this.armor = null;
		this.weapon = null;
		this.shield = null;
		// this.ring = null;
		// this.amulet = null;
	}
	
	public void setWeapon(Item item) {
		weapon = item;
	}
	
	public void setArmor(Item item) {
		armor = item;
	}
	
	public void setShield(Item item) {
		shield = item;
	}
	
	public boolean hasWeapon() {
		return !(weapon == null);
	}
	
	public boolean hasArmor() {
		return !(armor == null);
	}
	
	public boolean hasShield() {
		return !(shield == null);
	}
	
	// Getters :
	
	public Item weapon() {
		return weapon;
	}
	
	public Item armor() {
		return armor;
	}
	
	public Item shield() {
		return shield;
	}

}
