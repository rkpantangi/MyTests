package com.ram.metrics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;

/**
 * Periodically runs a thread lock detector (using Metrics API) and calls the
 * given Action if it detects a thread lock.
 * 
 * @author Ram
 * 
 */
public class TimerBasedDeadlockDetector {

	private ActionOnDeadlock deadlockAction;
	private int checkFrequencySeconds;
	private final ThreadDeadlockHealthCheck deadlockCheck = new ThreadDeadlockHealthCheck();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public static interface ActionOnDeadlock {
		void action(String message);
	}

	public static class SingleActionDeadlockDetectorBuilder {
		private ActionOnDeadlock deadlockAction;

		private int checkFrequencySeconds = 10;

		public SingleActionDeadlockDetectorBuilder withDeadlockAction(ActionOnDeadlock deadlockAction) {
			this.deadlockAction = deadlockAction;
			return this;
		}

		public SingleActionDeadlockDetectorBuilder withChekFrequencySeconds(int checkFrequencySeconds) {
			this.checkFrequencySeconds = checkFrequencySeconds;
			return this;
		}

		public TimerBasedDeadlockDetector build() {
			if (checkFrequencySeconds <= 0)
				throw new IllegalArgumentException("CheckFrequencySeconds must be > 0");
			TimerBasedDeadlockDetector detector = new TimerBasedDeadlockDetector();
			detector.checkFrequencySeconds = checkFrequencySeconds;
			detector.deadlockAction = deadlockAction;
			detector.start();
			return detector;
		}
	}

	private TimerBasedDeadlockDetector() {
	}

	private void start() {
		try {
			executor.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					Result result = deadlockCheck.execute();
					if (!result.isHealthy() && deadlockAction != null) {
						deadlockAction.action(result.getMessage());
					}
				}

			}, checkFrequencySeconds, checkFrequencySeconds, TimeUnit.SECONDS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
