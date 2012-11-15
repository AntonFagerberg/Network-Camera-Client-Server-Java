package local;

import common.JPEG;

import java.util.LinkedList;

public class ClientMonitor {
    private LinkedList[] queues = {
        new LinkedList<JPEG>(),
        new LinkedList<JPEG>()
    };

    public synchronized void storeJPEG(int camera, JPEG jpeg) {
        queues[camera].addLast(jpeg);
        notifyAll();
    }

    public synchronized JPEG getJPEG(int camera) {
        while (queues[camera].peekFirst() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return (JPEG) queues[camera].removeFirst();
    }
}
