package Controller;

import java.util.ArrayList;
import java.util.List;

public class GarageDoorSystem {

    private StateMachine sm;
    private Light light;
    private Motor motor;
    private List<Observer> observers = new ArrayList<>();

    public GarageDoorSystem() {
        sm = new StateMachine(this, true);
        motor = new Motor(this,true);
        light = new Light(this);
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
        notifyAllObservers();
    }

    public void lightTimed() {
        light.onTimed();
    }

    public boolean isGoingUp() {
        return sm.isGoingUp();
    }

    public boolean isLightOn() {
        return light.isLightOn();
    }

    public boolean isMotorOn() {
        return motor.inMotion();
    }

//    public State getDoorState() {
//        return sm.getState();
//    }
//
//    public State getLightState() {
//        return light.getState();
//    }
//
//    public State getMotorState() {
//        return motor.getState();
//    }

    public void attach(Observer obs) {
        this.observers.add(obs);
    }

    public void notifyAllObservers() {
        observers.forEach(o -> o.update());
    }

    @Override
    public String toString() {
        return "\nDoor State: " + (sm.isGoingUp() ? "UP" : "Down") +
                "\nLight: " + (light.isLightOn() ? "ON" : "OFF") +
                "\nMotor: " + (motor.inMotion() ? "ON" : "OFF");
    }
}
