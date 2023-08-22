import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class TCPServer extends Server {

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length != 1) { // check to see if the user supplied a port
			port = DEFAULT_PORT;
		} else { port = Integer.parseInt(args[0]); } // Receiving Port
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);  // UDP socket for receiving

		while (true) {
			Socket clntSock = serverSocket.accept();  // Get client connection

			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();

			// Receive binary-encoded request
			RequestDecoder decoder = new RequestDecoderBin();
			System.out.println("Received Binary-Encoded request:");

			// Decode and create response
			Request request;
			Response response;
			ResponseEncoderBin encoder = new ResponseEncoderBin();

			try {
				request = decoder.decode(in);
				System.out.println(request);
				response = processRequest(request);
			} catch(IOException e) {
				System.out.println("There was an error in the submitted request.");
				response = new Response((byte) 0, (byte) 127, 0);
			}
			System.out.println("Created response:");
			System.out.println(response);
			byte[] codedResponse = encoder.encode(response);
			out.write(codedResponse);

			clntSock.close();
		}
	}
}