package local;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ClientStateSender extends Thread {
    private StateMonitor stateMonitor;
    private int port;
    private OutputStream outputStream;

    public ClientStateSender(int port, StateMonitor stateMonitor) {
        this.stateMonitor = stateMonitor;
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            outputStream = serverSocket.accept().getOutputStream();

            while (true) {
                try {
                    stateMonitor.idle();
                    outputStream.write(1);
                    stateMonitor.movie();
                    outputStream.write(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
