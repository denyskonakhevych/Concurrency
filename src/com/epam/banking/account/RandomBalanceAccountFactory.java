package com.epam.banking.account;

import java.util.Random;

public class RandomBalanceAccountFactory implements AccountFactory {

	private static int id = 0;
	
	@Override
	public Account getAccount() {
		return new Account(id++, new Random().nextInt(10000));
	}
}
