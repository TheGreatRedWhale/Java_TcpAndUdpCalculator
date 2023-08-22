public interface ConnectionConst {
	// VARIABLES_______________________________________________________________
	public static final int GID = 2;					// The Group ID (Team Number)
	public static final int DEFAULT_PORT = 10010 + GID;	// The default port
	public static final int TIMEOUT = 1500;				// Resend timeout (milliseconds)
	public static final int MAXTRIES = 5;				// Maximum retransmissions
	public static final int REQUEST_MIN = 6;			// Minimum size of request packet
	public static final int REQUEST_MAX = 8;			// Maximum size of request packet
	public static final int RESPONSE_MAX = 7;			// Maximum size of response packet
	public static final int BUFFER_MAX = 7;				// Maximum buffer size for TCP packets
}
