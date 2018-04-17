package UserInterface;

import Controller.GarageDoorSystem;

import javax.swing.*;

public class Driver {

    public static void main(String[] argvs) {
        GarageDoorSystem gds = new GarageDoorSystem();
        JFrame remote = new RemoteGui(gds);
        GarageDoorStatus gdstatus = new GarageDoorStatus(gds);

        Thread consoleThread = new Thread(new Console(gds, Thread.currentThread()), "console");
        consoleThread.start();

        Thread exit = new Thread(new ExitThreadGUI(Thread.currentThread()), "exit");
        exit.start();
        try {
            exit.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        consoleThread.interrupt();
        gdstatus.kill();
        remote.dispose();
    }
}
