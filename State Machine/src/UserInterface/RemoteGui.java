package UserInterface;

import Controller.GarageDoorSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoteGui extends JFrame{

    private GarageDoorSystem gds;
//    private Thread doorThread, safetyThread, limitThread, lightThread;

    public RemoteGui(GarageDoorSystem gds) {//, Thread doorThread, Thread safetyThread, Thread limitThread, Thread lightThread) {
        this.gds = gds;
//        this.doorThread = doorThread;
//        this.safetyThread = safetyThread;
//        this.limitThread = limitThread;
//        this.lightThread = lightThread;

        creteContent();
        this.setSize(120, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void creteContent() {
        JPanel main = new JPanel();
        JButton dClick = new JButton("Door");
        JButton lClick = new JButton("Light");
        JButton safety = new JButton("Safety");
        JButton limit = new JButton("Limit");

        dClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gds.onClick();
            }
        });

        lClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gds.lClick();
            }
        });

        safety.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gds.onSafety();
            }
        });

        limit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gds.onLimit();
            }
        });

        main.setLayout(new GridLayout(0, 1));

        JPanel button = new JPanel();
        button.add(dClick);
        main.add(button);

        button = new JPanel();
        button.add(lClick);
        main.add(button);

        button = new JPanel();
        button.add(safety);
        main.add(button);

        button = new JPanel();
        button.add(limit);
        main.add(button);

        this.add(main);

    }

}
