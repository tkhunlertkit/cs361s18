package Controller;

public class StateMachine {

    private enum DoorState {
        CLOSED,
        OPENED,
        OPENING,
        CLOSING,
        PAUSEOPENING,
        PAUSECLOSING
    }

    private DoorState dState;
    private GarageDoorSystem gds;

    public StateMachine(GarageDoorSystem garageDoorSystem, boolean closedState) {
        dState = closedState ? DoorState.CLOSED : DoorState.OPENED;
        this.gds = garageDoorSystem;
    }

    public void nextStateClick() {
        boolean update = true;
        switch(this.dState) {
            case CLOSED:
                this.dState = DoorState.OPENING;
                this.gds.open();
                this.gds.lightTimed();
                break;
            case OPENING:
                this.dState = DoorState.PAUSEOPENING;
                this.gds.stop();
                break;
            case PAUSEOPENING:
                this.dState = DoorState.CLOSING;
                this.gds.close();
                break;
            case OPENED:
                this.dState = DoorState.CLOSING;
                this.gds.close();
                break;
            case CLOSING:
                this.dState = DoorState.PAUSECLOSING;
                this.gds.stop();
                break;
            case PAUSECLOSING:
                this.dState = DoorState.OPENING;
                this.gds.open();
                break;
            default:
                update = false;
                break;
        }
        if (update) {
            this.gds.notifyAllObservers();
        }
    }

    public void nextStateSafety() {
        this.gds.open();
        this.dState = DoorState.OPENING;
        this.gds.notifyAllObservers();
    }

    public void nextStateLimit() {
        boolean update = true;
        switch(this.dState) {
            case OPENING:
            case PAUSEOPENING:
                this.dState = DoorState.OPENED;
                this.gds.stop();
                break;

            case CLOSING:
            case PAUSECLOSING:
                this.dState = DoorState.CLOSED;
                this.gds.stop();
                break;

            default:
                update = false;
                break;
        }

        if (update) this.gds.notifyAllObservers();
    }

    public boolean isGoingUp() {
        return dState == DoorState.OPENING ||
                dState == DoorState.OPENED ||
                dState == DoorState.PAUSEOPENING;
    }
}
