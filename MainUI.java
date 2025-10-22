package com.bank;


public class MainUI {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new BankAppSwing().setVisible(true);
        });
    }
}