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
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной.");
        balance += amount;
        transactions.add(new Transaction("Пополнение", amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной.");
        if (amount > balance) throw new IllegalArgumentException("Недостаточно средств.");
        balance -= amount;
        transactions.add(new Transaction("Снятие", -amount));
    }

    @Override
    public String toString() {
        return String.format("Счёт %s | БИК: %s | КПП: %s | Баланс: %.2f", accountNumber, bik, kpp, balance);
    }
}
