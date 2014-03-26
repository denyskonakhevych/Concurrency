package com.epam.guardmeth.service;

import java.util.Random;

import com.epam.guardmeth.buffer.CircularBuffer;

public class Consumer implements Runnable {

	private CircularBuffer<?> buffer;
	
	public Consumer(CircularBuffer<?> buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < 250; i++) {
			try {
				System.out.println(buffer.pop());
				Thread.sleep(random.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
