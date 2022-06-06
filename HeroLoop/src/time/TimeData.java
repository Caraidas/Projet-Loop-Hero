package time;

import java.io.Serializable;

import entities.Player;

public class TimeData implements Serializable {

	private static final long serialVersionUID = 4586595290628799340L;
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// elapsed time since creation
	private long elapsedPlayer = 0; 	// elapsed time since last Player reset()
	private long elapsedRegen = 0;
	private int dayCount;
	private int lastfactor = 1;
	private boolean stopped;
	public static double DAY_MILLISECONDS = 24_000;
	public static int PLAYER_DELAY = 1500;
	public static int REGEN_DELAY = 1000;
	

	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedTotal += tock - tick;
		elapsedPlayer += tock - tick;
		elapsedRegen += tock - tick;
		tick = tock;
	}
	
	public double timeFraction() {
		if (!stopped) {
			tickTock();
		}
		return (elapsedTotal % DAY_MILLISECONDS) / (double) DAY_MILLISECONDS;
	}
	
	public long elapsedPlayer() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedPlayer;
	}
	
	public void resetElapsedBob() {
		elapsedPlayer = 0;
	}
	
	public long elapsedRegen() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedRegen;
	}
	
	public void resetElapsedRegen() {
		elapsedRegen = 0;
	}
	
	
	public void stop() {
		if (!stopped) {
			tickTock();
			stopped = true;
		}	
	}

	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
	public void timeControl() {
		if (stopped) {
			start();
		} else {
			stop();
		}
	}
	
	public void accelerateTime(int factor) {
		PLAYER_DELAY = 1500 / factor;

		DAY_MILLISECONDS = 24_000/factor;
		REGEN_DELAY = 1000 / factor;
		elapsedTotal = (elapsedTotal * lastfactor)/factor;
		lastfactor = factor;
	}
	
	public boolean dayPassed() {
		if (timeFraction() >= 0.90 && !stopped()) {
			updateDayCount();
			elapsedTotal = 0;
			return true;
		}
		return false;
	}
		
	public void updateDayCount() {
		dayCount++;
	}
	
	public void addTime(int t) {
		for (int i = 1; i <= t; i++) { 
			elapsedTotal += 1;
			dayPassed();
		}
	}
	
	public boolean playerMoved(Player player) {
		return (elapsedPlayer >= (PLAYER_DELAY * (1 / player.getStat("speed"))));
	}
	
	// Getters :
	
	public boolean stopped() {
		return stopped;
	}
	
	public int dayCount() {
		return dayCount;
	}
}
