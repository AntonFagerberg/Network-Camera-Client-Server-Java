import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public class ServerStateSender extends Thread {
    private ServerStateMonitor serverStateMonitor;
    private int port;

    public ServerStateSender(int port, ServerStateMonitor serverStateMonitor) {
        System.out.println("ServerStateSender: started.");
        this.serverStateMonitor = serverStateMonitor;
        this.port = port;
    }

    public void run() {
        ServerSocket serverSocket = null;
        OutputStream outputStream = null;

        while (true) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println("ServerStateSender: failed to create ServerSocket on port: " + port + ". Will retry in 5 seconds.");
                try { sleep(5000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }

            if (serverSocket != null) {
                try {
                    outputStream = serverSocket.accept().getOutputStream();
                } catch (IOException e) {
                    System.err.println("ServerStateSender: failed to get OutputStream.");
                }

                if (outputStream != null) {
                    try {
                        while (true) {
                            System.out.println("ServerStateSender: waiting for change.");
                            outputStream.write(serverStateMonitor.getModeBlocking());
                            System.out.println("ServerStateSender: change sent.");
                        }
                    } catch (IOException e) {
                        System.err.println("ServerStateSender: OutputStream aborted.");
                    }

                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        System.err.println("ServerStateSender: failed to close OutputStream.");
                    }
                    outputStream = null;
                }

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("ServerStateSender: failed to close ServerSocket.");
                }
                serverSocket = null;
            }
        }
    }
}
