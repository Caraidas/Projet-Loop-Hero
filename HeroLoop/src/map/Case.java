package map;

import java.awt.Color;
import java.util.ArrayList;

import Cards.Card;
import entities.Entity;
import fr.umlv.zen5.ApplicationContext;

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
