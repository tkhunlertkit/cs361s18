package UserInterface;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Controller.StateMachine.*;

public class GarageDoorStatus extends Observer {

    private static JLabel doorState;
    private static JLabel lightState;
    private static ImageIcon up;
    private static ImageIcon down;
    private static ImageIcon on;
    private static ImageIcon off;
    private Timer blink;
    private JFrame frame;

    public GarageDoorStatus(GarageDoorSystem gds) {
        this.gds = gds;
        this.gds.attach(this);

        doorState = new JLabel();
        lightState = new JLabel();

        frame = new JFrame();
        createContent(frame);
    }

    static {
        int imgWidth = 200;
        int imgHeight = 200;

        up   = new ImageIcon("up.png");
        down = new ImageIcon("down.png");
        on   = new ImageIcon(new ImageIcon("on.png").getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH));
        off  = new ImageIcon(new ImageIcon("off.png").getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH));
    }

    private void createContent(JFrame frame) {
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1, 0));

        JPanel temp = new JPanel();
        temp.add(doorState);
        main.add(temp);

        temp = new JPanel();
        temp.add(lightState);
        main.add(temp);

        frame.add(main);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        blink = new Timer(1000, e -> doorState.setVisible(!doorState.isVisible()));

        this.update();
    }

    @Override
    public void update() {
        doorState.setIcon(this.gds.isGoingUp() ? up : down);
        lightState.setIcon(this.gds.isLightOn() ? on : off);
        setBlinkStatus(this.gds.isMotorOn());
    }

    private void setBlinkStatus(boolean blinking) {
        if (blinking) {
            blink.start();
        } else {
            blink.stop();
            doorState.setVisible(true);
        }
    }

    public void kill() {
        frame.dispose();
    }
}
