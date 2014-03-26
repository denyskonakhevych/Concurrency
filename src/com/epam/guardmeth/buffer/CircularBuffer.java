package com.epam.guardmeth.buffer;

public interface CircularBuffer<T> {

	public void put(T item) throws InterruptedException;
	
	public T peek() throws InterruptedException;
	
	public T pop() throws InterruptedException;
}
