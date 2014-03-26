package com.epam.next;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicBigInteger {

	private AtomicReference<BigInteger> value = new AtomicReference<>(new BigInteger("0"));
	private final BigInteger multiplier = new BigInteger("2");
	
	public BigInteger next() {
		BigInteger oldValue;
		BigInteger newValue;
		do {
			oldValue = value.get();
			if (oldValue.equals(new BigInteger("0"))) {
				newValue = new BigInteger("1");
			} else {
				newValue = oldValue.multiply(multiplier);
			}
		} while (!value.compareAndSet(oldValue, newValue));
		
		return newValue;
	}
}
