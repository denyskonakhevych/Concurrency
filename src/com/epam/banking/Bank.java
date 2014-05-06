package com.epam.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.epam.banking.account.Account;
import com.epam.banking.account.exceptions.AccountAddressTransactionException;
import com.epam.banking.account.exceptions.AccountTemporaryUnavaliable;

public class Bank {

	private List<Account> accounts = new ArrayList<>();
	
	public float getTotalSum() {
		float sum = 0;
		for (Account acc : accounts) {
			sum += acc.getBalance();
		}
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
		try {
			for (int i = 0; i < 3; i++) {
				if (transfer(from, to, amount)) {
					return;
				} else {
					Thread.sleep(1000);
				}
			}
			throw new AccountTemporaryUnavaliable("Account temporary unavaliable");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		throw new AccountTemporaryUnavaliable("Account temporary unavaliable");
	}

	private boolean transfer(Account from, Account to, int amount) {
		
		Account first = (from.ID < to.ID) ? from : to;
		Account second = (from.ID < to.ID) ? to : from;
		try {
			if(first.lock.tryLock(10, TimeUnit.MILLISECONDS)) {
				try {
					if (second.lock.tryLock(10, TimeUnit.MILLISECONDS)) {
						try {
							from.withdraw(amount);
							to.deposit(amount);
							Thread.sleep(100);
							return true;
						} finally {
							second.lock.unlock();
						}
					}
				} finally {
					first.lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
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
}
