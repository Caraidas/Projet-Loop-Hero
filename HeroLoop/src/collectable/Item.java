package collectable;

import java.util.ArrayList;
import java.util.Random;

public class Item {
	private final String sprite;
	private final String type;
	private int stat = 0;
	
	public Item() {		
		Random rand = new Random();
		int n = rand.nextInt(3);
		
		ArrayList<String> lstType = new ArrayList<>();
		lstType.add("Weapon");
		lstType.add("Shield");
		lstType.add("Armor");
	
		this.type = lstType.get(n);
		this.sprite = "ressources/item-Sprite/"+lstType.get(n)+".png";
	}
	
	public void setStats(int lvl) {
		switch (type) {
		case "Weapon":
			Random rand = new Random();
			int n = rand.nextInt(2);
			stat = lvl * (4 + n); // between 4 and 6
			break;
		
		case "Shield":
			stat = 4 * lvl;
			break;
			
		case "Armor":
			rand = new Random();
			n = rand.nextInt(20);
			stat = lvl * (80 + n); // between 80 and 100
			break;

		default:
			break;
		}
	}
	
	public boolean isWeapon() {
		return type.equals("Weapon");
	}
	
	public boolean isArmor() {
		return type.equals("Armor");
	}
	
	public boolean isShield() {
		return type.equals("Shield");
	}
	
	// getters :
	
	public String sprite() {
		return sprite;
	}
	
	public int stat() {
		return stat;
	}
}
