import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        ATM atm = new ATM();
        atm.start();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.print(atm.getStatement());
            System.out.println(atm.execute(s.nextLine()));
        }

    }
}
