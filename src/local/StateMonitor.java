package local;

public class StateMonitor {
    public static final boolean
        MOVIE = true,
        IDLE = false;
    private boolean
        mode = IDLE,
        forceMode = false;

    public synchronized boolean getMode() {
        return mode;
    }

    public synchronized void setMode(boolean mode) {
        if (!forceMode) {
            this.mode = mode;
        }
    }

    public synchronized void setForcedMode(boolean mode) {
        forceMode = true;
        this.mode = mode;
    }

    public synchronized void unsetForcedMode() {
        forceMode = false;
    }
}
