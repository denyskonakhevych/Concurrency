package com.epam.banking.account;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.epam.banking.account.exceptions.NotEnoughMoneyException;

public class Account {

	public final int ID;
	private float balance;
	public final Lock lock = new ReentrantLock();

	public int getId() {
		return ID;
	}

	public float getBalance() {
		return balance;
	}

	public Account(int id, int balance) {
		ID = id;
		this.balance = balance;
	}

	public void withdraw(int amount) {
		assert (amount >= 0);
		if (balance < amount) {
			throw new NotEnoughMoneyException("Account " + ID + ": not enough money for transaction. Was: " + balance + ", wanted to withdraw: " + amount);
		}
		balance -= amount;
	}

	public void deposit(int amount) {
		assert (amount >= 0);
		balance += amount;
	}

	@Override
	public String toString() {
		return "ID: " + ID + "; balance: " + balance;
	}
}
