import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerStateReceiver extends Thread {
    ServerStateMonitor serverStateMonitor;
    String url;
    int port, mode;

    public ServerStateReceiver(String url, int port, ServerStateMonitor serverStateMonitor) {
        this.serverStateMonitor = serverStateMonitor;
        this.port = port;
        this.url = url;
        System.out.println("[" + System.currentTimeMillis() + "] ServerStateReceiver: started.");
    }

    public void run() {
        while (true) {
            try {
                InputStream inputStream = (new Socket(url, port)).getInputStream();
                while ((mode = inputStream.read()) != -1) {
//                    System.out.println("[" + System.currentTimeMillis() + "] ServerStateReceiver: waiting for input.");
                    System.out.println("[" + System.currentTimeMillis() + "] ServerStateReceiver: got mode: " + mode);
                    switch (mode) {
                        case ServerStateMonitor.IDLE:
                        case ServerStateMonitor.MOVIE:
                            serverStateMonitor.unsetForcedMode();
                            break;
                        case ServerStateMonitor.IDLE_FORCED:
                            serverStateMonitor.setMode(ServerStateMonitor.IDLE_FORCED);
                            break;
                        case ServerStateMonitor.MOVIE_FORCED:
                            serverStateMonitor.setMode(ServerStateMonitor.MOVIE_FORCED);
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("[" + System.currentTimeMillis() + "] ServerStateReceiver: connection failed, sleeping 5 seconds...");
                try {
                    sleep(5000);
                } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
    }
}
