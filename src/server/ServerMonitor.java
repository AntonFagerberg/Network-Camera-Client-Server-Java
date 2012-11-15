package server;

public class ServerMonitor {
    private static final int IDLE_WAIT = 5000;
    private boolean
            idle = false,
            wasIdle = false;

    public synchronized void setMovie() {
        if (idle) {
            idle = false;
            notifyAll();
        }
    }

    public synchronized void setIdle() {
        if (!idle) {
            idle = true;
            notifyAll();
        }
    }

    public synchronized void delay() {
        if (idle) {
            try {
                wait(IDLE_WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}