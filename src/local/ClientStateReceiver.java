package local;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientStateReceiver extends Thread {
    private InputStream inputStream;
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
            inputStream = (new Socket(url, port)).getInputStream();
            while (true) {
                System.out.println(inputStream.read());
                stateMonitor.setMovie();

            }
        } catch (IOException e) {
            System.out.println("Communication error with server: " + url + ":" + port + ".");
            e.printStackTrace();
        }
    }
}
