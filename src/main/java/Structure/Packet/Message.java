package Structure.Packet;

import Structure.Utility.CRC16;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Message {
    private final int cType;
    private final int bUserId;

    private final byte[] message;

    public Message(ByteBuffer buffer, int wLen) {
        this.cType = buffer.getInt();
        this.bUserId = buffer.getInt();

        int arrSize = wLen - Integer.BYTES * 2;
        byte[] msgInfo = new byte[arrSize];
        buffer.get(msgInfo, 0, arrSize);

        this.message = msgInfo;
    }

    public short getCrc16Msg() {
        ByteBuffer buffer = ByteBuffer.allocate(
                Integer.BYTES + Integer.BYTES + message.length);

        buffer.putInt(this.cType)
                .putInt(this.bUserId)
                .put(this.message);

        return CRC16.getCRC16(buffer.array());
    }

    public int getCType() {
        return cType;
    }

    public int getBUserId() {
        return bUserId;
    }

    public byte[] getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message {" +
                "cType = " + cType +
                ", bUserId = " + bUserId +
                ", message = " + Arrays.toString(message) +
                '}';
    }
}
