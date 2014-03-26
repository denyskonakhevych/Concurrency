package com.epam.sin.sum.count.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.epam.sin.sum.count.SinSeriesCounter;
import com.epam.sin.sum.count.SinSeriesCounterRunnable;

public class SinSeriesCounterRunnableTest {

	private final int LENGTH_OF_SEQUENCE = 1_000_000;
	private final double DELTA = 0.0001;
	
	@Test(expected = IllegalArgumentException.class)
	public void testTimeToCountIn0Thread() {
		int numberOfThreads = 0;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		sum.countSeries();
	}
	
	@Test
	public void testTimeToCountIn1Thread() {
		int numberOfThreads = 1;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn2Thread() {
		int numberOfThreads = 2;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn3Thread() {
		int numberOfThreads = 3;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn4Thread() {
		int numberOfThreads = 4;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn5Thread() {
		int numberOfThreads = 5;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn6Thread() {
		int numberOfThreads = 6;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn7Thread() {
		int numberOfThreads = 7;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testTimeToCountIn8Thread() {
		int numberOfThreads = 8;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testCount1ThreadPer1ElementOk() {
		final int CURRENT_LENGTH_OF_SEQUENCE = 1_000;
		int numberOfThreads = CURRENT_LENGTH_OF_SEQUENCE * 2 + 1;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(CURRENT_LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNumberOfThreadsMoreThanNumberOfElements() {
		final int CURRENT_LENGTH_OF_SEQUENCE = 1_000;
		int numberOfThreads = CURRENT_LENGTH_OF_SEQUENCE * 2 + 2;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(CURRENT_LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testCount1Element() {
		int numberOfThreads = 1;
		SinSeriesCounter sum = new SinSeriesCounterRunnable(0, numberOfThreads);
		assertEquals(0.0, sum.countSeries(), DELTA);
	}
	
	@Test
	public void testCountSequenceStartNegativeOrPositiveSumsEquals() {
		int numberOfThreads = 2;
		SinSeriesCounter sumPlus = new SinSeriesCounterRunnable(LENGTH_OF_SEQUENCE, numberOfThreads);
		SinSeriesCounter sumMinus = new SinSeriesCounterRunnable(-LENGTH_OF_SEQUENCE, numberOfThreads);
		assertEquals(sumPlus.countSeries(), sumMinus.countSeries(), DELTA);
	}
}