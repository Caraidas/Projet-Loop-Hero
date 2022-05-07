package data;

import java.util.Random;

public class Range { // Mainly used to represent the damage the player can do (from min to max)
	private int min;
	private int max;
	
	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int damage() {
		Random rand = new Random();
		int n = rand.nextInt(this.size());
		
		return min + n;
	}
	
	private int size() {
		return max - min;
	}
	
	public void boost(int n) {
		min+=n;
		max+=n;
	}
	
	@Override 
	public String toString() {
		return min + " - " + max;
	}
}
