import local.CameraListener;
import local.ClientMonitor;
import server.PictureSender;
import server.ServerMonitor;

public class Main {
    public static void main(String[] args) {
        ClientMonitor clientMonitor = new ClientMonitor();
        ServerMonitor serverMonitor = new ServerMonitor();
        (new PictureSender(6077, serverMonitor)).start();
        (new CameraListener("localhost", 6077, 0, clientMonitor)).start();
//        (new PictureSender(6078)).start();
//        (new CameraListener("localhost", 6078, 1, monitor)).start();
    }
}
