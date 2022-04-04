package time;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// elapsed time since creation
	private long elapsedPlayer = 0; 	// elapsed time since last Player reset()
	private int dayCount;
	private boolean stopped;
	public static double DAY_MILLISECONDS = 24_000;
	public static int PLAYER_DELAY = 1500;
	

	private void tickTock() {
		long tock = System.currentTimeMillis();
		elapsedTotal += tock - tick;
		elapsedPlayer += tock - tick;
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
	
	public void stop() {
		tickTock();
		stopped = true;
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
		if (factor == 1) {
			PLAYER_DELAY = 1500;
			DAY_MILLISECONDS = 24_000;
		}
		PLAYER_DELAY = 1500 / factor;
		DAY_MILLISECONDS = 24_000 / factor;
	}
	
	public boolean dayPassed() {
		return timeFraction() > 0.95 && !stopped();
	}
	
	public void updateDayCount() {
		dayCount++;
	}
	
	public void addTime(int t) {
		elapsedTotal += t;
	}
	
	// Getters :
	
	public boolean stopped() {
		return stopped;
	}
}
