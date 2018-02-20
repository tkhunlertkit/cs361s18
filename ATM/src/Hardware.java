public class Hardware {

    private ATM atm;

    public Hardware() {
        this(null);
    }

    public Hardware(ATM atm) {
        this.atm = atm;
    }

    public void setATM(ATM atm) {
        this.atm = atm;
    }

    public void display(String text) {
        System.out.println(text);
    }

    public void print(String time, String text) {
        System.out.println(time + " " + text);
    }

    public void dispense(String amount) {
        System.out.println("Here's your money: $" + amount);
    }

    public void execute(String rawCommand) {
        if (rawCommand.toLowerCase().startsWith("dispense")) {
            dispense(rawCommand.substring(9));
        } else if (rawCommand.toLowerCase().startsWith("print")) {
            print(atm.getTime(), rawCommand.substring(6));
        } else if (rawCommand.toLowerCase().startsWith("dis")) {
            display(rawCommand.substring(4));
        }
    }
}
