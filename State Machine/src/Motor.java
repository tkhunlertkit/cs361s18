public class Motor {

    private boolean goingDown;
    private boolean onMotion;

    public Motor(boolean goingdown) {
        this.goingDown = goingdown;
        this.onMotion = false;
    }

    public void on() {
        this.onMotion = true;
    }

    public void off() {
        this.onMotion = false;
    }

    public void reverse() {
        this.goingDown = !this.goingDown;
    }

    public void setDirection(boolean goingDown) {
        this.goingDown = goingDown;
    }

    public String getState() {
        return this.onMotion ? "ON" : "OFF";
    }
}
