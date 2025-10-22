package com.bank;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    /** ������� ����� ���� */
    public void openAccount(String accountNumber, String bik, String kpp) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("���� � ����� ������� ��� ����������.");
        }
        accounts.put(accountNumber, new BankAccount(accountNumber, bik, kpp));
        System.out.println("���� ������� ������.");
    }

    /** �������� ������ �� ���� */
    public void deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.deposit(amount);
        System.out.println("������ ������� ���������.");
    }

    /** ����� ������ �� ����� */
    public void withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.withdraw(amount);
        System.out.println("������ ������� �����.");
    }

    /** �������� ������ (��� ����������� ������) */
    public void showBalance(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        System.out.printf("������ ����� %s: %.2f%n", accountNumber, account.getBalance());
    }

    /** �������� ������ ���������� (��� ����������� ������) */
    public void showTransactions(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        if (account.getTransactions().isEmpty()) {
            System.out.println("��� ����������.");
            return;
        }
        account.getTransactions().forEach(System.out::println);
    }

    /** ����� �� ��������� (��� ����������� ������) */
    public void searchAccount(String query) {
        boolean found = false;
        for (BankAccount acc : accounts.values()) {
            if (acc.getAccountNumber().contains(query)
                    || acc.getBik().contains(query)
                    || acc.getKpp().contains(query)) {
                System.out.println("������ ����: " + acc);
                found = true;
            }
        }
        if (!found) {
            System.out.println("���� �� ������.");
        }
    }

    /** �������� ������ ����� (��� UI) */
    public BankAccount getAccount(String accountNumber) {
        return findAccount(accountNumber);
    }

    /** �������� ��� ����� (��� UI ������ � �����������) */
    public Collection<BankAccount> getAllAccounts() {
        return accounts.values();
    }

    /** ����� ����� �� ������, ���� ���������� */
    private BankAccount findAccount(String accountNumber) {
        BankAccount acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("���� �� ������.");
        }
        return acc;
    }
}