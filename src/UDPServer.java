import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class UDPServer extends Server {
	
	public static void main(String[] args) throws Exception {
		int port;
		if (args.length != 1) { // check to see if the user supplied a port
			port = DEFAULT_PORT;
		} else { port = Integer.parseInt(args[0]); } // Receiving Port
		
		@SuppressWarnings("resource")
		DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving
		DatagramPacket inPacket, outPacket;
		
		// Begin listening on supplied port
		while (true) {
			inPacket = new DatagramPacket(new byte[REQUEST_MAX],REQUEST_MAX);
			sock.receive(inPacket);
			
			// Receive binary-encoded request
			RequestDecoder decoder = new RequestDecoderBin();
			System.out.println("Received Binary-Encoded request:");
			
			// Decode and create response
			Response response;
			ResponseEncoder encoder = new ResponseEncoderBin();
			try {
				Request request = decoder.decode(inPacket);
				System.out.println(request);
				response = processRequest(request);
			} catch(IOException e) {
				System.out.println("There was an error in the submitted request.");
				response = new Response((byte) 0, (byte) 127, 0);
			}
			System.out.println("Created Response:");
			System.out.println(response);
			byte[] codedResponse = encoder.encode(response);
			outPacket = new DatagramPacket(codedResponse,  // Sending packet
					codedResponse.length, inPacket.getAddress(), inPacket.getPort());
			
			sock.send(outPacket);
			inPacket.setLength(REQUEST_MAX);
		}
	}
}