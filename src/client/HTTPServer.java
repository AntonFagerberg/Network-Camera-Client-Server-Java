package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer extends Thread {
	private int port;
	private static final byte[] CRLF = { 13, 10 };
	private byte[] jpeg;
	private Socket clientSocket;
	private HTTPMonitor httpMonitor;
	private ServerSocket serverSocket;

	public HTTPServer(int port, HTTPMonitor httpMonitor) {
		this.port = port;
		this.httpMonitor = httpMonitor;
		
		
	}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("HTTP server operating at port " + port + ".");
			
			
			
		while (true) {
			try {
				
				
				// The 'accept' method waits for a client to connect, then
				// returns a socket connected to that client.
				clientSocket = serverSocket.accept();
				
				

				// The socket is bi-directional. It has an input stream to read
				// from and an output stream to write to. The InputStream can
				// be read from using read(...) and the OutputStream can be
				// written to using write(...). However, we use our own
				// getLine/putLine methods below.
				InputStream is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();

				// Read the request
				String request = getLine(is);

				// The request is followed by some additional header lines,
				// followed by a blank line. Those header lines are ignored.
				String header;
				boolean cont;
				do {
					header = getLine(is);
					cont = !(header.equals(""));
				} while (cont);

				System.out.println("HTTP request '" + request + "' received.");

				// Interpret the request. Complain about everything but GET.
				// Ignore the file name.
				if (request.substring(0, 4).equals("GET ")) {
					jpeg = httpMonitor.requestImage();
					// Got a GET request. Respond with a common.JPEG image from
					// the
					// camera. Tell the client not to cache the image
					putLine(os, "HTTP/1.0 200 OK");
					putLine(os, "Content-Type: image/jpeg");
					putLine(os, "Pragma: no-cache");
					putLine(os, "Cache-Control: no-cache");
					putLine(os, ""); // Means 'end of header'
					os.write(jpeg, 0, jpeg.length);
					
				} else {
					// Got some other request. Respond with an error message.
					putLine(os, "HTTP/1.0 501 Method not implemented");
					putLine(os, "Content-Type: text/plain");
					putLine(os, "");
					putLine(os, "No can do. Request '" + request
							+ "' not understood.");

					System.out.println("Unsupported HTTP request!");
				}

				os.flush(); // Flush any remaining content
				clientSocket.close(); // Disconnect from the client
			} catch (IOException e) {
				System.out.println("Caught exception " + e);
			}
		}
	}
	

	private static String getLine(InputStream s) throws IOException {
		boolean done = false;
		String result = "";

		while (!done) {
			int ch = s.read(); // Read
			if (ch <= 0 || ch == 10) {
				// Something < 0 means end of data (closed socket)
				// ASCII 10 (line feed) means end of line
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}

		return result;
	}

	private static void putLine(OutputStream s, String str) throws IOException {
		s.write(str.getBytes());
		s.write(CRLF);
	}
	
	public void generateImage(byte[] jpeg) {
		this.jpeg = jpeg;
	}

}
