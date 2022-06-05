package collectable;

import java.io.Serializable;

public enum CardState implements Serializable { // Represents the three different states (types) a card can have.
	ROAD, 
	ROADSIDE,
	LANDSCAPE;
}
