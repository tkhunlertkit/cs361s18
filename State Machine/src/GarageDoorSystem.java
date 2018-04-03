public class GarageDoorSystem {

    private StateMachine sm;
    private Light light;
    private Motor motor;

    public GarageDoorSystem() {
        sm = new StateMachine(this, true);
        motor = new Motor(true);
        light = new Light();
    }

    public void open() {
        this.motor.setDirection(false);
        this.motor.on();
    }

    public void close() {
        this.motor.setDirection(true);
        this.motor.on();
    }

    public void stop() {
        this.motor.off();
    }

    public void onClick() {
        sm.nextStateClick();
    }

    public void onSafety() {
        sm.nextStateSafety();
    }

    public void onLimit() {
        sm.nextStateLimit();
    }

    public void lClick() {
        light.click();
    }

    public void lightTimed() {
        light.onTimed();
    }

    public String getStatus() {
        return "Door State: " + sm.getState() + "\nLight: " + light.getState() + "\nMotor: " + motor.getState();
    }
}
