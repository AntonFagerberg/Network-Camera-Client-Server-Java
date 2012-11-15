package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private ServerMonitor serverMonitor;
    private int port;
    private static final int
        MODE_MOVIE = 1;

    public ServerStateSender(int port, ServerMonitor serverMonitor) {
        this.port = port;
        this.serverMonitor = serverMonitor;
        System.out.println("ServerStateSender started on port: " + port + ".");
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            OutputStream  outputStream = serverSocket.accept().getOutputStream();
            System.out.println("ServerStateSender started at port: " + port + ".");

            while (true) {
                serverMonitor.isMovie();
                outputStream.write(MODE_MOVIE);
                System.out.println("Movie sent!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
