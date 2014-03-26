package com.epam.sin.waste;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SinSeriesTest {

	private final int LENGTH_OF_SEQUENCE = 5_000_000;
	private final double DELTA = 0.0001;
	private static Map<Integer, Long> results = new HashMap<>();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTimeToCountIn1Thread() {
		int numberOfThreads = 1;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn2Threads() {
		int numberOfThreads = 2;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn3Threads() {
		int numberOfThreads = 3;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn4Threads() {
		int numberOfThreads = 4;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn5Threads() {
		int numberOfThreads = 5;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn6Threads() {
		int numberOfThreads = 6;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn7Threads() {
		int numberOfThreads = 7;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn8Threads() {
		int numberOfThreads = 8;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		long start = System.currentTimeMillis();
		double sumOfSeries = sum.countSeriesRunnable();
		long end = System.currentTimeMillis();
		results.put(numberOfThreads, end - start);
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Iterator<Entry<Integer, Long>> it = results.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry<Integer, Long> pair = (Map.Entry<Integer, Long>)it.next();
	        System.out.format("%d thread - %d millis \n", pair.getKey(), pair.getValue());
		}
	}
}
