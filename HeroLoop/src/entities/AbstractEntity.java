package entities;

import inventory.Inventory;

abstract class AbstractEntity implements Entity {
	private int hpMax;
	private double hp;
	private double minDamage;
	private double maxDamage;
	private double def;
	private int attackSpeed;
	private double regen;
	private int damageToAll;
	private int vampirism;
	private int evasivness;
	
	private final Inventory inventory;

	public AbstractEntity(int hpMax, double hp, double minDamage, double maxDamage, double def, int attackSpeed,
			double regen, int damageToAll, int vampirism, int evasivness, Inventory inventory) {
		this.hpMax = hpMax;
		this.hp = hp;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.def = def;
		this.attackSpeed = attackSpeed;
		this.regen = regen;
		this.damageToAll = damageToAll;
		this.vampirism = vampirism;
		this.evasivness = evasivness;
		this.inventory = inventory;
	}

	
	
	
}
