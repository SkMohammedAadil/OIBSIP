public class Account {
    private final String accountId;
    private final String pin;
    private final String holderName;
    private double balance;

    public Account(String accountId, String pin, String holderName, double openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        this.accountId = accountId;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public String getAccountId() { return accountId; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public boolean verifyPin(String enteredPin) { return pin.equals(enteredPin); }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero.");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient Funds.");
        balance -= amount;
    }
}
