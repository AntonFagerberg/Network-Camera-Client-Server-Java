package local;

public class StateMonitor {
    public static final boolean
        MOVIE = true,
        IDLE = false;
    private boolean
        mode = IDLE,
        forceMode = false;
    private long lastTimeStamp = System.currentTimeMillis();

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

    public synchronized void synchronizeTimeStamps(int cameraIndex, long imageTimeStamp) {
        if (lastTimeStamp < imageTimeStamp) {
//            try {
                System.out.println("Waiting: " + (imageTimeStamp - lastTimeStamp));
//                wait(imageTimeStamp - lastTimeStamp);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        if (lastTimeStamp < imageTimeStamp)
            lastTimeStamp = imageTimeStamp;

        /*if (lastTimeStamp < 0) {
            lastTimeStamp = imageTimeStamp;
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (lastTimeStamp - imageTimeStamp > 0) {
//                System.out.println("Waiting: " + (lastTimeStamp - imageTimeStamp) + " for thread: " + Thread.currentThread().getId());
                try {
                    notify();
                    wait(lastTimeStamp - imageTimeStamp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastTimeStamp = -1;
        }   */
    }
}
