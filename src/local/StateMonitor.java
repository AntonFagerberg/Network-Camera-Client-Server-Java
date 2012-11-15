package local;

public class StateMonitor {
    private boolean idle = false;

    public synchronized void setMovie() {
        idle = false;
        notifyAll();
    }

    public synchronized void setIdle() {
        idle = false;
        notifyAll();
    }

    public synchronized void idle() throws InterruptedException {
        while (idle) {
            wait();
        }
    }

    public synchronized void movie() throws InterruptedException {
        while (!idle) {
            wait();
        }
    }
}
