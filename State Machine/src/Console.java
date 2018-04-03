import java.util.Scanner;

public class Console {

    private GarageDoorSystem gds;

    public Console(GarageDoorSystem gds) {
        this.gds = gds;
        inputCommands();
    }

    private void inputCommands() {
        Scanner s = new Scanner(System.in);
        String command = "";
        while(true) {
            System.out.print("Enter Command: ");
            command = s.next();
            switch(command.toLowerCase()) {
                case "dclick":
                    this.gds.onClick();
                    break;
                case "lclick":
                    this.gds.lClick();
                    break;
                case "limit":
                    this.gds.onLimit();
                    break;
                case "safety":
                    this.gds.onSafety();
                    break;
                case "off":
                    System.exit(0);
                    break;
                default:
                    break;
            }
            System.out.println(this.gds.getStatus());
        }
    }

}
