package com.epam.sin.sum.count;

public abstract class SinSeriesCounter {

	protected final int[][] borders;
	
	int lengthOfSequence; 
	int numberOfThreads;
	
	public SinSeriesCounter(int lengthOfSequence, int numberOfThreads) {
		if (lengthOfSequence < 0) {
			lengthOfSequence = 0 - lengthOfSequence;
		}
		if (numberOfThreads < 1) {
			System.out.println("Illegal number of threads: " + numberOfThreads);
			throw new IllegalArgumentException("Illegal number of threads: " + numberOfThreads);
		}
		//System.out.println(lengthOfSequence);
		//System.out.println(numberOfThreads);
		if (lengthOfSequence * 2 + 1 < numberOfThreads) {
			//System.out.println(lengthOfSequence * 2 + 1);
			throw new IllegalArgumentException("Number of threads is to big for current sequence. Maximum alloved number of threads: " + lengthOfSequence * 2 + 1);		
		}
		this.lengthOfSequence = lengthOfSequence;
		this.numberOfThreads = numberOfThreads;
		borders = getBorders(lengthOfSequence, numberOfThreads);
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
	
	public abstract double countSeries();
}
