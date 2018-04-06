package Thread;

public class ExitThread implements Runnable {

    private Thread mainThread;

    public ExitThread(Thread mainThread) {
        this.mainThread = mainThread;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            this.mainThread.interrupt();
        }
    }
}
