package inventory;

import java.util.ArrayList;
import collectable.Card;

public class Deck {
	private final ArrayList<Card> cards;
	
	public Deck() {
		this.cards = new ArrayList<>();
	}
	
	public void add(Card card) {
		if (cards.size() == 13) {
			cards.remove(0);
		}
		cards.add(card);
		
	}
	
	public void remove(int i) {
		cards.remove(i);
	}
	
	public ArrayList<Card> cards() {
		return cards;
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card getCard(int i) {
		return cards.get(i);
	}

}
