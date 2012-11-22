package client;

public class ClientStateMonitor {
    public final static int
            IDLE = 0,
            MOVIE = 1,
            IDLE_FORCED = 2,
            MOVIE_FORCED = 3;
    private int mode = IDLE;

    public synchronized int getModeBlocking() {
        try {
            wait();
        } catch (InterruptedException e) { e.printStackTrace(); }

        return mode;
    }

    public synchronized void setMode(int newMode) {
        if (mode != newMode && (newMode == IDLE || newMode ==  MOVIE || newMode == IDLE_FORCED || newMode == MOVIE_FORCED)) {
            mode = newMode;
            notifyAll();
        }
    }
}
