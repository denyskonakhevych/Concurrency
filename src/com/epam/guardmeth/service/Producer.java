package com.epam.guardmeth.service;

import java.util.Random;

import com.epam.guardmeth.buffer.CircularBuffer;

public class Producer implements Runnable {

	private CircularBuffer<Integer> buffer;
	private volatile static int counter = 0; 
	
	public Producer(CircularBuffer<Integer> buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			try {
				//int numb = getNext();
				buffer.put(getNext());
				//buffer.put(i);
				Thread.sleep(random.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static synchronized int getNext() {
		return ++counter;
	}
}
