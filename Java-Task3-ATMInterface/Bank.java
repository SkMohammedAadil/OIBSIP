import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account authenticate(String accountId, String pin) {
        Account account = accounts.get(accountId);
        return account != null && account.verifyPin(pin) ? account : null;
    }

    public Account findAccount(String accountId) {
        return accounts.get(accountId);
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }
}
