package com.epam.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.epam.banking.account.Account;
import com.epam.banking.account.exceptions.AccountAddressTransactionException;
import com.epam.banking.account.exceptions.NotEnoughMoneyException;

public class Bank {

	private List<Account> accounts = new ArrayList<>();
	
	private ExecutorService executor = Executors.newCachedThreadPool();
	//private ExecutorService executor = Executors.newFixedThreadPool(1000);
	
	public float getTotalSum() {
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			//NOP
		}
		float sum = 0;
		for (Account acc : accounts) {
			sum += acc.getBalance();
		}
		executor = Executors.newCachedThreadPool();
		return sum;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public Account getAccount(int id) {
		for (Account acc : accounts) {
			if (acc.getId() == id) {
				return acc;
			}
		}
		return null;
	}
	
	public void transfer(int fromId, int toId, int amount) {
		if (fromId == toId) {
			throw new AccountAddressTransactionException("Source and destination account id matches!");
		}
		Account from = accounts.get(fromId); 
		Account to = accounts.get(toId);
		transfer(from, to, amount);
	}

	private void transfer(Account from, Account to, int amount) {
		executor.execute(new Transaction(from, to, amount));
	}
	
	public List<String> getAccountsInfo() {
		List<String> info = new ArrayList<>();
		for (Account account : accounts) {
			info.add(account.toString());
		}
		return info;
	}
	
	public int getAccountNumber() {
		return accounts.size();
	}
	
	private class Transaction implements Runnable {
		
		private Account from;
		private Account to;
		private int amount;

		public Transaction(Account from, Account to, int amount) {
			this.from = from;
			this.to = to;
			this.amount = amount;
		}

		@Override
		public void run() {
			Account first = (from.ID < to.ID) ? from : to;
			Account second = (from.ID < to.ID) ? to : from;
			boolean success = false;
			while (!success) {
				try {
					while (!first.lock.tryLock(10, TimeUnit.MILLISECONDS)) {
						Thread.yield();
					}
					if (second.lock.tryLock(10, TimeUnit.MILLISECONDS)) {
						try {
							from.withdraw(amount);
							to.deposit(amount);
							success = true;
						} catch(NotEnoughMoneyException ex) {
							//System.out.println(ex.getMessage());
							return;
						} finally {
							second.lock.unlock();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					first.lock.unlock();
				}
			}
		}
	}
}
