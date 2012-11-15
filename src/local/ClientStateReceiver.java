package local;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientStateReceiver extends Thread {
    private String url;
    private int port;
    private StateMonitor stateMonitor;

    public ClientStateReceiver(String url, int port, StateMonitor stateMonitor) {
        this.url = url;
        this.port = port;
        this.stateMonitor = stateMonitor;
    }

    public void run() {
        try {
            InputStream inputStream = (new Socket(url, port)).getInputStream();

            while (true) {
                inputStream.read();
                stateMonitor.setMode(StateMonitor.MOVIE);
            }
        } catch (IOException e) {
            System.out.println("Communication error with server: " + url + ":" + port + ".");
            e.printStackTrace();
        }
    }
}
