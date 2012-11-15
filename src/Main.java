import gui.GUI2;
import gui.ImageHandler;
import local.CameraReceiver;
import local.ClientMonitor;
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;
import server.CameraManager;
import server.PictureSender;
import server.ServerMonitor;

public class Main {
    public static void main(String[] args) {
        final ClientMonitor clientMonitor = new ClientMonitor();
        final ServerMonitor serverMonitor = new ServerMonitor();
        (new CameraManager(serverMonitor, new Axis211A(), new MotionDetector())).start();
        (new PictureSender(6077, serverMonitor)).start();
        (new CameraReceiver("localhost", 6077, 0, clientMonitor)).start();
        (new ImageHandler(clientMonitor, (new GUI2()))).start();

//        (new MotionDetectorUpdater(serverMonitor, new MotionDetector())).start();
//        (new ServerStateSender(6078, serverMonitor)).start();

//        (new PictureSender(6078, serverMonitor)).start();
//        (new CameraListener("localhost", 6078, 1, clientMonitor)).start();
    }
}
