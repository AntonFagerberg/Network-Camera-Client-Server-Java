package local;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ClientStateSender extends Thread {
	private StateMonitor stateMonitor;
	private int port;

	public ClientStateSender(StateMonitor stateMonitor, int port) {
		this.port = port;
		this.stateMonitor = stateMonitor;
		System.out.println("ClientStateSender started on port: " + port + ".");
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			OutputStream outputStream = serverSocket.accept().getOutputStream();
			System.out.println("ClientStateSender started at port: " + port
					+ ".");
			int mode = -1;
			while(true){
				mode = stateMonitor.getMode(mode);
				outputStream.write(mode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
