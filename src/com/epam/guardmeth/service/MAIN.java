package com.epam.guardmeth.service;

import com.epam.guardmeth.buffer.CircularBuffer;
import com.epam.guardmeth.buffer.CircularBufferConcurrency;

public class MAIN {

	public static void main(String[] args) {
		int N = 10; 
		//CircularBuffer<Integer> cb = new CircularBufferNonconcurrency<>(N);
		CircularBuffer<Integer> cb = new CircularBufferConcurrency<>(N);
		for (int i = 0; i < 5; i++) {
			new Thread(new Producer(cb)).start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread(new Consumer(cb)).start();
		}
	}
}
