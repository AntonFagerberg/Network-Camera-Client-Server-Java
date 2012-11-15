package local;

import common.JPEG;

import java.util.LinkedList;

public class Monitor {
    private LinkedList[] queues = {
        new LinkedList<JPEG>(),
        new LinkedList<JPEG>()
    };

    private boolean idle = false;

    public synchronized void storeJPEG(int camera, JPEG jpeg) {
        queues[camera].addLast(jpeg);
        notifyAll();
    }

    public synchronized JPEG getJPEG(int camera) {
        while (queues[camera].peekFirst() != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return (JPEG) queues[camera].removeFirst();
    }

    public synchronized void setMovie() {
        idle = false;
    }

    public synchronized void setIdle() {
        idle = true;
    }

    public synchronized boolean isIdle() {
        return idle;
    }
}
