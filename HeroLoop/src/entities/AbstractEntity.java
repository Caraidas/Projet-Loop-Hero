package entities;

import data.Range;

abstract class AbstractEntity implements Entity {
	private int hpMax;
	private double hp;
	private Range damage;
	private double def;
	private double regen;
	private int damageToAll;
	private int vampirism;
	private int evasivness;


	public AbstractEntity(int hpMax, double hp, Range damage, double def,
			double regen, int damageToAll, int vampirism, int evasivness) {
		this.hpMax = hpMax;
		this.hp = hp;
		this.damage = damage;
		this.def = def;
		this.regen = regen;
		this.damageToAll = damageToAll;
		this.vampirism = vampirism;
		this.evasivness = evasivness;
	}

	
	
	
}
