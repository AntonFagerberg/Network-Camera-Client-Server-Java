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
        InputStream inputStream = null;

        while (true) {
            try {
                socket = new Socket(url, port);
            } catch (IOException e) {
                System.err.println("[" + currentThread().getId() + "] ClientStateReceiver: failed to create ServerSocket on port :" + port + ". Will retry in 5 seconds.");
                try { sleep(5000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }

            if (socket != null) {
                try {
                    inputStream = socket.getInputStream();
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] ClientStateReceiver: failed to get InputStream.");
                }

                if (inputStream != null) {
                    try {
                        while (true) {
                            int mode = inputStream.read();
                            clientStateMonitor.setMode(mode);
                            gui.changeMovieMode(mode, url);

                        }
                    } catch (IOException e) {
                        System.err.println("[" + currentThread().getId() + "] ClientStateReceiver: InputStream aborted.");
                    }

                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        System.err.println("[" + currentThread().getId() + "] ClientStateReceiver: failed to close InputStream.");
                    }
                    inputStream = null;
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("[" + currentThread().getId() + "] ClientStateReceiver: failed to close ServerSocket.");
                }
            }
        }
    }
}
