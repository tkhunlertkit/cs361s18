import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        ATM atm = new ATM();
        Scanner s = new Scanner(System.in);

        while (true) {
            String accountNumber;
            String pin;
            boolean withdrawal, deposit;

            System.out.print("Enter a card number: ");
            accountNumber = s.next();
            System.out.print("Enter a pin: ");
            pin = s.next();
            if (atm.validate(accountNumber, pin)) {
                System.out.print("Enter an operation [w]ithdrawal / [d]eposit: ");
                char entered = s.next().charAt(0);
                withdrawal = entered == 'w';
                deposit = entered == 'd';
                if (withdrawal || deposit) {
                    System.out.print("Please enter the amount: ");
                    double amount = s.nextDouble();
                    if (withdrawal) {
                        atm.withdrawal(amount);
                    } else {
                        atm.deposit(amount);
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }

            System.out.println("Final Balance: " + atm.checkBalance());

        }

    }
}
