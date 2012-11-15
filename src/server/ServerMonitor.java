package server;

public class ServerMonitor {
    private boolean
            idle = false,
            hasChanged = false;

    public synchronized void setMovie() {
        if (idle) {
            System.out.println("Detected motion bieacch!");
            hasChanged = true;
            notifyAll();
        }

        idle = false;
    }

    public synchronized void motionDetect() {
        while (!hasChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        hasChanged = false;
    }

    public synchronized void setIdle() {
        idle = true;

        try {
            wait(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}