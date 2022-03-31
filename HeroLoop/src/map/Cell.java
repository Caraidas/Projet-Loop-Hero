package map;

import java.util.ArrayList;

import collectable.Card;
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
	
	public void addCard(Card c) {
		card = c;
	}
	
	public Card card() {
		return card;
	}
	
	public String sprite() {
		if (card != null) {
			return card.sprite();
		} 
		
		return "ressources/Map-Sprite/normalLand.png";
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
