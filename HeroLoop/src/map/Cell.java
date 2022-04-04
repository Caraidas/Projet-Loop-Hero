package map;

import collectable.Card;
import collectable.CardState;
import entities.Monster;


public class Cell {
	private Card card;
	private CardState acceptableCardState;
	
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
	
	public void clear() { // clears a cell from the monster on it
		return;
	}
	
	public void addCard(Card c) {
		card = c;
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
	
}
