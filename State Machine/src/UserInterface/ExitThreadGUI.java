package UserInterface;

import javax.swing.*;

public class ExitThreadGUI implements Runnable {

    private Thread mainThread;
    private Thread thisThread;
    private JFrame frame;

    public ExitThreadGUI(Thread mainThread) {
        this.mainThread = mainThread;

        frame = new JFrame();
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            thisThread.interrupt();
            frame.dispose();
        });
        frame.add(exit);
        frame.setSize(100, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
