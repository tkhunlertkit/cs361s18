import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {


        Hardware hw = new Hardware();
        ATM atm = new ATM(hw);
        hw.setATM(atm);
        atm.start();

        Scanner s = new Scanner(System.in);

        System.out.print("[f]ile or [c]onsole: ");
        String input = s.nextLine();
        if (input.toLowerCase().charAt(0) == 'f') {
               try (Scanner sc = new Scanner(new File("input.txt"))) {
                   while (sc.hasNextLine()) {
                       String line = sc.nextLine();
                       System.out.println(line);
                       atm.execute(line);

                   }
            } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
        } else {
            while (true) {
                System.out.print("Enter Command: ");
                atm.execute(s.nextLine());
            }
        }

    }
}
