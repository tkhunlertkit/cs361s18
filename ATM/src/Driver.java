import java.util.Date;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {


        Hardware hw = new Hardware();
        ATM atm = new ATM(hw);
        hw.setATM(atm);
        atm.start();

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Command: ");
            atm.execute(s.nextLine());
        }

    }
}
