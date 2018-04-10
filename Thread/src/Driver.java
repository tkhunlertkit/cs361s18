import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        int numThread = 7;
        Thread[] threads = new Thread[numThread];
        SensorReciever[] sr = new SensorReciever[numThread];
        JFrame[] frames = new JFrame[numThread];
        long startTime = System.nanoTime();

        for (int i=0; i<numThread; i++) {
            sr[i] = new SensorReciever(Thread.currentThread(), startTime, i == (numThread - 1));
            threads[i] = new Thread(sr[i], "Sensor " + i);
            frames[i] = new SensorGUI(threads[i], i);
            threads[i].start();
        }

        try {
            threads[numThread - 1].join();
            System.out.println("\n--------------------------");
            System.out.println("Killing all threads:");
        } catch (InterruptedException e) {
            System.out.println("Should not be here.");
        }

        for (int i=0; i<numThread; i++) {
            sr[i].setKillFlag();
            threads[i].interrupt();
        }

        for (int i=0; i<numThread; i++) {
            while (threads[i].isAlive()) {
                String state = threads[i].isAlive() ? "Alive" : "Dead";
                System.out.println(threads[i].getName() + " is " + state);
            }
            String state = threads[i].isAlive() ? "Alive" : "Dead";
            System.out.println(threads[i].getName() + " is " + state);
            frames[i].dispose();
        }

        System.out.println("--------------------------");
        System.out.println("All threads killed, Bye.");
    }
}
