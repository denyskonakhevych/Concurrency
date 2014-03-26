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

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AtomicBigInteger that = (AtomicBigInteger) obj;
        if(!value.get().equals(that.value.get())) {
        	return false;
        }
        return true;
	}
	
	
}
