import local.CameraListener;
import local.Monitor;
import server.PictureSender;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        (new PictureSender()).start();
        (new CameraListener("localhost", 6077, 0, monitor)).start();
        (new CameraListener("localhost", 6077, 1, monitor)).start();
    }
}
