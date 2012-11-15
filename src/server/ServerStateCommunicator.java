package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateCommunicator extends Thread {
    private int port;
    private OutputStream outputStream;
    private ServerMonitor monitor;

    public ServerStateCommunicator(int port, ServerMonitor monitor) {
        this.port = port;
        this.monitor = monitor;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            outputStream = serverSocket.accept().getOutputStream();
            System.out.println("Started server state communicator at port: " + port + ".");
            while (true) {
                monitor.motionDetect();
                outputStream.write(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
