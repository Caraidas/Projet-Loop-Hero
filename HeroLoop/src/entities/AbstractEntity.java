package entities;

import data.Range;

abstract class AbstractEntity implements Entity {
	private double def;
	private double regen;
	private int damageToAll;
	private int vampirism;
	private int evasivness;
	
	private final String sprite;

	public AbstractEntity(double def, double regen, int damageToAll, int vampirism, int evasivness, String sprite) {
		
		this.def = def;
		this.regen = regen;
		this.damageToAll = damageToAll;
		this.vampirism = vampirism;
		this.evasivness = evasivness;
		this.sprite = sprite;
	}

	
	public String getSprite() {
		return sprite;
	}
	
}
