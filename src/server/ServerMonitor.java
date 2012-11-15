package server;

public class ServerMonitor {
    private static final int IDLE_WAIT = 5000;
    private boolean idle = true;

    public synchronized void setMovie() {
        System.out.println("Movie!");
        if (idle) {
            idle = false;
            notifyAll();
        }
    }

    public synchronized void setIdle() {
        System.out.println("Idle!");
        if (!idle) {
            idle = true;
            notifyAll();
        }
    }

    public synchronized void delay() {
        if (idle) {
            System.out.println("Waiting...");
            try {
                wait(IDLE_WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}