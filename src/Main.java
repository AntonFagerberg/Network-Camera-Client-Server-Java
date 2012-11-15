import gui.GUI2;
import local.CameraReceiver;
import local.ClientStateReceiver;
import local.StateMonitor;
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;
import server.CameraManager;
import server.PictureSender;
import server.ServerMonitor;
import server.ServerStateSender;

public class Main {
    public static void main(String[] args) {
        final ServerMonitor serverMonitor = new ServerMonitor();
        final StateMonitor stateMonitor = new StateMonitor();

        (new CameraManager(serverMonitor, new Axis211A(), new MotionDetector())).start();
        (new PictureSender(6077, serverMonitor)).start();
//        (new ServerStateSender(6078, serverMonitor)).start();
//        (new ClientStateReceiver("localhost", 6078, stateMonitor)).start();
        (new CameraReceiver("localhost", 6077, 0, new GUI2())).start();
    }
}
