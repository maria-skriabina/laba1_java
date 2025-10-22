package com.bank;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BankAppSwing extends JFrame {

    private final BankService bankService = new BankService();

    private final DefaultListModel<String> accountListModel = new DefaultListModel<>();
    private final JList<String> accountList = new JList<>(accountListModel);
    private final DefaultListModel<String> transactionListModel = new DefaultListModel<>();
    private final JList<String> transactionList = new JList<>(transactionListModel);

    private final JLabel balanceLabel = new JLabel("Баланс: —");
    private final JTextField amountField = new JTextField(10);
    private final JTextField searchField = new JTextField(15);

    public BankAppSwing() {
        setTitle("Банковская система (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.add(new JLabel("Счета:"), BorderLayout.NORTH);
        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane accountScroll = new JScrollPane(accountList);
        leftPanel.add(accountScroll, BorderLayout.CENTER);

        // Панель поиска
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Найти");
        searchButton.addActionListener(e -> searchAccounts());
        searchPanel.add(new JLabel("Поиск:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        leftPanel.add(searchPanel, BorderLayout.SOUTH);

        // Центральная панель
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        balancePanel.add(balanceLabel);

        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.add(new JLabel("Сумма:"));
        amountPanel.add(amountField);

        JButton depositButton = new JButton("Пополнить");
        JButton withdrawButton = new JButton("Снять");
        JButton refreshButton = new JButton("Обновить");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(refreshButton);

        depositButton.addActionListener(e -> depositMoney());
        withdrawButton.addActionListener(e -> withdrawMoney());
        refreshButton.addActionListener(e -> refreshTransactions());

        centerPanel.add(balancePanel, BorderLayout.NORTH);
        centerPanel.add(amountPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Панель транзакций
        JPanel transactionPanel = new JPanel(new BorderLayout());
        transactionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        transactionPanel.add(new JLabel("Транзакции:"), BorderLayout.NORTH);
        JScrollPane transactionScroll = new JScrollPane(transactionList);
        transactionPanel.add(transactionScroll, BorderLayout.CENTER);

        JSplitPane centerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel, transactionPanel);
        centerSplit.setDividerLocation(150);
        add(centerSplit, BorderLayout.CENTER);

        // Нижняя панель создания счета
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JTextField accField = new JTextField(10);
        JTextField bikField = new JTextField(8);
        JTextField kppField = new JTextField(8);
        JButton createButton = new JButton("Создать счёт");

        createButton.addActionListener(e -> {
            try {
                bankService.openAccount(accField.getText(), bikField.getText(), kppField.getText());
                accountListModel.addElement(accField.getText());
                showInfo("Счёт успешно создан!");
                accField.setText(""); bikField.setText(""); kppField.setText("");
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        bottomPanel.add(new JLabel("Номер счёта:"));
        bottomPanel.add(accField);
        bottomPanel.add(new JLabel("БИК:"));
        bottomPanel.add(bikField);
        bottomPanel.add(new JLabel("КПП:"));
        bottomPanel.add(kppField);
        bottomPanel.add(createButton);

        add(leftPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        accountList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = accountList.getSelectedValue();
                if (selected != null) updateAccountInfo(selected);
            }
        });
    }

    private void depositMoney() {
        String selected = accountList.getSelectedValue();
        if (selected == null) {
            showError("Выберите счёт!");
            return;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            bankService.deposit(selected, amount);
            updateAccountInfo(selected);
        } catch (NumberFormatException e) {
            showError("Введите корректную сумму!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void withdrawMoney() {
        String selected = accountList.getSelectedValue();
        if (selected == null) {
            showError("Выберите счёт!");
            return;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            bankService.withdraw(selected, amount);
            updateAccountInfo(selected);
        } catch (NumberFormatException e) {
            showError("Введите корректную сумму!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void refreshTransactions() {
        String selected = accountList.getSelectedValue();
        if (selected != null) updateAccountInfo(selected);
    }

    private void searchAccounts() {
        String query = searchField.getText();
        accountListModel.clear();
        bankService.getAllAccounts().forEach(acc -> {
            if (acc.getAccountNumber().contains(query)
                    || acc.getBik().contains(query)
                    || acc.getKpp().contains(query)) {
                accountListModel.addElement(acc.getAccountNumber());
            }
        });
        if (accountListModel.isEmpty()) {
            showInfo("Счета не найдены.");
        }
    }

    private void updateAccountInfo(String accNum) {
        try {
            BankAccount account = bankService.getAccount(accNum);
            balanceLabel.setText(String.format("Баланс: %.2f", account.getBalance()));

            transactionListModel.clear();
            List<Transaction> transactions = account.getTransactions();
            for (Transaction t : transactions) {
                transactionListModel.addElement(t.toString());
            }
        } catch (Exception e) {
            showError("Ошибка обновления: " + e.getMessage());
        }
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
