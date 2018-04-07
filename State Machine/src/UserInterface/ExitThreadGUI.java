package UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitThreadGUI implements Runnable {

    private Thread mainThread;
    private Thread thisThread;

    public ExitThreadGUI(Thread mainThread) {
        this.mainThread = mainThread;

        JFrame frame = new JFrame();
//        JPanel main = new JPanel();
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisThread.interrupt();
            }
        });
        frame.add(exit);
        frame.setSize(100, 100);
        frame.setVisible(true);
    }


    @Override
    public void run() {
        thisThread = Thread.currentThread();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
