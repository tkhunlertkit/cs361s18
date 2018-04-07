package UserInterface;

import Controller.GarageDoorSystem;
import Controller.Observer;

import javax.swing.*;
import java.util.Scanner;

public class Console extends Observer implements Runnable {

    private Thread mainThread;

    public Console(GarageDoorSystem gds, Thread mainThread) {
        this.gds = gds;
        this.gds.attach(this);
        this.mainThread = mainThread;
    }

    private void inputCommands() {
        Scanner s = new Scanner(System.in);
        String command = "";
        while(true) {
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

    @Override
    public void run() {
        inputCommands();
    }
}
