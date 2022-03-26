package entities;

public class Monster extends AbstractEntity {
	private final int baseHp;
	private final double baseStrength;
	
	// 13, 3.3, 0, 0, 0, 0, "ressources/Entities-Sprite/monsters/Slime.png"

	public Monster(int baseHp, double baseStrength, double def, double regen, int damageToAll, int vampirism,
			int evasivness, String sprite) {
		
		super(def, regen, damageToAll, vampirism, evasivness, sprite);
		this.baseHp = baseHp;
		this.baseStrength = baseStrength;
	}

}
