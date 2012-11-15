import local.CameraListener;
import local.Monitor;
import server.PictureSender;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        (new PictureSender(6077)).start();
        (new CameraListener("localhost", 6077, 0, monitor)).start();
//        (new PictureSender(6078)).start();
//        (new CameraListener("localhost", 6078, 1, monitor)).start();
    }
}
