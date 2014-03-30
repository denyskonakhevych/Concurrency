package com.epam.banking;

import java.util.Random;

public class MAIN {

	public static void main(String[] args) {
		Bank bank = new Bank();
		AccountFactory af = new RandomBalanceAccountFactory();
		for (int i = 0; i < 1000; i++) {
			bank.addAccount(af.getAccount());
		}
		float bankSumBefore = bank.getTotalSum();
		Random random = new Random();
		final int ACCOUNT_MAX_NUMBER = bank.getAccountNumber();
		for (int i = 0; i < 10000; i++) {
			assert(ACCOUNT_MAX_NUMBER>1);
			int from = random.nextInt(ACCOUNT_MAX_NUMBER);
			int to = random.nextInt(ACCOUNT_MAX_NUMBER);
			while (from == to) {
				to = random.nextInt(ACCOUNT_MAX_NUMBER);
			}
			int ammount = random.nextInt(1000);
			bank.transfer(from, to, ammount);
		}
		float bankSumAfter = bank.getTotalSum();
		System.out.println("Bank sum before: before = " + bankSumBefore + "; after = " + bankSumAfter + " dif = " + (bankSumBefore - bankSumAfter));
		/*
		for (String s : bank.getAccountsInfo()) {
			System.out.println(s);
		}
		*/
	}
}
