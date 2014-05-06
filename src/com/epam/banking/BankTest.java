package com.epam.banking;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.epam.banking.account.Account;
import com.epam.banking.account.AccountFactory;
import com.epam.banking.account.RandomBalanceAccountFactory;
import com.epam.banking.account.exceptions.BankingException;

public class BankTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetTotalSum() {
		Bank bank = new Bank();
		Account account = new Account(0, 100);
		bank.addAccount(account);
		assertEquals(100, bank.getTotalSum(), 0.0001);
	}
	
	@Test
	public void testTransfer() {
		Bank bank = new Bank();
		Account account1 = new Account(0, 200);
		Account account2 = new Account(1, 300);
		bank.addAccount(account1);
		bank.addAccount(account2);
		bank.transfer(0, 1, 100);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(100, bank.getAccount(0).getBalance(), 0.0001);
		assertEquals(400, bank.getAccount(1).getBalance(), 0.0001);
	}
	
	@Test
	public void testBankManyTransfers() {
		final int MAX_INVOKES = 5000;
		final int ACCOUNT_MAX_NUMBER = 1000;
		
		final Bank bank = new Bank();
		AccountFactory af = new RandomBalanceAccountFactory();
		for (int i = 0; i < ACCOUNT_MAX_NUMBER; i++) {
			bank.addAccount(af.getAccount());
		}
		float bankSumBefore = bank.getTotalSum();
		final Random random = new Random();
		
		Thread[] threads = new Thread[MAX_INVOKES];
		for (int i = 0; i < MAX_INVOKES; i++) {
			assert(ACCOUNT_MAX_NUMBER > 1);
			
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					int from = random.nextInt(ACCOUNT_MAX_NUMBER);
					int to = random.nextInt(ACCOUNT_MAX_NUMBER);
					while (from == to) {
						to = random.nextInt(ACCOUNT_MAX_NUMBER);
					}
					int ammount = random.nextInt(100);
					try {
						bank.transfer(from, to, ammount);
					} catch(BankingException ex) {
						System.out.println(ex.getMessage());
					}
				}
			});
			threads[i].start();
		}
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		float bankSumAfter = bank.getTotalSum();
		assertEquals(0, bankSumBefore - bankSumAfter, 0.0001);
	}
}

