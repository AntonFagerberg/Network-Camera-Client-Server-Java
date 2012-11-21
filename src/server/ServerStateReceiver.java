package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ServerStateReceiver extends Thread {
	ServerMonitor serverMonitor;
	String url;
	int port;

	public ServerStateReceiver(ServerMonitor serverMonitor, int port, String url) {
		this.url = url;
		this.port = port;
		this.serverMonitor = serverMonitor;
	}

	public void run() {
		try {
			InputStream inputStream = (new Socket(url, port)).getInputStream();

			while (true) {
				int msg = inputStream.read();
				serverMonitor.setMode(msg);
			}
		} catch (IOException e) {
			System.out.println("Communication error with server: " + url + ":"
					+ port + ".");
			e.printStackTrace();
		}
	}
}
