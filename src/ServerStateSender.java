import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private int port;
    private ServerStateMonitor serverStateMonitor;

    public ServerStateSender(int port, ServerStateMonitor serverStateMonitor) {
        System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: started.");
        this.serverStateMonitor = serverStateMonitor;
        this.port = port;
    }

    public void run() {
        try {
            OutputStream outputStream = (new ServerSocket(port)).accept().getOutputStream();
            while (true) {
                System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: waiting for change.");
                outputStream.write(serverStateMonitor.getModeBlocking());
                System.out.println("[" + System.currentTimeMillis() + "] ServerStateSender: change sent.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
