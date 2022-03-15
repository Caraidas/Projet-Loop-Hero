package map;

import Cards.Card;

public class Case {
	private boolean isFull;
	private Card card;
	private boolean isRoad;
	private boolean playerOn = false;
	
	
	public Case(boolean isFull, Card card, boolean isRoad) {
		this.isFull = isFull;
		this.card = card;
		this.isRoad = isRoad;
	}
	
	/**
	 * @param card la carte posé sur la case null su=i la case est vide
	 */
	public Case(Card card) { // la case par défaut(vide) est une case ne contenant pas de route ou de batiment.
		this(false,card,false);
	}

	@Override
	public String toString() {
		return "";
	}
}
