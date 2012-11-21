import gui.GUI2;
import local.CameraReceiver;
import local.StateMonitor;
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;


public class Main {
    public static void main(String[] args) {
        final ServerMonitor serverMonitor1 = new ServerMonitor();
        final ServerMonitor serverMonitor2 = new ServerMonitor();
        final StateMonitor stateMonitor = new StateMonitor();

        final GUI2 gui = new GUI2(stateMonitor);
        final Axis211A camera = new Axis211A();

        (new CameraServer(serverMonitor1, camera, new MotionDetector())).start();
        (new CameraServer(serverMonitor2, camera, new MotionDetector())).start();

        (new PictureSender(6077, serverMonitor1)).start();
        (new PictureSender(6078, serverMonitor2)).start();

        (new CameraReceiver("localhost", 6077, "localhost", 6078, stateMonitor, gui)).start();
    }
}
