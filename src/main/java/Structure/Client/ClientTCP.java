package Structure.Client;

import Structure.Commands.UserCommand;
import Structure.Packet.Packet;
import Structure.Utility.CRC16;
import Structure.Utility.Cypher;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static Structure.Commands.UserCommand.CONNECT_REQUEST;

public class ClientTCP extends Thread {

    public static Map<String, ClientTCP> clientMap = new HashMap<>();
    public static Map<Integer, ClientTCP> clientMapID = new HashMap<>();
    private static int userID = 0;
    private static int packetID = 0;

    private int clientUserId;
    private Socket clientSocket;
    private PrintWriter out;
    private ClientReceiver receiver;

    private ConcurrentLinkedQueue<byte[]> queueOfPackets;
    private boolean shutdown;

    public void startConnection(String login, String password, String host, int port, String serverPassword) throws Exception {
        this.clientUserId = userID++;
        clientSocket = new Socket(host, port);
        clientSocket.setSoTimeout(50000);
        queueOfPackets = new ConcurrentLinkedQueue<>();

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.start();

        receiver = new ClientReceiver(clientSocket);
        receiver.start();

        JSONObject object = new JSONObject();
        object.put("login", login);
        object.put("password", Cypher.getEncryptedBytes(password));
        object.put("host", host);
        object.put("port", port);
        object.put("server password", Cypher.getEncryptedBytes(serverPassword));

        sendMessage(CONNECT_REQUEST, object);

        clientMap.put(login, this);
        clientMapID.put(clientUserId, this);
    }

    public void run() {
        shutdown = false;

        while (!shutdown) {
            if (queueOfPackets.size() != 0) {
                out.println(Arrays.toString(queueOfPackets.poll()));
            }
        }
    }

    public void sendMessage(UserCommand command, JSONObject object) throws IOException {
        System.out.println(Arrays.toString(getPacket(command, object, clientUserId, packetID++)));
        queueOfPackets.add(getPacket(command, object, clientUserId, packetID++));
    }

    public void stopConnection() {
        shutdown = true;
        clientMapID.remove(clientUserId);
    }

    private static byte[] getPacket(UserCommand command, JSONObject object, int userID, int packetID) throws UnsupportedEncodingException {

        byte[] messageInfo = object.toString().getBytes("utf-8");

        final int COMMAND_SIZE = messageInfo.length;
        final int MESSAGE_SIZE = Integer.BYTES + Integer.BYTES + COMMAND_SIZE;
        final int PACKET_DATA_SIZE = Byte.BYTES + Byte.BYTES
                + Long.BYTES + Integer.BYTES;

        ByteBuffer buffer = ByteBuffer.allocate(PACKET_DATA_SIZE + Short.BYTES
                + MESSAGE_SIZE + Short.BYTES);

        ByteBuffer bufferPkt = ByteBuffer.allocate(PACKET_DATA_SIZE);

        bufferPkt.put(Packet.getBMagic())
                .put((byte) 0)
                .putLong(packetID)
                .putInt(MESSAGE_SIZE);

        short wCrc16Pkt = CRC16.getCRC16(bufferPkt.array());
        buffer.put(bufferPkt.array())
                .putShort(wCrc16Pkt);

        ByteBuffer bufferMsg = ByteBuffer.allocate(MESSAGE_SIZE);
        bufferMsg.putInt(command.ordinal())
                .putInt(userID)
                .put(messageInfo);

        short wCrc16Msg = CRC16.getCRC16(bufferMsg.array());
        buffer.put(bufferMsg.array())
                .putShort(wCrc16Msg);

        return buffer.array();
    }
}
