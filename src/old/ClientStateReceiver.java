package old;

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
            System.out.println("ClientStateReceiver started at port: " + port + ".");
            while (true) {
                inputStream.read();
                System.out.println("ClientStateReceiver - received");
                stateMonitor.setMode(StateMonitor.MOVIE);
            }
        } catch (IOException e) {
            System.out.println("Communication error with server: " + url + ":" + port + ".");
            e.printStackTrace();
        }
    }
}
