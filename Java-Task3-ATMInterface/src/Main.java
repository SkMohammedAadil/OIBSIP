/**
 * Oasis Infobyte Java Development - Task 3
 * ATM Interface demonstration.
 *
 * Demo accounts:
 * User ID: 1001 | PIN: 1234 | Opening balance: Rs. 5000.00
 * User ID: 1002 | PIN: 4321 | Opening balance: Rs. 3000.00
 *
 * This is an educational in-memory simulation, not a real banking system.
 */
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.addAccount(new Account("1001", "1234", "Aadil", 5000.00));
        bank.addAccount(new Account("1002", "4321", "Demo User", 3000.00));

        ATM atm = new ATM(bank);
        atm.start();
    }
}
