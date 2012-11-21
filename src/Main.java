import gui.GUI2;
import local.CameraReceiver;
import local.ClientStateReceiver;
import local.ClientStateSender;
import local.StateMonitor;
import se.lth.cs.fakecamera.Axis211A;
import se.lth.cs.fakecamera.MotionDetector;


public class Main {
    public static void main(String[] args) {
        final ServerMonitor serverMonitor1 = new ServerMonitor();
        final ServerMonitor serverMonitor2 = new ServerMonitor();
        final StateMonitor stateMonitor = new StateMonitor();
        
        ServerStateSender serverStateSender1 = new ServerStateSender(6079, serverMonitor1);
        ClientStateReceiver clientStateReceiver1 = new ClientStateReceiver("localhost", 6079, stateMonitor);
        
        ServerStateSender serverStateSender2 = new ServerStateSender(6080, serverMonitor2);
        ClientStateReceiver clientStateReceiver2 = new ClientStateReceiver("localhost", 6080, stateMonitor);
        
        
        ServerStateReceiver serverStateRec1 = new ServerStateReceiver(serverMonitor1, 6081, "localhost");
        ClientStateSender clientStateSender1 = new ClientStateSender(stateMonitor, 6081);
        
        ServerStateReceiver serverStateRec2 = new ServerStateReceiver(serverMonitor2, 6082, "localhost");
        ClientStateSender clientStateSender2 = new ClientStateSender(stateMonitor, 6082);
        
        serverStateSender1.start();
        serverStateRec1.start();
        
        serverStateSender2.start();
        serverStateRec2.start();
        
        clientStateSender1.start();
        clientStateReceiver1.start();
        
        clientStateSender2.start(); 
        clientStateReceiver2.start();
        
        
        
            
        
        
        
        
        

        
        final GUI2 gui = new GUI2(stateMonitor);
        final Axis211A camera = new Axis211A();

        (new FakeCameraServer(serverMonitor1, camera, new MotionDetector())).start();
        (new FakeCameraServer(serverMonitor2, camera, new MotionDetector())).start();

        (new PictureSender(6077, serverMonitor1)).start();
        (new PictureSender(6078, serverMonitor2)).start();

        (new CameraReceiver("localhost", 6077, "localhost", 6078, stateMonitor, gui)).start();
    }
}
