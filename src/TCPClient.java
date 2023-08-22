import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient extends Client {
	
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
			
			// Encode request...
			byte[] codedRequest = encoder.encode(request);

            // Create socket that is connected to server on specified port
            Socket socket = new Socket(destAddr, destPort);
            System.out.println("Connected to server.");

            // Create data streams...
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

			// Print Request to be sent for testing...
			System.out.println("Sending Request...");
			out.write(codedRequest);
			System.out.println("Sent Request:\n" + request);
			
			Response response = decoder.decode(in);
			System.out.println("Received Response:\n" + response);

			// Close Socket
			socket.close();
		}
		keyboard.close();
	}
}