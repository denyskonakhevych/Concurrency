package com.epam.sin.sum.count;

import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SinSeriesCounterCollable extends SinSeriesCounter {

	public SinSeriesCounterCollable(int lengthOfSequence, int numberOfThreads) {
		super(lengthOfSequence, numberOfThreads);
	}

	@Override
	public double countSeries() {
		double sumOfSeries = 0;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<Double>> list = new ArrayList<>();
		for(int i=0; i< numberOfThreads; i++){
			Callable<Double> callable = new SeriesCounter<>(borders[i][0], borders[i][1]);
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
	
	private class SeriesCounter<Double> implements Callable<Double> {

		int startBorder; 
		int endBorder;
		
		public SeriesCounter(int startBorder, int endBorder) {
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
