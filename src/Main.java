import client.CameraClient;
import se.lth.cs.fakecamera.Axis211A;

public class Main {
    public static void main(String[] args) {
        Axis211A camera = new Axis211A();
//        (new CameraServer(6077, 6078, "localhost", 6079, camera)).start();
        (new CameraServer(6080, 6081, "localhost", 6082, camera)).start();

        (new CameraClient(
                "argus-7", 6600, 6602, 6601,
                "localhost", 6080, 6082, 6081
        )).start();
    }
}
