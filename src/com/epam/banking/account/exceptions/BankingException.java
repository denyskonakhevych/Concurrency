package com.epam.banking.account.exceptions;

public abstract class BankingException extends RuntimeException {

	public BankingException(String message) {
		super(message);
	}
}
