package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ClientStateSender extends Thread {
    private ClientStateMonitor clientStateMonitor;
    private int port;
    public volatile boolean alive;

    public ClientStateSender(int port, ClientStateMonitor clientStateMonitor) {
        this.clientStateMonitor = clientStateMonitor;
        this.port = port;
    }
    public void run() {
        ServerSocket serverSocket = null;
        OutputStream outputStream = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("[" + currentThread().getId() + "] ClientStateSender: failed to create Socket on port: " + port + ". Will retry in 5 seconds.");
            try { sleep(5000); } catch (InterruptedException e1) { e1.printStackTrace(); }
        }

        if (serverSocket != null) {

            try {
                outputStream = serverSocket.accept().getOutputStream();
            } catch (IOException e) {
                System.err.println("[" + currentThread().getId() + "] ClientStateSender: failed to get OutputStream.");
            }

            if (outputStream != null) {
                try {
                    while (true) {
                        outputStream.write(clientStateMonitor.getModeBlocking());
                        System.out.println("Sending...");
                    }
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] ClientStateSender: OutputStream aborted.");
                }

                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] ClientStateSender: failed to close OutputStream.");
                }
                outputStream = null;
            }

            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("[" + currentThread().getId() + "] ClientStateSender: failed to close Socket.");
            }
            serverSocket = null;
        }
    }
}
