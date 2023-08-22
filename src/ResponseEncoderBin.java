// for ByteArrayOutputStream and DataOutputStream
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ResponseEncoderBin implements ResponseEncoder {

    public ResponseEncoderBin() {

    }

    public byte[] encode(Response response) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        out.writeByte(response.tml);
        out.writeByte(response.id);
        out.writeByte(response.erCode);
        out.writeInt(response.result);

        out.flush();
        return buf.toByteArray();
    }
}