package com.bank;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    /** Создать новый счёт */
    public void openAccount(String accountNumber, String bik, String kpp) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Счёт с таким номером уже существует.");
        }
        accounts.put(accountNumber, new BankAccount(accountNumber, bik, kpp));
        System.out.println("Счёт успешно открыт.");
    }

    /** Положить деньги на счёт */
    public void deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.deposit(amount);
        System.out.println("Деньги успешно зачислены.");
    }

    /** Снять деньги со счёта */
    public void withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        account.withdraw(amount);
        System.out.println("Деньги успешно сняты.");
    }

    /** Показать баланс (для консольного режима) */
    public void showBalance(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        System.out.printf("Баланс счёта %s: %.2f%n", accountNumber, account.getBalance());
    }

    /** Показать список транзакций (для консольного режима) */
    public void showTransactions(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        if (account.getTransactions().isEmpty()) {
            System.out.println("Нет транзакций.");
            return;
        }
        account.getTransactions().forEach(System.out::println);
    }

    /** Поиск по атрибутам (для консольного режима) */
    public void searchAccount(String query) {
        boolean found = false;
        for (BankAccount acc : accounts.values()) {
            if (acc.getAccountNumber().contains(query)
                    || acc.getBik().contains(query)
                    || acc.getKpp().contains(query)) {
                System.out.println("Найден счёт: " + acc);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Счёт не найден.");
        }
    }

    /** Получить объект счёта (для UI) */
    public BankAccount getAccount(String accountNumber) {
        return findAccount(accountNumber);
    }

    /** Получить все счета (для UI поиска и отображения) */
    public Collection<BankAccount> getAllAccounts() {
        return accounts.values();
    }

    /** Поиск счёта по номеру, либо исключение */
    private BankAccount findAccount(String accountNumber) {
        BankAccount acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("Счёт не найден.");
        }
        return acc;
    }
}