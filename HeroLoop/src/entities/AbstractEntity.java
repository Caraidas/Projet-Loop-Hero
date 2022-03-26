package entities;


abstract class AbstractEntity implements Entity {
	private double def;
	private double regen;
	private int damageToAll;
	private int vampirism;
	private int evasivness;
	private int hpMax;
	protected double hp;
	
	private final String sprite;

	public AbstractEntity(int hpMax, double hp, double def, double regen, int damageToAll, int vampirism, int evasivness,
			String sprite) {
		
		this.hp = hp;
		this.hpMax = hpMax;
		this.def = def;
		this.regen = regen;
		this.damageToAll = damageToAll;
		this.vampirism = vampirism;
		this.evasivness = evasivness;
		this.sprite = sprite;
	}
	
	@Override 
	public void takeDamage(int damage) {
		hp -= damage;
	}

	public String getSprite() {
		return sprite;
	}
	
}
