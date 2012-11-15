package server;

import se.lth.cs.fakecamera.Axis211A;

public class ServerMonitor {
    private byte[] JPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
    private int length = -1;
    private final static long IDLE_WAIT = 5000;
    private boolean modeMovie = false,
                    transferredModeMovie = false;

    public synchronized void storeJPEGData(byte[] JPEGData, int length) {
        this.length = length;
        this.JPEGData = JPEGData;
    }

    public synchronized void setMovie() {
        System.out.println("Movie!");
        modeMovie = true;
        transferredModeMovie = true;
        notifyAll();
    }

    public synchronized void unsetMovie() {
        System.out.println("Idle!");
        modeMovie = false;
    }

    public synchronized void isMovie() {
        while (!transferredModeMovie) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        transferredModeMovie = true;
    }

    public synchronized int fillData(byte[] JPEGData) {
        if (!modeMovie || length < 0) {
            try {
                wait(IDLE_WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.arraycopy(this.JPEGData, 0, JPEGData, 0, JPEGData.length);
        return length;
    }
}