public class ATM {

    private enum State {
        IDLE, ACCOUNT_NUM_ENTERED, ACCOUNT_VALIDATED, WITHDRAWAL_SELECTED, DEPOSIT_SELECTED;

        private static State[] vals = values();

        public State next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }
    }

    private class Session {
        public String accountNumber;
        public String pin;
        public State state;

        public Session() {
            state = State.IDLE;
        }

        public Session(String accountNumber, String pin) {
            this.accountNumber = accountNumber;
            this.pin = pin;
        }
    }

    private Bank b;
    private Session currentSession;

    public void start() {
        b = Bank.getBank();
        currentSession = new Session();
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

    public String getStatement() {
        switch (currentSession.state) {
            case IDLE:                  return "Enter a card number: ";
            case ACCOUNT_NUM_ENTERED:   return "Enter a pin number: ";
            case ACCOUNT_VALIDATED:     return "Enter an operation [w]ithdrawal / [d]eposit: ";
            case WITHDRAWAL_SELECTED:
            case DEPOSIT_SELECTED:
                return "Enter an amount: ";

            default:
                currentSession = new Session();
                return "Awkward.... Restart....\nEnter a card number: ";
        }
    }

    public String execute(String command) {
        String returnStatement = "";
        double amount;
        switch(currentSession.state) {
            case IDLE:
                if (b.checkAccount(command)) {
                    currentSession.accountNumber = command;
                    currentSession.state = currentSession.state.next();
                    returnStatement = "Account Number Entered.";
                } else {
                    currentSession = new Session();
                    returnStatement = "Invalid account number\n";
                }
                break;

            case ACCOUNT_NUM_ENTERED:
                if (b.validate(currentSession.accountNumber, command)) {
                    currentSession.pin = command;
                    currentSession.state = currentSession.state.next();
                    returnStatement = "Account validated";
                } else {
                    returnStatement = "Invalid Account information\n";
                    currentSession = new Session();
                }
                break;

            case ACCOUNT_VALIDATED:
                char entered = command.toLowerCase().charAt(0);
                if (entered == 'w') {
                    currentSession.state = State.WITHDRAWAL_SELECTED;
                    returnStatement = "Withdeawal Selected.";
                } else if (entered == 'd') {
                    currentSession.state = State.DEPOSIT_SELECTED;
                    returnStatement = "Deposit Selected.";
                } else {
                    currentSession = new Session();
                    returnStatement = "Invalid Command.\n";
                }
                break;

            case WITHDRAWAL_SELECTED:
                try {
                    amount = Double.parseDouble(command);
                    if (this.withdrawal(amount)) {
                        returnStatement = "Final Balance: $" + this.checkBalance();
                    } else {
                        returnStatement = "Unable to withdraw the amount of $" + amount + ", \nThe current balance is: $" + this.checkBalance();
                    }

                } catch (NumberFormatException nfe) {
                    returnStatement = "Not a number !";
                }
                returnStatement += "\n";
                currentSession = new Session();
                break;

            case DEPOSIT_SELECTED:
                try {
                    amount = Double.parseDouble(command);
                    if (this.deposit(amount)) {
                        returnStatement = "Final Balance: $" + this.checkBalance();
                    } else {
                        returnStatement = "Unable to deposit the amount of $" + amount + ", \nThe current balance is: $" + this.checkBalance();
                    }
                } catch (NumberFormatException nfe) {
                    returnStatement = "Not a number !";
                }

                returnStatement += "\n";
                currentSession = new Session();
                break;

            default:
                currentSession = new Session();
                returnStatement = "No idea what's going on...";
                break;
        }

        return returnStatement;
    }

    public String getState() {
        return currentSession.state.name();
    }
}
