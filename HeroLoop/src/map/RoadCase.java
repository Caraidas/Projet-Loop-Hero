package map;

import Cards.Card;

public class RoadCase extends Case {
	private boolean playerOn;
	
	public RoadCase(boolean playerOn, Card card) {
		super(card);
		this.playerOn = playerOn;
	}
	
	public RoadCase(boolean playerOn) {
		super();
		this.playerOn = playerOn;
	}
	
	public RoadCase() {
		this(false);
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
