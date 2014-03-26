package com.epam.sin.waste;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class SinSumCollableTest {

	private final int LENGTH_OF_SEQUENCE = 5_000_000;
	private final double DELTA = 0.0001;

	@Test
	public void testTimeToCountIn1Thread() {
		int numberOfThreads = 1;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn2Thread() {
		int numberOfThreads = 2;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn3Thread() {
		int numberOfThreads = 3;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn4Thread() {
		int numberOfThreads = 4;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn5Thread() {
		int numberOfThreads = 5;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn6Thread() {
		int numberOfThreads = 6;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn7Thread() {
		int numberOfThreads = 7;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
	
	@Test
	public void testTimeToCountIn8Thread() {
		int numberOfThreads = 8;
		SinSeries sum = new SinSeries(LENGTH_OF_SEQUENCE, numberOfThreads);
		double sumOfSeries = sum.countSeriesCollable();
		assertEquals(0.0, sumOfSeries, DELTA);
	}
}
