package UserInterface;

import Controller.GarageDoorSystem;
import Thread.ExitThread;

public class Driver {

    public static void main(String[] argvs) {
        GarageDoorSystem gds = new GarageDoorSystem();
        new RemoteGui(gds);
        new GarageDoorStatus(gds);

        Thread console = new Thread(new Console(gds, Thread.currentThread()), "console");
        Thread exit = new Thread(new ExitThread(Thread.currentThread()), "exit");
        console.start();

        int numThreadActive = 1;
        while (numThreadActive > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.out.println("Notified from other thread ");
                ie.printStackTrace();
            }
            numThreadActive = 0;
            if (console.isAlive()) {
                numThreadActive++;
            }
        }

    }
}
