package map;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import Cards.Card;
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
	
	public void draw(ApplicationContext context, int i, int j) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.red);
			graphics.fillRect(i * 50, j * 50, 20, 20);
		});
	}
}
