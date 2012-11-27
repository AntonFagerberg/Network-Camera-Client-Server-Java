package client;

public class HTTPMonitor {
	private byte[] JPEGData = new byte[1];

	public synchronized void storeImage(byte[] JPEGData) {
        if (this.JPEGData == null) {
            this.JPEGData = JPEGData;
            notify();
        }
	}
	
	public synchronized byte[] requestImage() {
        JPEGData = null;

        while (JPEGData == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

		return JPEGData;
	}
}
