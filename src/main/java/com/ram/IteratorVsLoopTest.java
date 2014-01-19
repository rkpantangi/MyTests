package com.ram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorVsLoopTest {

	public static void main(String[] args) {

		List<String> strList = new ArrayList<>(100);
		for (int i = 1; i <= 1000; i++) {
			strList.add("abc");
		}

		long t1 = System.nanoTime();
		for (int i = 0; i < strList.size(); i++) {
			@SuppressWarnings("unused")
			String s = strList.get(i);
		}
		long t2 = System.nanoTime() - t1;
		System.out.println("Time using index loop (nanos): " + t2);

		long t3 = System.nanoTime();
		Iterator<String> iter = strList.iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("unused")
			String s = iter.next();
		}
		long t4 = System.nanoTime() - t3;
		System.out.println("Time using Iterator (nanos): " + t4);

		double times = Math.round((t4 * 1.0d / t2) * 100);
		times = times / 100;
		System.out.println("Using Iterator is slower than using index loop by "
				+ times + " times");
	}

}
