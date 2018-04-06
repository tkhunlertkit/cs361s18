package Thread;

import Controller.GarageDoorSystem;

public class DoorClickThread extends AbstractRemoteInterface {

    public DoorClickThread(GarageDoorSystem garageDoorSystem) {
        this.gds = garageDoorSystem;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            this.gds.onClick();
        }

    }
}
