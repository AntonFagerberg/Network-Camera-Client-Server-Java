import gui.GUI2;
import gui.ImageHandler;
import local.CameraReceiver;
import local.ClientMonitor;
import server.PictureSender;
import server.ServerMonitor;

public class Main {
    public static void main(String[] args) {
        ClientMonitor monitor = new ClientMonitor();
        ServerMonitor serverMonitor = new ServerMonitor();
        (new PictureSender(6077, serverMonitor)).start();
        (new CameraReceiver("localhost", 6077, 0, monitor)).start();
        (new ImageHandler(monitor, (new GUI2()))).start();
//        (new PictureSender(6078)).start();
//        (new CameraListener("localhost", 6078, 1, monitor)).start();
    }
}
