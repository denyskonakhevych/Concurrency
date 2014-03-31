package com.epam.banking.account.exceptions;

public class NotEnoughMoneyException extends BankingException {

	public NotEnoughMoneyException(String message) {
		super(message);
	}
}
