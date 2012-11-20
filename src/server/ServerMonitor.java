package server;

import se.lth.cs.fakecamera.Axis211A;

public class ServerMonitor {
    private byte[] JPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
    private int length = -1;
    private final static long IDLE_WAIT_TIME = 5000;
    private final static boolean
        MOVIE = false,
        IDLE = true;
    private boolean
        mode = false,
        transferMode = IDLE;
    private boolean forced;

    public synchronized void storeJPEGData(byte[] JPEGData, int length) {
        this.length = length;
        this.JPEGData = JPEGData;
        forced = false;
    }

    public synchronized int fetchJPEGData(byte[] JPEGData) {
        if (mode == IDLE) {
            try {
                wait(IDLE_WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.arraycopy(this.JPEGData, 0, JPEGData, 0, JPEGData.length);
        return length;
    }

    public synchronized void setMovie() {
    	if(!forced) {
        mode = MOVIE;
        transferMode = MOVIE;
        notify();
    	}
    }

    public synchronized void unsetMovie() {
        if (!forced) {
        	mode = IDLE;
        }
    }

    public synchronized void isMovie() {
        while (transferMode == IDLE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        transferMode = IDLE;
    }
    
    public synchronized void setForced(boolean forced) {
    	this.forced = forced;
    }
}