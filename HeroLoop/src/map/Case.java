package map;

import Cards.Card;

public class Case {
	private Card card;
	
	public Case(Card card) {
		this.card = card;
	}
	
	public Case() {
		this(null);
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
