package com.epam.guardmeth.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularBufferConcurrency<T> implements CircularBuffer<T> {

	private final int MAX_SIZE;
	private volatile int size;
	private Entry<T> firstEmpty;
	private Entry<T> firstOccupied;
	
	final Lock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition(); 
	final Condition notEmpty = lock.newCondition();
	
	public CircularBufferConcurrency(int size) {
		
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
	
	public void put(T item) throws InterruptedException {
		lock.lock();
		try {
			while (isFull()) {
				notFull.await();
			}
			firstEmpty.setItem(item);
			firstEmpty = firstEmpty.getNext();
			size++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public T peek() throws InterruptedException {
		lock.lock();
		try {
			while (isEmpty()) {
				notEmpty.await();
			}
			T item = firstOccupied.getItem();
			notFull.signal();
			return item;
		} finally {
			lock.unlock();
		}
	}
	
	public T pop() throws InterruptedException {
		lock.lock();
		try {
			while (isEmpty()) {
				notEmpty.await();
			}
			T item = firstOccupied.getItem();
			firstOccupied = firstOccupied.getNext();
			size--;
			notFull.signal();
			return item;
		} finally {
			lock.unlock();
		}
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
