package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import collectable.Card;
import collectable.Item;
import data.Range;
import inventory.Deck;
import inventory.Inventory;

public class Player extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3365884907282642497L;
	private int position;
	private final HashMap<String, Integer> ressources;
	private Inventory inventory; 
	private final Deck deck;
	private final ArrayList<Item> items;
	
	private Range damage;
	
	// for vilage quest
	private int accomplishedQuest = 0;
	
	// for score
	private final HashMap<String, Integer> ressourcePoint = new HashMap<>();

	/*
	 * Creation of the Player that contains:
	 * 	- His first position on the road
	 * 	- An HashMap of his resources
	 * 	- An inventory that contains his item like sword, shield etc.
	 * 	- A deck for cards
	 * 	- A list of Item equipped (Sword, shield, etc.)
	 * 	- An HashMap of statistics
	 * 	- A range for his damages
	 * 	- And unique statistic of the player (statistic that cannot be used for a monster
	 */	
	private Player(int position, HashMap<String, Integer> ressources, Inventory inventory, Deck deck, ArrayList<Item> items, HashMap<String, Double> basicStats, Range damage) {
		
		super(basicStats, "Player");
		this.position = position;
		this.inventory = inventory;
		this.deck = deck;
		this.items = items;
		
		for (int i = 0; i < 12; i++) {
			items.add(null);
		}
		
		// stats that monsters can't have but player can
		this.ressources = ressources;
		this.damage = damage;
		
		addCard(Card.createCard("Rock"));
		addCard(Card.createCard("Meadow"));
		addCard(Card.createCard("Grove"));
		addCard(Card.createCard("Grove"));
		addCard(Card.createCard("Oblivion"));
		addCard(Card.createCard("Ruins"));
		addCard(Card.createCard("SpiderCocoon"));
		addCard(Card.createCard("Cemetery"));
		addCard(Card.createCard("VampireMansion"));
		addCard(Card.createCard("Battlefield"));
		addCard(Card.createCard("WheatFields"));
		addCard(Card.createCard("Village"));
		addCard(Card.createCard("Beacon"));
		
		// ressourcePoint.put(); a faire
	}
	
	public Player(int position, HashMap<String, Double> basicStats, Range damage, int pureDamage, int counter, double regen, int damageToAll, double speed) {
		this(position, new HashMap<>(), new Inventory(), new Deck(), new ArrayList<>(), basicStats, damage);
		// Set all the stats of the player
		this.addStat("hp", 200);
		this.addStat("hpMax", 250);
		this.addStat("def", 0);
		this.addStat("pureDamage", pureDamage);
		this.addStat("counter", counter);
		this.addStat("vampirism", 0);
		this.addStat("evade", 0);
		this.addStat("regen", regen);
		this.addStat("damageToAll", damageToAll);
		this.addStat("speed", speed);
	}
	
	public Card selectedCard(int selected) { //return the card selected by the user
		return deck.getCard(selected);
	}
	
	public void updatePosition() { // move the player by one on the loop
		position++;
		if (position >= 34) {
			position = 0;
		}
	}
	
	public void addCard(Card c) { // Add a card to the deck n times
		deck.add(c);
	}
	
	
	public void addRessource(String s, int n) { // adds n resources in the players "bag"
		if (ressources.containsKey(s)) {
			ressources.replace(s, ressources.get(s) + n);
		} else {
			ressources.put(s, n);
		}
	}
	
	public void addItemInInventory(Item item) { // add item to the item's list
		if (items.size() == 12) {
			items.remove(0);
		}
		items.add(item);
	}
	
	public Card selectCard(int i) { // get the card with his position in the deck's list
		return deck.getCard(i);
	}
	
	public Item selectItem(int i) { // get the item with his position in the item's list
		return items.get(i);
	}
	
	public void equipItem(int itemIndex) {
		
		Item item = items.get(itemIndex);
		
		if (item.isArmor()) {	
			if (!(inventory.armor() == null)) {
				for (String stat : inventory().armor().stats().keySet()) {	
					boostStat(stat, -(inventory.armor().stats().get(stat))); // We get rid of the stats of the item that was here before
					
					if (getStat(stat) < 0) {
						basicStats().put(stat, 0.0);
					}
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
						if (getStat(stat) < 0) {
							basicStats().put(stat, 0.0);
						}
					}
				}
			}
			inventory.setWeapon(items.get(itemIndex));
			
		} else if (item.isShield()) {
			if (!(inventory.shield() == null)) {
				for (String stat : inventory().shield().stats().keySet()) {	
					boostStat(stat, -(inventory.shield().stats().get(stat))); // We get rid of the stats of the item that was here before
					
					if (getStat(stat) < 0) {
						basicStats().put(stat, 0.0);
					}
				}
			}
			inventory.setShield(item);
			
		} else if (item.isRing()) {
			if (!(inventory.ring() == null)) {
				for (String stat : inventory().ring().stats().keySet()) {	
					boostStat(stat, -(inventory.ring().stats().get(stat))); // We get rid of the stats of the item that was here before
					
					if (getStat(stat) < 0) {
						basicStats().put(stat, 0.0);
					}
				}
			}
			inventory.setRing(item);
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
	
	public void destroyItem(int i) { // delete the surplus (the last item) of the inventory
		for (int j = i; j >= 0; j--) {
			if (j == 0) {
				items.set(j, null);
			} else {
				items.set(j, items.get(j - 1));
			}
		}
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
	
	public int deckSize() { // get the deck size
		return deck.size();
	}
	
	public int position() { // get the position of the player
		return position;
	}
	
	public Deck deck() { // get the deck of the player
		return deck;
	}
	
	public HashMap<String, Integer> ressources() { // get the resources of the player
		return ressources;
	}
	
	public Inventory inventory() { // get the inventory of the player
		return inventory;
	}
	
	public ArrayList<Item> items() { // get the item of the player
		return items;
	}

	public boolean accomplishedQuest() {
		return accomplishedQuest > 0;
	}

	public void addAccomplishedQuest(int accomplishedQuest) {
		this.accomplishedQuest += accomplishedQuest;
	}
	

}
