import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {
	
	public static final int MAX_BUFFER_SIZE = 8;
	public RequestDecoderBin() {}

	public Request decode(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[MAX_BUFFER_SIZE];
		DataInputStream src = new DataInputStream(inputStream);
		src.read(buffer);
		printByteArrayAsHex(buffer);
		
		byte tml = buffer[0];
		if (tml < 6 || tml > 8 || tml != buffer.length) throw new IOException();
		byte ID		= buffer[1];
		byte opCode	= buffer[2];
		byte numOps	= buffer[3];
		short opOne	= (short) ((buffer[4] << 8) | (buffer[5]));
		
		if (numOps == 1) {
			return new Request(ID, opCode, opOne);
		} else {
			short opTwo = (short) ((buffer[6] << 8) | (buffer[7]));
			return new Request(ID, opCode, opOne, opTwo);
		}
  }
	
	public Request decode(DatagramPacket p) throws IOException {
		ByteArrayInputStream payload =
				new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
		return decode(payload);
	}
	
	private void printByteArrayAsHex(byte[] byteArray) {
		String output = "";
		String hex;
		for (byte b : byteArray) {
			hex = Integer.toHexString(b & 0xFF);
			if (hex.length() == 1) { hex = "0" + hex; }
			output += (hex + " ");
		}
		output.trim();
		System.out.println("Request as Hex: " + output);
	}
}