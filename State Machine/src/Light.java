import java.util.Timer;
import java.util.TimerTask;

public class Light {

    private enum LightState {
        ON, OFF
    }

    private LightState ls;

    public Light() {
        ls = LightState.OFF;
    }

    public void on() {
        ls = LightState.ON;
    }

    public void off() {
        ls = LightState.OFF;
    }

    public void onTimed() {
        ls = LightState.ON;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ls = LightState.OFF;
                System.out.println(ls.toString());
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

    public String getState() {
        return ls.toString();
    }

}
