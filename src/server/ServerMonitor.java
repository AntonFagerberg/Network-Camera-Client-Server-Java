package server;

public class ServerMonitor {
    private boolean idle = false;

    public synchronized void setMovie() {
        if (idle) {
            notifyAll();
        }
        idle = false;
    }

    public synchronized void motionDetect() {
        while (idle) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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