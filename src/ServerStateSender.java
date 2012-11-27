import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private ServerStateMonitor serverStateMonitor;
    private int port;

    public ServerStateSender(int port, ServerStateMonitor serverStateMonitor) {
        System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: started.");
        this.serverStateMonitor = serverStateMonitor;
        this.port = port;
    }

    public void run() {
        while (true) {
            try {
                OutputStream outputStream = (new ServerSocket(port)).accept().getOutputStream();
                while (true) {
                    System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: waiting for change.");
                    outputStream.write(serverStateMonitor.getModeBlocking());
                    System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: change sent.");
                }
            } catch (IOException e) {
                System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: outputStream closed. Reconnecting in 5 seconds.");
                try {
                    sleep(5000);
                } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
    }
}
