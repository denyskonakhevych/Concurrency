package com.epam.mergesort;

import java.util.Arrays;

public class ParallelMergeSort {

	public static void main(String... args) {
		int[] arr = new int[] {1, 15, 16, 2, -1, 19, 3, 7, 15, 9, -3};
		ParallelMergeSort pms = new ParallelMergeSort();
        pms.sort(arr);
        System.out.println(Arrays.toString(arr));
	}
	
	public void sort(int [] a) {
	    sort(a, 0, a.length - 1);
	}
	
	private void sort(int [] a, int lo, int hi) {
	    if (hi <= lo)
	        return;
	    
	    int mid = lo + (hi - lo) / 2;
	    sort(a, lo, mid);
	    sort(a, mid + 1, hi);
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
