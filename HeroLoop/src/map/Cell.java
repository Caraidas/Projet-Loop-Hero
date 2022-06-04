package map;

import collectable.Card;
import collectable.CardState;
import collectable.ZoneCard;
import data.GridPosition;
import entities.Monster;


public class Cell {
	private Card card;
	private CardState acceptableCardState;
	private int inZone = 0; // for graphics purposes
	
	public Cell(Card card, CardState acceptableCardState) {
		this.card = card;
		this.acceptableCardState = acceptableCardState;
	}
	
	public Cell() {
		this(null, null);
	}
	
	public void addCardState(CardState c) {
		acceptableCardState = c;
	}
	
	public void spawn(Monster monster) {
		return;
	}
	
	public void addDirection(String s) {
		return;
	}
	
	public void clear() { // clears a cell from the building on it
		addCard(null);
		addToZone(-1);
	}
	
	public void addCard(Card c) {
		card = c;
	}
	
	public boolean hasNoMonsters() {
		return true;
	}
	
	public boolean hasNoBuilding() {
		return card == null;
	}
	
	public void addToZone(int i) {
		inZone += i;
		if (inZone < 0) {
			inZone = 0;
		}
	}
	
	// Getters :
	
	public Card card() {
		return card;
	}
	
	public CardState acceptableCardState() {
		return acceptableCardState;
	}
	
	public String sprite() {
		return card.sprite();
	}
	
	public int inZone() {
		return inZone;
	}
	
}
