package UserInterface;

import Controller.*;

import javax.swing.*;
import java.awt.*;

import static Controller.StateMachine.*;
import static Controller.StateMachine.DoorState.OPENING;

public class GarageDoorStatus extends Observer{

    private static JLabel doorState;
    private static JLabel lightState;
    private static ImageIcon up;
    private static ImageIcon down;
    private static ImageIcon on;
    private static ImageIcon off;

    public GarageDoorStatus(GarageDoorSystem gds) {
        this.gds = gds;
        this.gds.attach(this);

        doorState = new JLabel();
        lightState = new JLabel();

        JFrame frame = new JFrame();
        createContent(frame);
    }

    static {
        up   = new ImageIcon("up.png");
        down = new ImageIcon("down.png");
        on   = new ImageIcon("on.png");
        int imgWidth = 200;
        int imgHeight = 200;
        Image image = on.getImage();
        image = image.getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH);
        on = new ImageIcon(image);

        off = new ImageIcon(new ImageIcon("off.png").getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH));
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        this.update();
    }

    @Override
    public void update() {
        State i = this.gds.getDoorState();
        State l = this.gds.getLightState();

        if (DoorState.OPENING.equals(i)) {
            doorState.setIcon(up);
        }
        if (DoorState.OPENED.equals(i)) doorState.setIcon(up);
        if (DoorState.CLOSED.equals(i)) doorState.setIcon(down);
        if (DoorState.CLOSING.equals(i)) doorState.setIcon(down);
        if (DoorState.PAUSEOPENING.equals(i)) doorState.setIcon(up);
        if (DoorState.PAUSECLOSING.equals(i)) doorState.setIcon(down);

        if (Light.LightState.ON.equals(l)) lightState.setIcon(on);
        if (Light.LightState.OFF.equals(l)) lightState.setIcon(off);

    }
}
