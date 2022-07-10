package Structure.Utility;

import Structure.Packet.Packet;

import java.nio.ByteBuffer;

public class Encryptor {
    public static byte[] encrypt(Packet packet) {
        final int COMMAND_SIZE = packet.getBMsg().getMessage().length;
        final int MESSAGE_SIZE = Integer.BYTES + Integer.BYTES + COMMAND_SIZE;
        final int PACKET_DATA_SIZE = Byte.BYTES + Byte.BYTES
                + Long.BYTES + Integer.BYTES;

        ByteBuffer buffer = ByteBuffer.allocate(PACKET_DATA_SIZE + Short.BYTES
                + MESSAGE_SIZE + Short.BYTES);

        buffer.put(Packet.getBMagic());
        buffer.put(packet.getBSrc());

        buffer.putLong(packet.getBPktId());
        buffer.putInt(packet.getWLen());

        buffer.putShort(packet.getWCrc16());

        buffer.putInt(packet.getBMsg().getCType());
        buffer.putInt(packet.getBMsg().getBUserId());
        buffer.put(packet.getBMsg().getMessage());

        buffer.putShort(packet.getWCrc16Msg());

        return buffer.array();
    }
}
