package com.ram.disruptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class SPSCDisruptorTest {
	private static final int UNIT_SIZE = 1024;
	private static final int BUFFER_SIZE = UNIT_SIZE * 1024 * 16;
	private static final int ITERATIONS = BUFFER_SIZE;
	private static final Logger logger = LoggerFactory
			.getLogger(SPSCDisruptorTest.class);

	private static class Data {
		private String data;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "Data [data=" + data + "]";
		}

		public final static EventFactory<Data> DATA_FACTORY = new EventFactory<Data>() {

			@Override
			public Data newInstance() {
				return new Data();
			}

		};
	}

	private static class DataEventTranslator implements EventTranslator<Data> {
		private String payload;

		public DataEventTranslator(String payload) {
			this.payload = payload;
		}

		@Override
		public void translateTo(Data d, long sequence) {
			d.setData(payload);
		}

	};

	public static void main(String[] args) throws InterruptedException {
		new SPSCDisruptorTest().testDisruptor();
		new SPSCDisruptorTest().testExecutor();
	}

	@SuppressWarnings("unchecked")
	public void testDisruptor() {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Disruptor<Data> disruptor = new Disruptor<Data>(
				SPSCDisruptorTest.Data.DATA_FACTORY, BUFFER_SIZE, exec,
				ProducerType.SINGLE, new SleepingWaitStrategy());
		disruptor.handleEventsWith(new EventHandler<Data>() {

			@Override
			public void onEvent(Data data, long sequence, boolean endOfBatch)
					throws Exception {
			}

		});
		long t1 = System.nanoTime();
		RingBuffer<Data> buffer = disruptor.start();
		for (int i = 1; i <= ITERATIONS; i++) {
			buffer.publishEvent(new DataEventTranslator("data" + i));
		}
		logger.info("waiting for shutdown");
		disruptor.shutdown();
		logger.info("Disruptor Time (ms): " + (System.nanoTime() - t1 * 1.0)
				/ 1000);
		logger.info("Disruptor is shutdown");
		exec.shutdown();
	}

	public void testExecutor() throws InterruptedException {
		ExecutorService executor = new ThreadPoolExecutor(1, 1, 0L,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
						BUFFER_SIZE));
		long t1 = System.nanoTime();
		for (int i = 1; i <= ITERATIONS; i++) {
			executor.submit(new DataRunner("data" + i));
		}
		executor.shutdown();
		executor.awaitTermination(5000, TimeUnit.SECONDS);
		logger.info("Executor Time (ms): " + (System.nanoTime() - t1 * 1.0)
				/ 1000);
	}

	private static class DataRunner implements Runnable {
		private String data;

		public DataRunner(String data) {
			this.data = data;
		}

		@Override
		public void run() {
		}

	}
}
