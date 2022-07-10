package Structure.Utility;

import Structure.Packet.Message;
import Structure.Packet.Packet;

import java.nio.ByteBuffer;

public class Descriptor {
    public static Packet decrypt(byte[] bytes) throws Exception {
        Packet packet = null;

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        while (buffer.remaining() != 0) {
            if (buffer.get() == Packet.getBMagic()) {
                packet = new Packet();

                packet.setBSrc(buffer.get());
                packet.setBPktId(buffer.getLong());

                int wLen = buffer.getInt();
                packet.setWLen(wLen);

                short pktWCrc16 = packet.getCrc16Pkt();
                packet.setWCrc16(buffer.getShort());

                if (pktWCrc16 == packet.getWCrc16()) {
                    packet.setBMsg(new Message(buffer, wLen));

                    packet.setWCrc16Msg(buffer.getShort());
                    short pktWCrc16Msg = packet.getBMsg().getCrc16Msg();

                    if (pktWCrc16Msg == packet.getWCrc16Msg()) {
                        return packet;
                    } else throw new Exception ("Wrong message CRC. ");
                } else throw new Exception ("Wrong packet CRC. ");
            }
        }

        return packet;
    }
}
