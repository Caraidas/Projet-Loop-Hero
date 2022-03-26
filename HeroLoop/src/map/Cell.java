package map;

import java.util.ArrayList;

import Cards.Card;
import entities.Entity;

public class Cell {
	private Card card;
	
	public Cell(Card card) {
		this.card = card;
	}
	
	public Cell() {
		this(null);
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
