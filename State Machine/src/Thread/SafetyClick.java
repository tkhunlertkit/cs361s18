package Thread;

import Controller.GarageDoorSystem;

public class SafetyClick extends AbstractRemoteInterface {

    public SafetyClick(GarageDoorSystem garageDoorSystem) {
        this.gds = garageDoorSystem;
    }

    @Override
    public void run() {
        this.gds.onSafety();
    }
}
