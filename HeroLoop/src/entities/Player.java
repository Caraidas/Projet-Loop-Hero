package entities;

import java.util.ArrayList;

import collectable.Card;
import data.Range;
import inventory.Deck;
import inventory.Inventory;

public class Player extends AbstractEntity {
	private int position;
	private Inventory inventory;
	private final Deck deck;
	private Range damage;

	private Player(int position, Inventory inventory, Deck deck, int hpMax, int hp, Range damage, double def, double regen, int damageToAll, 
			int vampirism, int evasivness) {
		
		super(hpMax, hp, def, regen, damageToAll, vampirism, evasivness, "ressources/Entities-Sprite/Player.png");
		this.position = position;
		this.inventory = inventory;
		this.deck = deck;
	}
	
	public Player(int position, int hpMax, Range damage, double def, double regen, int damageToAll, 
			int vampirism, int evasivness) {
		this(position, new Inventory(), new Deck(), hpMax, hpMax, damage, def, regen, damageToAll, vampirism, evasivness);
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
	
	public void addCard(Card c, int n) {
		for (int i = 0; i < n; i++) {
			deck.add(c);
		}	
	}
	
	public Deck deck() {
		return deck;
	}
	
	public int deckSize() {
		return deck.size();
	}
	
	public int getHp() {
		return super.getHp();
	}
	
	public int getHpMax() {
		return super.getHpMax();
	}
	
	public boolean isDead() {
		if (super.getHp() <= 0) {
			return true;
		}
		
		return false;
	}
	
	
}
