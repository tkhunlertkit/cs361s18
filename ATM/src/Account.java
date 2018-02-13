public class Account {

    private String accountNumber;
    private String pin;
    private int balance;

    public Account() {
        this("0000", "0000", 0);
    }

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = (int)(balance * 100);
    }

    public boolean validate(String pin) {
        return pin.equalsIgnoreCase(this.pin);
    }

    public double getBalance() {
        return ((double) this.balance / 100);
    }

    public void updateBalance(double amount) {
        this.balance += (int) (amount * 100);
    }

}
