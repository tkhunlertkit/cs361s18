package UserInterface;

import Controller.GarageDoorSystem;
import Controller.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console extends Observer implements Runnable {

    private Thread mainThread;

    public Console(GarageDoorSystem gds, Thread mainThread) {
        this.gds = gds;
        this.gds.attach(this);
        this.mainThread = mainThread;
    }

    private void execute(String command) {
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

    @Override
    public void update() {
        System.out.println(this.gds);
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    while (!br.ready()) {
                        Thread.sleep(1000);
                    }
                    execute(br.readLine());
                } catch (IOException e) {
                    System.out.println("Error reading command");
                }
            }
        } catch (InterruptedException e) {

        }
    }
}
