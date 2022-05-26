package entities;

import java.util.ArrayList;
import java.util.HashMap;

import collectable.Card;
import collectable.Item;
import data.Range;
import inventory.Deck;
import inventory.Inventory;

public class Player extends AbstractEntity {
	private int position;
	private final HashMap<String, Integer> ressources;
	private Inventory inventory; 
	private final Deck deck;
	private final ArrayList<Item> items;
	
	private Range damage;
	private int pureDamage;
	private int counter;
	private double regen;
	private int damageToAll;

	private Player(int position, HashMap<String, Integer> ressources, Inventory inventory, Deck deck, ArrayList<Item> items, HashMap<String, Double> basicStats, Range damage, int pureDamage, int counter, double regen, int damageToAll) {
		
		super(basicStats, "Player.png");
		this.position = position;
		this.inventory = inventory;
		this.deck = deck;
		this.items = items;
		
		for (int i = 0; i < 12; i++) {
			items.add(null);
		}
		
		this.ressources = ressources;
		this.damage = damage;
		this.pureDamage = pureDamage;
		this.counter = counter;
		this.regen = regen;
	}
	
	public Player(int position, HashMap<String, Double> basicStats, Range damage, int pureDamage, int counter, double regen, int damageToAll) {
		this(position, new HashMap<>(), new Inventory(), new Deck(), new ArrayList<>(), basicStats, damage, pureDamage, counter, regen, damageToAll);
		this.addStat("hp", 200);
		this.addStat("hpMax", 250);
		this.addStat("def", 0);
		this.addStat("pureDamage", pureDamage);
		this.addStat("counter", counter);
		this.addStat("vampirism", 0);
		this.addStat("evade", 0);
		this.addStat("regen", regen);
		this.addStat("damageToAll", damageToAll);
	}
	
	public Card selectedCard(int selected) {
		return deck.getCard(selected);
	}
	
	public void updatePosition() { // move the player by one on the loop
		position++;
		if (position >= 34) {
			position = 0;
		}
	}
	
	public void addCard(Card c, int n) { // Add a card to the deck n times
		for (int i = 0; i < n; i++) {
			deck.add(c);
		}	
	}
	
	public int deckSize() {
		return deck.size();
	}
	
	public boolean isDead() {
		if (super.getHp() <= 0) {
			return true;
		}
		
		return false;
	}
	
	public void addRessource(String s, int n) { // adds n ressources in the players "bag"
		if (ressources.containsKey(s)) {
			ressources.replace(s, ressources.get(s) + n);
		} else {
			ressources.put(s, n);
		}
	}
	
	public void addItemInInventory(Item item) {
		if (items.size() == 12) {
			items.remove(0);
		}
		items.add(item);
	}
	
	public Card selectCard(int i) {
		return deck.getCard(i);
	}
	
	public Item selectItem(int i) {
		return items.get(i);
	}
	
	public void boostStat(Card c) {
		for (String stat : c.boostedStats().keySet()) {
			boostStat(stat, c.boostedStats().get(stat));
		}
	}
	
	public void dailyStatBoost(Card c) {
		for (String stat : c.dailyStatBoost().keySet()) {
			boostStat(stat, (double)c.dailyStatBoost().get(stat));
			
			this.heal(0);
		}
	}
	
	public void giveRessourcesWhenCrossed(Card c) {
		if (c != null) {
			if (c.ressourceGivenWhenCrossed() != "") {
				if (ressources.containsKey(c.ressourceGivenWhenCrossed())) {
					ressources.replace(c.ressourceGivenWhenCrossed(), ressources.get(c.ressourceGivenWhenCrossed()) + 1);
				} else {
					ressources.put(c.ressourceGivenWhenCrossed(), 1);
				}
			}
		}
	}
	
	public void giveRessource(Card c) {
		if (c != null) {
			if (c.ressourceGiven() != "") {
				if (ressources.containsKey(c.ressourceGiven())) {
					ressources.replace(c.ressourceGiven(), ressources.get(c.ressourceGiven()) + 1);
				} else {
					ressources.put(c.ressourceGiven(), 1);
				}
			}	
		}
	}
	
	public void equipItem(int itemIndex) {
		
		Item item = items.get(itemIndex);
		
		if (item.isArmor()) {	
			if (!(inventory.armor() == null)) {
				for (String stat : inventory().armor().stats().keySet()) {	
					boostStat(stat, -(inventory.armor().stats().get(stat))); // We get rid of the stats of the item that was here before
				}
			}	
			inventory.setArmor(item);
			
		} else if (item.isWeapon()) {	
			if (!(inventory.weapon() == null)) {
				for (String stat : inventory().weapon().stats().keySet()) {	
					if (stat.equals("damage")) {
						damage.boost(-(inventory.weapon().stats().get("damage"))); // We get rid of the stats of the item that was here 
					} else {
						boostStat(stat, -(inventory.weapon().stats().get(stat))); // We get rid of the stats of the item that was here before
					}
				}
			}
			inventory.setWeapon(items.get(itemIndex));
			
		} else if (item.isShield()) {
			if (!(inventory.shield() == null)) {
				for (String stat : inventory().shield().stats().keySet()) {	
					boostStat(stat, -(inventory.shield().stats().get(stat))); // We get rid of the stats of the item that was here before
				}
			}
			inventory.setShield(item);
		}
		
		for (String stat : item.stats().keySet()) {
			
			if (stat.equals("damage")) {
				damage.boost(item.stats().get(stat));
			} else {
				this.boostStat(stat, item.stats().get(stat));
			}
		}
		
		destroyItem(itemIndex);
	}
	
	public void destroyItem(int i) {
		items.set(i, null);
 	}
	
	public int damage() {
		return damage.damage();
	}
	
	public String damageString() {
		return damage.toString();
	}
	
	public void clearInventory() {
		for (int i = 0; i < 12; i++) {
			items.set(i, null);
		}
	}
	
	// Getters :
	
	public int position() {
		return position;
	}
	
	public Deck deck() {
		return deck;
	}
	
	public HashMap<String, Integer> ressources() {
		return ressources;
	}
	
	public Inventory inventory() {
		return inventory;
	}
	
	public ArrayList<Item> items() {
		return items;
	}
	
	public int pureDamage() {
		return pureDamage;
	}
	
	public int counter() {
		return counter;
	}
	

}
