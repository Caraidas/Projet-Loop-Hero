package collectable;

import java.util.ArrayList;
import java.util.Objects;

import data.GameData;
import entities.Player;
import map.Cell;

public abstract class AbstractCard implements Card {
	private final ArrayList<CardState> cardStates;
	private final String sprite;
	private final ArrayList<String> ressourcesGiven;
	
	public AbstractCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven) {
		super();
		this.cardStates = Objects.requireNonNull(cardStates);
		this.sprite = Objects.requireNonNull(sprite);
		this.ressourcesGiven = ressourcesGiven;
	}
	
	@Override
	public String sprite() {
		return sprite;
	}
	
	@Override
	public ArrayList<CardState> cardState() {
		return cardStates;
	}
	
	public static Card createCard(String name) {
		return null;
	}
	
	@Override
	public void giveRessource(Player player) {
		for (String r : ressourcesGiven) {
			player.addRessource(r, 3);
		}
	}
	
	@Override
	public boolean acceptCardState(Cell cell, GameData gameData) {
		if (cell.card() != null) {
			return false;
		}
		
		for (CardState c : cardStates) {
			if (c != cell.acceptableCardState()) {
				return false;
			}
		}
		return true;
	}
}
