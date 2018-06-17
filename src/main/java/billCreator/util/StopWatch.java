package billCreator.util;

import java.util.Date;

public class StopWatch {
	public enum TimeUnit {
		DAYS(24.0 * 3600.0 * 1000.0), HOURS(3600.0 * 1000), MINUTES(60.0 * 1000.0), SECONDS(1000.0), MILLISECONDS(1.0);
		double value;
		private TimeUnit(double t) {
			value = t;
		}
		public double convert(double t) {
			return t / value;
		}
		public long revert(double t) {
			return (long) (t * value);
		}
	}

	Date startTime;
	long time;


	public StopWatch(double initialTime, TimeUnit timeUnit) {

		this.time = timeUnit.revert(initialTime);
	}

	public void start() {
		startTime = new Date();
	}

	public double getTime(TimeUnit timeUnit) {

		double t = time;
		if (isStarted()) {
			t += new Date().getTime() - startTime.getTime();
		}
		return timeUnit.convert(t);
	}

	public void stop() {
		time += new Date().getTime() - startTime.getTime();
		startTime = null;
	}

	public void reset() {
		time = 0;
		if (isStarted()) {
			startTime = new Date();
		}
	}

	public boolean isStopped() {
		return startTime == null;
	}

	public boolean isStarted() {
		return startTime != null;
	}
}
