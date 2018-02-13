public class ATM {

    private Bank b;
    private Session currentSession;

    public void start() {
        b = new Bank();
    }

    public void restartSession() {
        currentSession = null;
    }

    public boolean validate(String accountNumber, String pin) {
        if (b.validate(accountNumber, pin)) {
            currentSession = new Session(accountNumber, pin);
            return true;
        }

        return false;
    }

    public boolean withdrawal(double amount) {
        return b.withdraw(currentSession.accountNumber, currentSession.pin, amount);
    }

    public boolean deposit(double amount) {
        return b.deposit(currentSession.accountNumber, currentSession.pin, amount);
    }

    public double checkBalance() {
        return b.checkBalance(currentSession.accountNumber, currentSession.pin);
    }

    private class Session {
        public String accountNumber;
        public String pin;

        public Session(String accountNumber, String pin) {
            this.accountNumber = accountNumber;
            this.pin = pin;
        }
    }
}
