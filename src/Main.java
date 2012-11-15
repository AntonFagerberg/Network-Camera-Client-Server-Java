import local.CameraReceiver;
import local.ClientMonitor;
import local.ClientStateReceiver;
import local.StateMonitor;
import server.PictureSender;
import server.ServerMonitor;
import server.ServerStateSender;

public class Main {
    public static void main(String[] args) {
        ClientMonitor clientMonitor = new ClientMonitor();
        ServerMonitor serverMonitor = new ServerMonitor();
        StateMonitor stateMonitor = new StateMonitor();
        (new PictureSender(6077, serverMonitor)).start();
        (new CameraReceiver("localhost", 6077, 0, clientMonitor)).start();
        (new ServerStateSender(6078, serverMonitor)).start();
        (new ClientStateReceiver("localhost", 6078, clientMonitor)).start();
//        (new PictureSender(6078)).start();
//        (new CameraReceiver("localhost", 6078, 1, monitor)).start();
    }
}
