import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SensorGUI extends JFrame{

    private Thread sensorThread;
    private static final int width = 200;
    private static final int height = 100;

    public SensorGUI(Thread sensorThread, int id) {
        this.sensorThread = sensorThread;
        createContent(id);
        this.setLocation(id * width, 0);
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Sensor " + id);
    }

    private void createContent(int id) {
        JButton jb = new JButton("Sensor " + id);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sensorThread.interrupt();
            }
        });
        this.add(jb);
    }
}
