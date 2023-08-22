import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

	public RequestEncoderBin() {
		
	}

	public byte[] encode(Request request) throws Exception {

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(buf);
		out.writeByte(request.tml);
		out.writeByte(request.id);
		out.writeByte(request.opCode);
		out.writeByte(request.numOps);
		out.writeShort(request.opOne);
		if (request.numOps == 2)
			out.writeShort(request.opTwo);
		
		out.flush();
    return buf.toByteArray();
  }
}
