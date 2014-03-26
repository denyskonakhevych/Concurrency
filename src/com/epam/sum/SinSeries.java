package com.epam.sum;
import static java.lang.Math.sin;

public class SinSeries {
	
	volatile double totalSum = 0;

	public static void main(String[] args) throws InterruptedException {
		int lengthOfSequence = 10;
		int numberOfThreads = 10;
		SinSeries sum = new SinSeries();
		int[][] borders = sum.getBorders(lengthOfSequence, numberOfThreads);
		
		Thread[] seriesCounterArray = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			Runnable runnable = sum.new SeriesCounter(borders[i][0], borders[i][1]);
			seriesCounterArray[i] = new Thread(runnable);
			seriesCounterArray[i].start();
		}
		for (int i = 0; i < numberOfThreads; i++) {
			seriesCounterArray[i].join();
		}
		/*
		double sumOfSeries = 0;
		for (int i = 0; i < numberOfThreads; i++) {
			System.out.println(borders[i][0] + " ––– " + borders[i][1]);
			sumOfSeries += sum.getSumOfSeries(borders[i][0], borders[i][1]);
		}*/
		System.out.format("%f", sum.getSum());
	}
	
	public double getSum() {
		return totalSum;
	}
	/*
	private double getSumOfSeries(int startBorder, int endBorder) {
		double tempSumOfSeries = 0;
		for (int i = startBorder; i < endBorder; i++) {
			tempSumOfSeries += sin(i);
			//System.out.println(tempSumOfSeries);
		}
		return tempSumOfSeries;
	}
	*/
	private int[][] getBorders(int lengthOfSequence, int numberOfThreads) {
		if (lengthOfSequence < 0) {
			lengthOfSequence = 0 - lengthOfSequence;
		}
		if (numberOfThreads < 1) {
			throw new IllegalArgumentException("Illegal number of threads: " + numberOfThreads);
		}
		if (lengthOfSequence * 2 + 1 < numberOfThreads) {
			throw new IllegalArgumentException("Number of threads is to big for current sequence. Maximum alloved number of threads: " + lengthOfSequence * 2 + 1);		
		}
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
	
	class SeriesCounter implements Runnable {

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
			System.out.println("Thread finished it's work!");
			totalSum += tempSumOfSeries;
		}
	}
}
