public class Response {

    // VARIABLES_______________________________________________________________
    public byte tml;		// Total Message Length
    public byte id;			// Item identification number
    public byte erCode;		// Item identification number
    public int result;		// Item identification number

    // CONSTRUCTORS____________________________________________________________

    public Response(byte ID, byte erCode, int result)  {
        this.tml	= (byte) 7;
        this.id		= ID;
        this.erCode	= erCode;
        this.result	= result;
    }

    // METHODS_________________________________________________________________
    public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        return "Request ID = " + unsignedByte(id) + EOLN +
                "Error Code = " + erCode + EOLN +
                "Result = " + result + EOLN;
    }
    
    private short unsignedByte(byte b) {
    	return (short) (0xff & b);
    }
}