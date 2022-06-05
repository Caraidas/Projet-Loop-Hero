package inventory;

import java.io.Serializable;
import java.util.ArrayList;

import collectable.Card;

public class Deck implements Serializable {

	private static final long serialVersionUID = -3960471687503556044L;
	private final ArrayList<Card> cards;
	
	public Deck() { // create a deck
		this.cards = new ArrayList<>();
	}
	
	public void add(Card card) { // add a card to the deck
		if (cards.size() == 13) {
			cards.remove(0);
		}
		cards.add(card);
		
	}
	
	public void remove(int i) { // remove a card from the deck
		cards.remove(i);
	}
	
	// Getters :
	
	public ArrayList<Card> cards() { // get the cards of the deck
		return cards;
	}
	
	public int size() { // get the size of the deck
		return cards.size();
	}
	
	public Card getCard(int i) {  // get a card from the deck with his position in parameter
		return cards.get(i);
	}

}
