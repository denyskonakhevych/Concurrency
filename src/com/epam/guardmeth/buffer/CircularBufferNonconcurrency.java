package com.epam.guardmeth.buffer;

public class CircularBufferNonconcurrency<T> implements CircularBuffer<T> {
	
	private final int MAX_SIZE;
	private volatile int size;
	private Entry<T> firstEmpty;
	private Entry<T> firstOccupied;
	
	public CircularBufferNonconcurrency(int size) {
		
		if (size < 1) {
			throw new IllegalArgumentException("Illegal size of buffer: " + size);
		}
		this.size = 0;
		
		Entry<T> currentElem;
		firstEmpty = new Entry<>(null);
		firstOccupied = firstEmpty;
		currentElem = firstEmpty;
		
		for (int i = 0; i < size; i++) {
			currentElem.setNext(new Entry<T>(null));
			currentElem = currentElem.getNext();
		}
		currentElem.setNext(firstEmpty);
		MAX_SIZE = size;
	}
	
	public synchronized void put(T item) throws InterruptedException {
		while (isFull()) {
            wait();
		}
		firstEmpty.setItem(item);
		firstEmpty = firstEmpty.getNext();
		size++;
		notifyAll();
	}
	
	public synchronized T peek() throws InterruptedException {
		while (isEmpty()) {
            wait();
		}
		T item = firstOccupied.getItem();
		notifyAll();
		return item;
	}
	
	public synchronized T pop() throws InterruptedException {
		while (isEmpty()) {
            wait();
		}
		T item = firstOccupied.getItem();
		firstOccupied = firstOccupied.getNext();
		size--;
		notifyAll();
		return item;
	}
	
	private final boolean isEmpty() {
		return size == 0;
	}
	
	private final boolean isFull() {
		return size == MAX_SIZE;
	}
	
	private class Entry<E> {
		private E item;
		private Entry<E> next;
		
		Entry(Entry<E> next) {
			this.next = next;
		}
		
		public E getItem() {
			return item;
		}

		public void setItem(E item) {
			this.item = item;
		}

		public Entry<E> getNext() {
			return next;
		}

		void setNext(Entry<E> next) {
			this.next = next;
		}
	}
}
