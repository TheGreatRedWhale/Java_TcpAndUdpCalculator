import java.io.IOException;

/**
 * 
 * @author Jared Jeffrey George Whaley
 * @version 2/10/2022
 *
 */
public abstract class Client implements OpCode, ConnectionConst {
	// VARIABLES_______________________________________________________________
	
	protected static final String EXIT_STRING = "quit";	// Input string to exit
	
	// METHODS_________________________________________________________________
	/**
	 * Method for converting a {@code String} representation of a simple
	 * equation (i.e. "2 + 8") into a {@code Request}.
	 * 
	 * @param id
	 *        The supplied Request ID.
	 * @param input
	 *        The input {@code String} to be parsed.
	 * @return Returns a {@code Request} upon completion of the method.
	 * @throws IOException
	 *         Thrown when the operator cannot be resolved.
	 */
	public static Request getUserRequest(byte id, String input)
			throws IOException {
		byte opCode;
		short opOne, opTwo;
		Request output;
		String[] inArr = input.split(" ");
		
		if (inArr.length < 2 || inArr.length > 3) throw new IOException();
		
		// Create Request
		opCode = stringToOpCode(inArr[1]);
		//     Single operands
		if (inArr.length == 2) {
			if (opCode != NOT) throw new IOException();
			opOne = (short) Integer.parseInt(inArr[1]);
			output = new Request(id, opCode, opOne);
			return output;
		//     Two Operands
		} else {
			if (opCode < 0 && opCode > 7) throw new IOException();
			opOne = (short) Integer.parseInt(inArr[0]);
			opTwo = (short) Integer.parseInt(inArr[2]);
			output = new Request(id, opCode, opOne, opTwo);
			return output;
		}
	}
	
	/**
	 * Method for converting a {@code String} (usually from user input) to a
	 * usable opcode of type {@code byte}.
	 * 
	 * @param s
	 * 		  String representation of our operation
	 * @return byte value representation of our operand.
	 */
	public static byte stringToOpCode(String s) {
		if (s.equals("+")) {
			return ADD;
		} else if (s.equals("-")) {
			return SUB;
		} else if (s.equals("|")) {
			return OR;
		} else if (s.equals("&")) {
			return AND;
		} else if (s.equals("<<")) {
			return SHIFT_LEFT;
		} else if (s.equals(">>")) {
			return SHIFT_RIGHT;
		} else if (s.equals("~")){
			return NOT;
		} else {
			return (byte) Integer.parseInt(s);
		}
	}
}
