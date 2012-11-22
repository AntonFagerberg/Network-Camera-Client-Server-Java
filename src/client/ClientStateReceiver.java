package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientStateReceiver extends Thread {
    private ClientStateMonitor clientStateMonitor;
    private String url;
    private int port;

    public ClientStateReceiver(String url, int port, ClientStateMonitor clientStateMonitor) {
        this.url = url;
        this.port = port;
        this.clientStateMonitor = clientStateMonitor;
    }

    public void run() {
        while (true) {
            try {
                InputStream inputStream = (new Socket(url, port)).getInputStream();
                while (true) {
                    clientStateMonitor.setMode(inputStream.read());
                }
            } catch (IOException e) {
                System.out.println("[ClientStateReceiver] Connection failed: Sleeping 5 seconds...");
                try {
                    sleep(5000);
                } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
    }
}
