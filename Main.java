package com.bank;



import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();
        boolean running = true;

        while (running) {
            System.out.println("\n=== ���������� ���� ===");
            System.out.println("1. ������� ����");
            System.out.println("2. �������� ������");
            System.out.println("3. ����� ������");
            System.out.println("4. �������� ������");
            System.out.println("5. �������� ������ ����������");
            System.out.println("6. ������ ���� �� ���������");
            System.out.println("0. �����");
            System.out.print("�������� ��������: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("������� ����� �����: ");
                        String accNum = scanner.nextLine();
                        System.out.print("������� ���: ");
                        String bik = scanner.nextLine();
                        System.out.print("������� ���: ");
                        String kpp = scanner.nextLine();
                        bankService.openAccount(accNum, bik, kpp);
                    }
                    case "2" -> {
                        System.out.print("������� ����� �����: ");
                        String accNum = scanner.nextLine();
                        System.out.print("������� �����: ");
                        double deposit = Double.parseDouble(scanner.nextLine());
                        bankService.deposit(accNum, deposit);
                    }
                    case "3" -> {
                        System.out.print("������� ����� �����: ");
                        String accNum = scanner.nextLine();
                        System.out.print("������� �����: ");
                        double withdraw = Double.parseDouble(scanner.nextLine());
                        bankService.withdraw(accNum, withdraw);
                    }
                    case "4" -> {
                        System.out.print("������� ����� �����: ");
                        String accNum = scanner.nextLine();
                        bankService.showBalance(accNum);
                    }
                    case "5" -> {
                        System.out.print("������� ����� �����: ");
                        String accNum = scanner.nextLine();
                        bankService.showTransactions(accNum);
                    }
                    case "6" -> {
                        System.out.print("������� ��������� ������ (����� �����/���/���): ");
                        String query = scanner.nextLine();
                        bankService.searchAccount(query);
                    }
                    case "0" -> running = false;
                    default -> System.out.println("������������ �����.");
                }
            } catch (Exception e) {
                System.out.println("������: " + e.getMessage());
            }
        }

        System.out.println("������ ���������.");
        scanner.close();
    }
}
