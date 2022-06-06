package collectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import inventory.Ressource;

public class EnteringBoost extends BoostCard implements Serializable {

	private static final long serialVersionUID = 1871123950044164532L;
	
	/*
	 * Class that make some card apply an effect when the player go on it 
	 */
	
	public EnteringBoost(ArrayList<CardState> cardStates, String sprite, ArrayList<Ressource> ressourcesGiven,
			HashMap<String, Double> boost) {
		super(cardStates, sprite, ressourcesGiven, boost);
	}

}
