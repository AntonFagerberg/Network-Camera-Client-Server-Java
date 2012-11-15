package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private int port;
    private OutputStream outputStream;
    private ServerMonitor monitor;

    public ServerStateSender(int port, ServerMonitor monitor) {
        this.port = port;
        this.monitor = monitor;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            outputStream = serverSocket.accept().getOutputStream();
            while (true) {
                monitor.motionDetect();
                outputStream.write(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
