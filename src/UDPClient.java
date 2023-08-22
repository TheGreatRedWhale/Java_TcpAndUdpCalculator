import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;

public class UDPClient extends Client {
	
	// MAIN METHOD_____________________________________________________________
	public static void main(String args[]) throws Exception {
		
		if (args.length != 1 && args.length != 2) {	// Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Destination>" +
					" [Port]");
		}
		
		InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
		int destPort;											// Destination port
		if (args.length == 2) { destPort = Integer.parseInt(args[1]); }
		else { destPort = DEFAULT_PORT; }
		RequestEncoder encoder = new RequestEncoderBin();		// Request Encoder
		ResponseDecoder decoder = new ResponseDecoderBin();		// Response Decoder
		Scanner keyboard = new Scanner(System.in);				// Keyboard Input
		byte reqID = (byte) (									// Initial Request ID
				Math.random()*RequestBinConst.MAX_REQ_ID_LEN);
		
		// Run until the program is closed; either forcibly, or by typing in EXIT_STRING.
		while (true) {
			System.out.println("NOTE: Input " + EXIT_STRING.toUpperCase() +
					" to exit.");
			// Create Request
			System.out.println("Input request: ");
			String input = keyboard.nextLine().trim();
			// Exit the program if the EXIT_STRING is entered
			if (input.toLowerCase().contains("quit")) { break; }
			Request request = getUserRequest(reqID++, input);

			// Print Request to be sent for testing...
			System.out.println("Sent Request:\n" + request);
			
			// Encode request...
			byte[] codedRequest = encoder.encode(request);
			
			// Create the in and out packets
			DatagramPacket outPacket = new DatagramPacket(codedRequest, codedRequest.length, destAddr, destPort);
			DatagramPacket inPacket = new DatagramPacket(new byte[RESPONSE_MAX], RESPONSE_MAX);
			
			// Open Socket
			DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
			sock.setSoTimeout(TIMEOUT);  				// Maximum receive blocking time (ms)
			
			int tries = 0;      // Packets may be lost, so we have to keep trying
			boolean receivedResponse = false;
			do {
				sock.send(outPacket);
				// Try to receive the response packet.
				try {
					sock.receive(inPacket);
					if (!inPacket.getAddress().equals(destAddr))  // Check source
						throw new IOException("Received packet from an unknown source");
					receivedResponse = true;
				} catch (InterruptedIOException e){
					tries += 1;
					System.out.println("Timed out, " + (MAXTRIES-tries) + " more tries...");
				}
			} while ((!receivedResponse) && (tries < MAXTRIES));
			
			if (receivedResponse) {
				Response response = decoder.decode(inPacket);
				System.out.println("Received:\n" + response);
			}
			else System.out.println("No response -- giving up.");
			// Close Socket
			sock.close();
		}
		keyboard.close();
	}
}
