package time;

public class TimeData {
	private long tick = System.currentTimeMillis();
	private long elapsedTotal = 0; 	// elapsed time since creation
	private long elapsedPlayer = 0; 	// elapsed time since last Player reset()
	private boolean stopped;
	public final static double DAY_MILLISECONDS = 20_000;
	public final static int PLAYER_DELAY = 1000;

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
	
	public long elapsedBob() {
		if (stopped) {
			return 0;
		}
		tickTock();
		return elapsedPlayer;
	}
	
	public void resetElapsedBob() {
		elapsedPlayer = 0;
	}

	public boolean stopped() {
		return stopped;
	}
	
	public void stop() {
		tickTock();
		stopped = true;
	}

	public void start() {
		stopped = false;
		tick = System.currentTimeMillis();
	}
	
}
