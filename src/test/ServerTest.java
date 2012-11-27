package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class ServerTest extends Thread {
    public void run() {
        ServerSocket serverSocket = null;
        OutputStream outputStream;

        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(6666));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        while (serverSocket != null) {
            try {
                outputStream = serverSocket.accept().getOutputStream();
                int x = 0;
                while (true) {
                    outputStream.write(x);
                    x++;
                }

            } catch (IOException e) {
                System.err.println("Stream aborted. Reconnecting...");
                try { sleep(3000); } catch (InterruptedException e1) { e1.printStackTrace(); }
            }
        }
    }

    public static void main(String[] args) {
        (new ServerTest()).start();
    }
}
