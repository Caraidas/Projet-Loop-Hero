package map;

import Cards.Card;
import entities.Monster;


public class Cell {
	private Card card;
	
	public Cell(Card card) {
		this.card = card;
	}
	
	public Cell() {
		this(null);
	}
	
	public void spawn(Monster monster) {
		return;
	}
	
	public void clear() {
		return;
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
