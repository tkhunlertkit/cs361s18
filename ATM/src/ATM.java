import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
        public String time;

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
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Hardware hw;

    public ATM(Hardware hw) {
        this.hw = hw;
    }

    public void start() {
        b = Bank.getBank();
        currentSession = new Session();
    }

    public double withdrawal(double amount) {
        return b.withdraw(currentSession.accountNumber, currentSession.pin, amount);
    }

    public boolean deposit(double amount) {
        return b.deposit(currentSession.accountNumber, currentSession.pin, amount);
    }

    public double checkBalance() {
        return b.checkBalance(currentSession.accountNumber, currentSession.pin);
    }

    @Deprecated
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

    public void checkAccount(String accountNumber) {
        if (b.checkAccount(accountNumber)) {
            currentSession.accountNumber = accountNumber;
            currentSession.state = currentSession.state.next();
            hw.execute("DIS \"Enter PIN\"");
        } else {
            currentSession = new Session();
            hw.execute("DIS \"Invalid account number\"");
        }
    }

    public void validateAccount(String pinNumber) {
        if (b.validate(currentSession.accountNumber, pinNumber)) {
            currentSession.pin = pinNumber;
            currentSession.state = currentSession.state.next();
            hw.execute("DIS \"Choose Transaction\"");
        } else {
            hw.execute("DIS \"Invalid Account information\"");
        }
    }

    public void withdraw(String amountAsString) {
        double amount;
        try {
            amount = Double.parseDouble(amountAsString);
            amount = withdrawal(amount);
            hw.execute("DISPENSE " + amount);
            hw.execute("PRINT \"Final Balance: $" + this.checkBalance() + "\"");
            currentSession.state = State.ACCOUNT_VALIDATED;
        } catch (NumberFormatException nfe) {
            hw.execute("DIS \"Not a number !\"");
            currentSession = new Session();
        }


    }

    public void deposit(String amountString) {
        double amount;
        try {
            amount = Double.parseDouble(amountString);
            if (this.deposit(amount)) {
                hw.execute("DIS \"Final Balance: $" + this.checkBalance() + "\"");
            } else {
                hw.execute("PRINT \"Unable to deposit the amount of $" + amount + ", \nThe current balance is: $" + this.checkBalance() + "\"");
            }
            currentSession.state = State.ACCOUNT_VALIDATED;
        } catch (NumberFormatException nfe) {
            hw.execute("DIS \"Not a number !\"");
            currentSession = new Session();
        }


    }

    public void accountOperation(String param) {

        switch (currentSession.state) {
            case ACCOUNT_NUM_ENTERED: validateAccount(param); break;
            case WITHDRAWAL_SELECTED: withdraw(param);        break;
            case DEPOSIT_SELECTED:    deposit(param);         break;
            default: hw.execute("DIS \"Invalid Command\""); break;
        }
    }

    public void selectOperation(String parameter) {
        parameter = parameter.toLowerCase();
        if (parameter.startsWith("w")) {
            currentSession.state = State.WITHDRAWAL_SELECTED;
            hw.execute("DIS \"Withdrawal Selected.\"");
        } else if (parameter.startsWith("d")) {
            currentSession.state = State.DEPOSIT_SELECTED;
            hw.execute("DIS \"Deposit Selected.\"");
        } else if (parameter.startsWith("cb")) {
            hw.execute("PRINT \"The current amount is: $" + checkBalance() + "\"");
        } else if (parameter.startsWith("cancel")) {
            hw.execute("EJECT");
            hw.execute("DIS \"Card Ejected\"");
            currentSession = new Session();
        } else {
            hw.execute("DIS \"Not an operation\"");
        }
    }

    // command: Time command parameter
    public void execute(String command) {
        ArrayList<String> brokenCommand = new ArrayList(Arrays.asList(command.split(" ")));

        if (brokenCommand.size() < 3) {
            brokenCommand.add(0, new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }

        try {
            currentSession.time = brokenCommand.get(0);
            String givenCommand = brokenCommand.get(1);
            String parameter = brokenCommand.get(2);

            switch(givenCommand.toUpperCase()) {
                case "CARDREAD": checkAccount(parameter); break;
                case "NUM": accountOperation(parameter); break;
                case "BUTTON": selectOperation(parameter); break;
                default:
                    hw.execute("DIS \"No idea what's going on...\"");
                    break;
            }

        } catch (IndexOutOfBoundsException e) {
            hw.execute("DIS \"Invalid Command\"");
        } catch (AccountNotExistedException e) {
            hw.execute("DIS \"Session timed out, Please insert your card\"");
            currentSession = new Session();
        }


    }

    public String getState() {
        return currentSession.state.name();
    }

    public String getTime() {
        return currentSession.time;
    }
}
