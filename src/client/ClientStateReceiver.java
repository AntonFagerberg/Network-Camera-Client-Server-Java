package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientStateReceiver extends Thread {
    private ClientStateMonitor clientStateMonitor;
    private String url;
    private int port;
    private GUI gui;

    public ClientStateReceiver(String url, int port, ClientStateMonitor clientStateMonitor, GUI gui) {
        this.url = url;
        this.port = port;
        this.clientStateMonitor = clientStateMonitor;
        this.gui = gui;
    }

    public void run() {
        Socket socket = null;
        InputStream inputStream;
        int mode;

        try {
            socket = new Socket(url, port);
            inputStream = socket.getInputStream();

            while (true) {
                mode = inputStream.read();
                if (mode == -1) {
                    throw new IOException("Stream closed.");
                }
                clientStateMonitor.setMode(mode);
                gui.changeMovieMode(mode, url);
            }
        } catch (IOException e) {
            System.out.println("[ClientStateReceiver] No connection to client: " + url + " on port: " + port + ". Reconnecting in 1 second.");
            try { sleep(1000); } catch (InterruptedException e1) { e1.printStackTrace(); }
        }
    }
}
