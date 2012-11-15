package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private int port;
    private OutputStream outputStream;
    private ServerMonitor serverMonitor;

    public ServerStateSender(int port, ServerMonitor serverMonitor) {
        this.port = port;
        this.serverMonitor = serverMonitor;
        System.out.println("ServerStateSender started on port: " + port + ".");
    }

    public void run() {
        while (true) {
            serverMonitor.isMovie();
        }
    }
}
