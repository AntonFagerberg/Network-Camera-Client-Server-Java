package gui;

import common.JPEG;

import local.ClientMonitor;

public class ImageHandler extends Thread {
	private ClientMonitor monitor;
	private GUI2 gui;
	private JPEG jpeg;

	public ImageHandler(ClientMonitor monitor, GUI2 gui) {
		this.monitor = monitor;
		this.gui = gui;
	}

	public void run() {
		while (true) {
			fetchImage();
			refreshGUI();
		}
	}

	private void fetchImage() {
		jpeg = monitor.getJPEG(0);
		// Hämta två bilder
		// Delay, sync etc

	}

	private void refreshGUI() {
		gui.refreshImage(jpeg.getData());
	}
}
