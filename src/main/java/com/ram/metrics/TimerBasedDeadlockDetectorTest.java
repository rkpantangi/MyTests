package com.ram.metrics;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ram.metrics.TimerBasedDeadlockDetector.ActionOnDeadlock;
import com.ram.metrics.TimerBasedDeadlockDetector.SingleActionDeadlockDetectorBuilder;

/**
 * @author Ram
 * 
 */
public class TimerBasedDeadlockDetectorTest {
	private static final Logger logger = LoggerFactory.getLogger(TimerBasedDeadlockDetectorTest.class);

	private static final Object lock1 = new Object();
	private static final Object lock2 = new Object();

	public static void main(String[] args) {
		SingleActionDeadlockDetectorBuilder detectorBuilder = new SingleActionDeadlockDetectorBuilder()
				.withDeadlockAction(new ActionOnDeadlock() {

					@Override
					public void action(String message) {
						logger.error("Deadlock detected: {}{}", SystemUtils.LINE_SEPARATOR, message);
					};

				}).withChekFrequencySeconds(20);
		detectorBuilder.build();
		createDeadlock();
	}

	public static void createDeadlock() {
		new Thread() {
			public void run() {
				try {
					synchronized (lock1) {
						Thread.sleep(1000);
						synchronized (lock2) {

						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					synchronized (lock2) {
						Thread.sleep(1000);
						synchronized (lock1) {

						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
}
