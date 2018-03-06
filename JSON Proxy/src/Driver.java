import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        DirectoryEditor de = new DirectoryEditor();

        try (Scanner s = new Scanner(System.in)) {
            String in;

            while (true) {
                in = s.nextLine();
                if (in.toLowerCase().startsWith("exit")) {
                    break;
                }
                de.execute(in);
            }
        }



    }
}
