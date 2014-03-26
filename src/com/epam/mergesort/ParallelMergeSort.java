package com.epam.mergesort;

public class ParallelMergeSort implements Runnable {
	
	private int[] array;
	private int from;
	private int to;
	
	public ParallelMergeSort(int[] array) {
		if (array == null) {
			throw new IllegalArgumentException("Expected array but was: " + array);
		}
		this.array = array;
		this.from = 0;
		this.to = array.length - 1;
	}
	
	public ParallelMergeSort(int[] array, int from, int to) {
		this.array = array;
		this.from = from;
		this.to = to;
	}
	
	@Override
	public void run() {
		try {
			sort(array, from, to);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sort() throws InterruptedException {
	    sort(array, from, to);
	}
	
	private void sort(int [] a, int lo, int hi) throws InterruptedException {
	    if (hi <= lo) {
	        return;
	    }
	    int mid = lo + (hi - lo) / 2;
	    
	    if (mid == lo) {
	    	sort(a, lo, mid);
	    } else {
		    Thread right = new Thread(new ParallelMergeSort(a, mid + 1, hi));
		    right.start();
		    sort(a, lo, mid);
			right.join();
	    }
	    merge(a, lo, mid, hi);
	}
	
	public void merge(int[] a, int lo, int mid, int hi) {
	    int i = lo, j = mid + 1;
	    int[] aux = new int [a.length];
	    System.arraycopy(a, 0, aux, 0, a.length);
	    for (int k = lo; k <= hi; k++) {
	        if (i > mid) {
	        	System.arraycopy(aux, j, a, k, hi - k + 1);
                break;
	        } else if (j > hi) {
	        	System.arraycopy(aux, i, a, k, hi - k + 1);
	        	break;
	        } else if (aux[j]<(aux[i]) ) {
	            a[k] = aux[j++];
	        } else {
	            a[k] = aux[i++];
	        }
	    }
	}
}
