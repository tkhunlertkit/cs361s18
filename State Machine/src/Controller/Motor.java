package Controller;

public class Motor {

    private enum MotorDirection {
        UP, DOWN;

        public static MotorDirection reverse(MotorDirection md) {
            if (md == UP) return DOWN;
            else return UP;
        }
    }

    private MotorDirection mDir;
    private boolean inMotion;
    private GarageDoorSystem gds;

    public Motor(GarageDoorSystem gds, boolean goingdown) {
        this.mDir = goingdown ? MotorDirection.DOWN : MotorDirection.UP;
        this.inMotion = false;
        this.gds = gds;
    }

    public void on() {
        this.inMotion = true;
        this.gds.notifyAllObservers();
    }

    public void off() {
        this.inMotion = false;
        this.gds.notifyAllObservers();
    }

    public void reverse() {
        this.mDir = MotorDirection.reverse(this.mDir);
        this.gds.notifyAllObservers();
    }

    public void setDirection(boolean goingDown) {
        this.mDir = goingDown ? MotorDirection.DOWN : MotorDirection.UP;
        this.gds.notifyAllObservers();
    }

    public boolean inMotion() {
        return inMotion;
    }
}
