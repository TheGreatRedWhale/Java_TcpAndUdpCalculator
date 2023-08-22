
public abstract class Server implements OpCode, ConnectionConst {
	
	// METHODS_________________________________________________________________
	/**
	 * Method that converts request objects into response objects. In other
	 * words, it does the calculations found within the request.
	 * @param r
	 *        Supplied {@code Request}
	 * @return Returns a response object to be re-encoded on the data-stream.
	 */
	public static Response processRequest(Request r) {
		int result = -1;
		if (r.opCode == ADD) {
			result = r.opOne + r.opTwo;
		} else if (r.opCode == SUB) {
			result = r.opOne - r.opTwo;
		} else if (r.opCode == OR) {
			result = r.opOne | r.opTwo;
		} else if (r.opCode == AND) {
			result = r.opOne & r.opTwo;
		} else if (r.opCode == SHIFT_LEFT) {
			result = r.opOne << r.opTwo;
		} else if (r.opCode == SHIFT_RIGHT) {
			result = r.opOne >> r.opTwo;
		} else { result = ~r.opOne; }
		
		return new Response(r.id, (byte) 0, result);
	}
}
