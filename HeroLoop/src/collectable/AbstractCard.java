package collectable;

import java.util.ArrayList;
import java.util.Objects;

import data.GameData;
import data.GridPosition;
import entities.Player;
import map.Cell;

public abstract class AbstractCard implements Card {

	private static final long serialVersionUID = 3376995241453595011L;
	private final ArrayList<CardState> cardStates;
	private final String sprite;
	private final ArrayList<String> ressourcesGiven;
	
	public AbstractCard(ArrayList<CardState> cardStates, String sprite, ArrayList<String> ressourcesGiven) {
		super();
		this.cardStates = Objects.requireNonNull(cardStates);
		this.sprite = Objects.requireNonNull(sprite);
		this.ressourcesGiven = ressourcesGiven;
	}
	/*
	 *  Abstract class that contains every characters points in common for every cards
	 */
	
	@Override
	public boolean acceptCardState(GridPosition pos, GameData gameData) { // check if the tile can be place somewhere on the map
		Objects.requireNonNull(pos);
		Objects.requireNonNull(gameData);
		System.out.println(pos);
		Cell cell = gameData.map().getCell(pos);
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
	
	// Getter
	
	@Override
	public String sprite() { // get the sprite of the cards
		return sprite;
	}
	
	@Override
	public ArrayList<CardState> cardState() { // get the CardState (ROAD, ROADSIDE,	LANDSCAPE)
		return cardStates;
	}
	
	@Override
	public void giveRessource(Player player) { // get the resources give to the player
		for (String r : ressourcesGiven) {
			player.addRessource(r, 3);
		}
	}
}
