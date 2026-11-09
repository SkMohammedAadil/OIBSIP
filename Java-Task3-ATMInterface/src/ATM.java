import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private final Bank bank;
    private final Scanner scanner;
    private final List<Transaction> transactionHistory;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
        this.transactionHistory = new ArrayList<>();
    }

    public void start() {
        System.out.println("================================");
        System.out.println("          JAVA ATM");
        System.out.println("================================");
        System.out.println("Demo login: 1001 / 1234");
        System.out.println("Second demo account: 1002 / 4321");

        Account account = login();
        if (account == null) {
            System.out.println("Too many failed attempts. Access denied.");
            scanner.close();
            return;
        }

        System.out.println("\nWelcome, " + account.getHolderName() + "!");
        showMenu(account);
        scanner.close();
    }

    private Account login() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.authenticate(userId, pin);
            if (account != null) return account;

            System.out.println("Invalid User ID or PIN. Attempts remaining: " + (3 - attempt));
        }
        return null;
    }

    private void showMenu(Account account) {
        boolean running = true;
        while (running) {
            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Balance Enquiry");
            System.out.println("6. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": showHistory(); break;
                case "2": withdraw(account); break;
                case "3": deposit(account); break;
                case "4": transfer(account); break;
                case "5":
                    System.out.printf("Available balance: Rs. %.2f%n", account.getBalance());
                    break;
                case "6":
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 to 6.");
            }
        }
    }

    private void withdraw(Account account) {
        double amount = readAmount("Enter withdrawal amount: ");
        if (amount < 0) return;
        try {
            account.withdraw(amount);
            transactionHistory.add(new Transaction("WITHDRAWAL", "Cash withdrawal", amount));
            System.out.printf("Please collect your cash. New balance: Rs. %.2f%n", account.getBalance());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void deposit(Account account) {
        double amount = readAmount("Enter deposit amount: ");
        if (amount < 0) return;
        try {
            account.deposit(amount);
            transactionHistory.add(new Transaction("DEPOSIT", "Cash deposit", amount));
            System.out.printf("Deposit successful. New balance: Rs. %.2f%n", account.getBalance());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void transfer(Account sender) {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine().trim();
        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }
        if (recipient == sender) {
            System.out.println("You cannot transfer money to the same account.");
            return;
        }

        double amount = readAmount("Enter transfer amount: ");
        if (amount < 0) return;

        try {
            sender.withdraw(amount);
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("TRANSFER",
                    "Transferred to account " + recipientId, amount));
            System.out.printf("Transfer successful. New balance: Rs. %.2f%n", sender.getBalance());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private double readAmount(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(input);
            if (!Double.isFinite(amount) || amount <= 0) {
                System.out.println("Enter a valid amount greater than zero.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid amount. Enter numbers only.");
            return -1;
        }
    }

    private void showHistory() {
        System.out.println("\n======= TRANSACTION HISTORY =======");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions in this session yet.");
            return;
        }
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
