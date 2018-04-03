package Controller;

import java.util.Timer;
import java.util.TimerTask;

public class Light {

    public enum LightState implements State {
        ON, OFF
    }

    private GarageDoorSystem gds;
    private LightState ls;

    public Light(GarageDoorSystem gds) {
        this.gds = gds;
        ls = LightState.OFF;
        this.gds.notifyAllObservers();
    }

    public void on() {
        ls = LightState.ON;
        this.gds.notifyAllObservers();
    }

    public void off() {
        ls = LightState.OFF;
        this.gds.notifyAllObservers();
    }

    public void onTimed() {
        ls = LightState.ON;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                off();
                t.cancel();
            }
        }, 5000, 500);
    }

    public void click() {
        switch(this.ls) {
            case ON:
                this.off();
                break;
            case OFF:
                this.on();
                break;
            default:
                break;
        }
    }

    public State getState() {
        return ls;
    }

}
