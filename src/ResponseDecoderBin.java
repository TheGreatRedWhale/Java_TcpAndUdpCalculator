import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class ResponseDecoderBin implements ResponseDecoder {

    public ResponseDecoderBin() {}

    // IDK what "wire" is in this context. If we can figure that out, this should work.
    public Response decode(InputStream inputStream) throws IOException {
        DataInputStream src = new DataInputStream(inputStream);
        byte  tml		= src.readByte();
        if (tml != 7) throw new IOException();
        byte  ID		= src.readByte();
        byte  erCode	= src.readByte();
        int  result	= src.readInt();

        return new Response(ID, erCode, result);
    }

    public Response decode(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decode(payload);
    }
}