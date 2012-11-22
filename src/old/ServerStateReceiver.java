package old;

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
			System.out.println("ServerStateReceiver started at port: " + port + ".");
			while (true) {
				System.out.println("ServerStateReceiver waiting for msg");
				int msg = inputStream.read();
				serverMonitor.setMode(msg);
				System.out.println("Received inputStream message: " + msg);
			}
		} catch (IOException e) {
			System.out.println("Communication error with server: " + url + ":"
					+ port + ".");
			e.printStackTrace();
		}
	}
}
