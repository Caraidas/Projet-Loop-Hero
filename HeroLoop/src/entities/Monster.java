package entities;

public class Monster extends AbstractEntity {
	private final double baseStrength;

	public Monster(int hpMax, int hp, double baseStrength, double def, double regen, int damageToAll, int vampirism,
			int evasivness, String sprite) {
		
		super(hpMax, hp, def, regen, damageToAll, vampirism, evasivness, sprite);
		this.baseStrength = baseStrength;
	}

}
