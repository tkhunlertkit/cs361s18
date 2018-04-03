package UserInterface;

import Controller.GarageDoorSystem;
import Controller.Observer;

import javax.swing.*;
import java.util.Scanner;

public class Console extends Observer {

    public Console(GarageDoorSystem gds) {
        this.gds = gds;
        this.gds.attach(this);
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
        }
    }

    @Override
    public void update() {
        System.out.println(this.gds);
    }
}
