package com.bank;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String accountNumber;
    private final String bik;
    private final String kpp;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public BankAccount(String accountNumber, String bik, String kpp) {
        this.accountNumber = accountNumber;
        this.bik = bik;
        this.kpp = kpp;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBik() {
        return bik;
    }

    public String getKpp() {
        return kpp;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("����� ������ ���� �������������.");
        balance += amount;
        transactions.add(new Transaction("����������", amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("����� ������ ���� �������������.");
        if (amount > balance) throw new IllegalArgumentException("������������ �������.");
        balance -= amount;
        transactions.add(new Transaction("������", -amount));
    }

    @Override
    public String toString() {
        return String.format("���� %s | ���: %s | ���: %s | ������: %.2f", accountNumber, bik, kpp, balance);
    }
}
