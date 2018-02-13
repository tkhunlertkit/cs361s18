public class Account {

    private String accountNumber;
    private String pin;
    private int balance;

    public Account() {
        this("0000", "0000", 0);
    }

    public Account(String accountNumber, String pin, int balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean validate(String pin) {
        return pin == this.pin;
    }

    public double getBalance() {
        return (double) this.balance / 100;
    }

    public void updateBalance(double amount) {
        this.balance += (int)amount * 100;
    }
}
