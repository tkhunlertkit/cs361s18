package UserInterface;

import Controller.GarageDoorSystem;

public class Driver {

    public static void main(String[] argvs) {
        GarageDoorSystem gds = new GarageDoorSystem();
        new RemoteGui(gds);
        new GarageDoorStatus(gds);
        new Console(gds);

    }
}
