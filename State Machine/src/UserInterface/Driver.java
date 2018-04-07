package UserInterface;

import Controller.GarageDoorSystem;

public class Driver {

    public static void main(String[] argvs) {
        GarageDoorSystem gds = new GarageDoorSystem();
        new RemoteGui(gds);
        new GarageDoorStatus(gds);

        Thread console = new Thread(new Console(gds, Thread.currentThread()), "console");
        console.start();

        Thread exit = new Thread(new ExitThreadGUI(Thread.currentThread()), "exit");
        exit.start();
        try {
            exit.join();
            System.out.println("Exit Thread Done.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Exit: Bye Bye");
        System.exit(0);
    }
}
