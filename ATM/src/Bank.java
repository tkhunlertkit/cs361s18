import java.util.HashMap;
import java.util.Set;

public class Bank {

    private HashMap<String, Account> accounts;
    private static Bank bank = null;

    public static Bank getBank() {
        if (bank == null) {
            bank = new Bank();
        }

        return bank;
    }

    public void resetBank() {
        bank = new Bank();
    }

    private Bank() {
        accounts = new HashMap<>();
        this.add("1234", "6789", 80);
        this.add("6789", "4321", 60);
    }

    public void add(String accountNumber, String pin, double balance) throws AccountExistedException{
        Account a = new Account(accountNumber, pin, balance);
        if (!accounts.containsKey(accountNumber)) {
            accounts.put(accountNumber, a);
            return;
        }
        throw new AccountExistedException(accountNumber + " Existed");
    }

    public boolean validate(String accountNumber, String pin) throws AccountNotExistedException {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber).validate(pin);
        }

        throw new AccountNotExistedException();
    }

    public double withdraw(String accountNumber, String pin, double amount) throws AccountNotExistedException {

        if (validate(accountNumber, pin)) {
            // Account exists
            Account a = accounts.get(accountNumber);
            if (a.getBalance() < amount) {
                // update amount
                amount = a.getBalance();
            }

            a.updateBalance(-amount);
            return amount;
        }

        throw new AccountNotExistedException();
    }

    public boolean deposit(String accountNumber, String pin, double amount) throws AccountNotExistedException {
        if (validate(accountNumber, pin)) {
            accounts.get(accountNumber).updateBalance(amount);
            return true;
        }

        return false;
    }

    public double checkBalance(String accountNumber, String pin) throws AccountNotExistedException {
        if (validate(accountNumber, pin)) {
            return accounts.get(accountNumber).getBalance();
        }

        throw new AccountNotExistedException();
    }

    public Set<String> getAllAccounts() {
        return accounts.keySet();
    }

    public boolean checkAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }
}
