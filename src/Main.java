import client.CameraClient;
import client.GUI;
import client.HTTPServer;
import se.lth.cs.fakecamera.Axis211A;

public class Main {
	public static void main(String[] args) {
        Axis211A camera = new Axis211A();

        (new CameraServer(6600, 6601, "localhost", 6602, camera)).start();
        (new CameraServer(6610, 6611, "localhost", 6612, camera)).start();
        new GUI();
	}
}
