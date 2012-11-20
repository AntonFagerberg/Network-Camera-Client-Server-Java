import gui.GUI2;
import local.CameraReceiver;
import local.StateMonitor;
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;
import server.CameraManager;
import server.PictureSender;
import server.ServerMonitor;

public class Main {
    public static void main(String[] args) {
        final ServerMonitor serverMonitor1 = new ServerMonitor();
        final ServerMonitor serverMonitor2 = new ServerMonitor();
        final StateMonitor stateMonitor = new StateMonitor();
        final GUI2 gui = new GUI2();

        Axis211A camera = new Axis211A();

        (new CameraManager(serverMonitor1, camera, new MotionDetector())).start();
        (new CameraManager(serverMonitor2, camera, new MotionDetector())).start();

        (new PictureSender(6077, serverMonitor1)).start();
        (new PictureSender(6078, serverMonitor2)).start();
        (new CameraReceiver("localhost", 6077, 1, stateMonitor, gui)).start();
        (new CameraReceiver("localhost", 6078, 2, stateMonitor, gui)).start();
    }
}
