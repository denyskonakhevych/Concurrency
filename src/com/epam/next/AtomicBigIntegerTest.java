package com.epam.next;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class AtomicBigIntegerTest {

	@Test
	public void test() {
		final int MAX = 100;
		
		final AtomicBigInteger inc = new AtomicBigInteger();
		final Set<BigInteger> init = new TreeSet<>();
		
		final SortedSet<BigInteger> numbers = Collections.synchronizedSortedSet((SortedSet<BigInteger>) init);
		
		Thread[] threads = new Thread[MAX];
		for (int i = 0; i < MAX; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < MAX; j++) {
						numbers.add(inc.next());
					}
				}
			});
			threads[i].start();
		}
		for(int i = 0; i < MAX; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		BigInteger sum = new BigInteger("0");
		for (BigInteger i : numbers) {
			sum = sum.add(i);
		}
		assertEquals(sum, getSum(2, numbers.size()));
	}
	
	private static BigInteger getSum(int q, int n) {
		BigInteger res = new BigInteger("" + q);
		res = res.pow(n).subtract(new BigInteger("1"));
		return res;
	}
}
