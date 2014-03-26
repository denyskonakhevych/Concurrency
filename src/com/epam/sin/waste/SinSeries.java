package com.epam.sin.waste;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Deprecated
public class SinSeries {
	
	private volatile double totalSum = 0;
	private double[] rezult;
	private final int[][] borders;
	
	int lengthOfSequence; 
	int numberOfThreads;
	
	public SinSeries(int lengthOfSequence, int numberOfThreads) {
		if (lengthOfSequence < 0) {
			lengthOfSequence = 0 - lengthOfSequence;
		}
		if (numberOfThreads < 1) {
			throw new IllegalArgumentException("Illegal number of threads: " + numberOfThreads);
		}
		if (lengthOfSequence * 2 + 1 < numberOfThreads) {
			throw new IllegalArgumentException("Number of threads is to big for current sequence. Maximum alloved number of threads: " + lengthOfSequence * 2 + 1);		
		}
		this.lengthOfSequence = lengthOfSequence;
		this.numberOfThreads = numberOfThreads;
		borders = getBorders(lengthOfSequence, numberOfThreads);
	}
	
	public double countSeriesRunnable() {
		Thread[] seriesCounterArray = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			Runnable runnable = new SeriesCounter(borders[i][0], borders[i][1]);
			seriesCounterArray[i] = new Thread(runnable);
			seriesCounterArray[i].start();
		}
		try {
			for (int i = 0; i < numberOfThreads; i++) {
				seriesCounterArray[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println("Error in multithreading." + ex.getMessage());
		}
		return totalSum;
	}
	
	public double countSeriesCollable() {
		double sumOfSeries = 0;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<Double>> list = new ArrayList<>();
		for(int i=0; i< numberOfThreads; i++){
			Callable<Double> callable = new SeriesCounterCollable<>(borders[i][0], borders[i][1]);
            Future<Double> future = executor.submit(callable);
            list.add(future);
        }
		for(Future<Double> fut : list){
            try {
            	sumOfSeries += fut.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
		return sumOfSeries;
	}
	
	private int[][] getBorders(int lengthOfSequence, int numberOfThreads) {
		int totalLength = lengthOfSequence + 1 + lengthOfSequence;
		int delta = totalLength / numberOfThreads;
		int[][] borders = new int[numberOfThreads][2];
		for(int i = 0; i < numberOfThreads; i++) {
			borders[i][0] = -lengthOfSequence + (i) * delta;
			borders[i][1] = -lengthOfSequence + (i + 1) * delta;
			if (i == numberOfThreads - 1) {
				borders[i][1] += totalLength % numberOfThreads;
			}
		}
		return borders;
	}
	
	private class SeriesCounter implements Runnable {
		int startBorder; 
		int endBorder;
		
		public SeriesCounter(int startBorder, int endBorder) {
			this.startBorder = startBorder;
			this.endBorder = endBorder;
		}
		
		@Override
		public void run() {
			double tempSumOfSeries = 0;
			for (int i = startBorder; i < endBorder; i++) {
				tempSumOfSeries += sin(i);
			}
			totalSum += tempSumOfSeries;
		}
	}
	
	private class SeriesCounterCollable<Double> implements Callable<Double> {

		int startBorder; 
		int endBorder;
		
		public SeriesCounterCollable(int startBorder, int endBorder) {
			this.startBorder = startBorder;
			this.endBorder = endBorder;
		}

		@Override
		public Double call() throws Exception {
			double tempSumOfSeries = 0;
			for (int i = startBorder; i < endBorder; i++) {
				tempSumOfSeries += sin(i);
			}
			return (Double) new java.lang.Double(tempSumOfSeries);
		}
	}
}
