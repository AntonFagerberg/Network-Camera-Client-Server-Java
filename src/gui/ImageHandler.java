package gui;

import common.JPEG;

import local.Monitor;
import se.lth.cs.realtime.JThread;

public class ImageHandler extends Thread {
	private Monitor monitor;
	private GUI2 gui;
	private JPEG jpeg;

	public ImageHandler(Monitor monitor, GUI2 gui) {
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
		System.out.println("test1");
		jpeg = monitor.getJPEG(0);
		System.out.println("test2");
		// Hämta två bilder
		// Delay, sync etc

	}

	private void refreshGUI() {
		gui.refreshImage(jpeg.getData());
	}
}
