package data;

import java.util.Objects;
import java.util.Random;

public class Range { // Mainly used to represent the damage the player can do (from min to max)
	private int min;
	private int max;
	
	public Range(int min, int max) { // range of damages of the player
		Objects.requireNonNull(min);
		Objects.requireNonNull(max);
		this.min = min;
		this.max = max;
	}
	
	public int damage() { // return the damages deal with a random between the min value and the max value
		Random rand = new Random();
		int n = rand.nextInt(this.size());
		
		return min + n;
	}
	
	private int size() { // use for the random
		return max - min;
	}
	
	public void boost(double d) { // boost of damages apply by a weapon
		min+=d;
		max+=d;
	}
	
	@Override 
	public String toString() {
		return min + " - " + max;
	}
}
