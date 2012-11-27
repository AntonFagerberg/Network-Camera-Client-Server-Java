package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientStateSender extends Thread {
    private ClientStateMonitor clientStateMonitor;
    private int port;
    public volatile boolean alive;

    public ClientStateSender(int port, ClientStateMonitor clientStateMonitor) {
        this.clientStateMonitor = clientStateMonitor;
        this.port = port;
        alive = true;
    }
    public void run() {
        try {
            OutputStream outputStream = (new ServerSocket(port)).accept().getOutputStream();
            while (alive) {
                outputStream.write(clientStateMonitor.getModeBlocking());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("See ya");
    }
   
}
