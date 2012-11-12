package local;

import se.lth.cs.cameraproxy.Axis211A;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CameraListener {
	private InputStream inputStream;

	public CameraListener(String url, int port) {
		try {
			inputStream = (new Socket("localhost", 6077)).getInputStream();
            fetchJPEG();
		} catch (IOException e) {
			System.out.println("Communication error with server: " + url + ":" + port + ".");
			e.printStackTrace();
		}
    }

    private void fetchJPEG() throws IOException {
        byte[] receivedJPEGData = new byte[Axis211A.IMAGE_BUFFER_SIZE];
        int bytesReceived = 0;

        while (true) {
            bytesReceived = inputStream.read(receivedJPEGData, 0, Axis211A.IMAGE_BUFFER_SIZE);

            if (bytesReceived > 0) {
                System.out.println("Bytes received: " + bytesReceived);
                byte[] jpeg = new byte[bytesReceived];
                System.arraycopy(receivedJPEGData, 0, jpeg, 0, bytesReceived);

                for (byte b : jpeg) {
                    System.out.print(b + " ");
                }
            }
        }
    }

	public static void main(String[] args) {
		new CameraListener("localhost", 6077);
	}
}
