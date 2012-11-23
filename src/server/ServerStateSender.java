package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private int port, mode;
    private ServerStateMonitor serverStateMonitor;

    public ServerStateSender(int port, ServerStateMonitor serverStateMonitor) {
        this.serverStateMonitor = serverStateMonitor;
        this.port = port;
    }

    public void run() {
        try {
            OutputStream outputStream = (new ServerSocket(port)).accept().getOutputStream();
            while (true) {
                outputStream.write(serverStateMonitor.getModeBlocking());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
