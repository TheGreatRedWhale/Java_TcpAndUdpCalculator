public class Request {

    // VARIABLES_______________________________________________________________
	public byte tml;		// Total Message Length
    public byte id;		// Item identification number
    public byte opCode;		// Item identification number
    public byte numOps;		// Item identification number
    public short opOne;		// First Operand
    public short opTwo = 0;	// Second Operand

    // CONSTRUCTORS____________________________________________________________
    
    /**
     * Constructor for requests which require multiple operands
     * 
     * @param ID		Unique identifier of the request
     * @param opCode	Numeric value representing the operations to be performed.
     * 						See values contained in the OpCode Interface.
     * @param opOne		The first operand
     * @param opTwo		The second operand
     */
    public Request(byte ID, byte opCode, short opOne, short opTwo)  {
    	this.tml	= (byte) 8;
    	this.numOps	= (byte) 2;
    	this.id		= ID;
    	this.opCode	= opCode;
    	this.opOne	= opOne;
    	this.opTwo	= opTwo;
    }
    
    /**
     * Constructor for requests which require only one operand ("NOT" functions)
     * 
     * @param ID		Unique identifier of the request
     * @param opCode	Numeric value representing the operations to be performed.
     * 						See values contained in the OpCode Interface.
     * @param opOne		The operand
     */
    public Request(byte ID, byte opCode, short opOne)  {
    	this.tml	= (byte) 6;
    	this.numOps	= (byte) 1;
    	this.id		= ID;
    	this.opCode	= opCode;
    	this.opOne	= opOne;
    }
    
    // METHODS_________________________________________________________________
    public String toString() {
    	final String EOLN = java.lang.System.getProperty("line.separator");
    	String value = "Request ID = " + unsignedByte(id) + EOLN +
    			"Operand One = " + opOne + EOLN;
    	if (opCode == OpCode.ADD) {
    		value += "Operation = '+'" + EOLN;
    	}
    	else if (opCode == OpCode.SUB) {
    		value += "Operation = '-'" + EOLN;
    	}
    	else if (opCode == OpCode.OR) {
    		value += "Operation = '|'" + EOLN;
    	}
    	else if (opCode == OpCode.AND) {
    		value += "Operation = '&'" + EOLN;
    	}
    	else if (opCode == OpCode.SHIFT_RIGHT) {
    		value += "Operation = '>>'" + EOLN;
    	}
    	else if (opCode == OpCode.SHIFT_LEFT) {
    		value += "Operation = '<<'" + EOLN;
    	}
    	else if (opCode == OpCode.NOT) {
    		value += "Operation = '~'" + EOLN;
    	}
    	if (numOps > 1)
    		value += "Operand Two = " + opTwo + EOLN;
    	return value;
    }
    
    private short unsignedByte(byte b) {
    	return (short) (0xff & b);
    }
}
