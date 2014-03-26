package com.epam.sin.sum.count;

import static java.lang.Math.sin;

public class SinSeriesCounterRunnable extends SinSeriesCounter {

	public SinSeriesCounterRunnable(int lengthOfSequence, int numberOfThreads) {
		super(lengthOfSequence, numberOfThreads);
	}
	
	@Override
	public double countSeries() {
		double[] rezults = new double[numberOfThreads];
		Thread[] seriesCounterArray = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			Runnable runnable = new SeriesCounter(borders[i][0], borders[i][1], rezults, i);
			seriesCounterArray[i] = new Thread(runnable);
			seriesCounterArray[i].start();
		}
		double totalSum = 0;
		try {
			for (int i = 0; i < numberOfThreads; i++) {
				seriesCounterArray[i].join();
				totalSum += rezults[i];
			}
		} catch (InterruptedException ex) {
			System.out.println("Error in multithreading." + ex.getMessage());
		}
		return totalSum;
	}
	
	private class SeriesCounter implements Runnable {
		private int startBorder; 
		private int endBorder;
		private double[] rezults;
		private int n;
		
		public SeriesCounter(int startBorder, int endBorder, double[] rezults, int n) {
			this.startBorder = startBorder;
			this.endBorder = endBorder;
			this.rezults = rezults;
			this.n = n;
		}
		
		@Override
		public void run() {
			double tempSumOfSeries = 0;
			for (int i = startBorder; i < endBorder; i++) {
				tempSumOfSeries += sin(i);
			}
			rezults[n] += tempSumOfSeries;
		}
	}
}
