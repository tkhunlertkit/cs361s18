public class SensorReciever implements Runnable {

    private Thread mainThread;
    private boolean killFlag;
    private long startTime;

    public SensorReciever(Thread mainThread, long startTime, boolean lastThread) {
        this.mainThread = mainThread;
        this.startTime = startTime;
        this.killFlag = lastThread;
    }

    public void setKillFlag() {
        this.killFlag = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                String threadName = Thread.currentThread().getName();
                long elapsedTime = System.nanoTime() - startTime;
                double seconds = elapsedTime / 1e9;
                System.out.println(threadName + " was interrupted at " + seconds + " seconds");
                if (killFlag) {
                    return;
                }
            }
        }

    }
}
