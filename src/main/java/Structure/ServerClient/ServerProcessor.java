package Structure.ServerClient;

import Structure.Commands.OtherCommand;
import Structure.Commands.UserCommand;
import Structure.Packet.Message;
import Structure.Packet.Packet;
import Structure.Utility.Encryptor;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerProcessor extends Thread {
    private static final int THREAD_AMOUNT = 10;

    private boolean shutdown;
    private ExecutorService service;

    private ServerSender sender;
    private ConcurrentLinkedQueue<Packet> queueOfPackets;

//    private ShopDatabase db;

    public ServerProcessor() {
//        db = new ShopDatabase();
//        db.initialization();

        queueOfPackets = new ConcurrentLinkedQueue<>();
        service = Executors.newFixedThreadPool(THREAD_AMOUNT);
        sender = new ServerSender();
    }

    public void run() {
        shutdown = false;
        sender.start();

        while (!shutdown) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (queueOfPackets) {
                            if (queueOfPackets.size() != 0)
                                command(queueOfPackets.poll());
                        }
                    } catch (Exception e) {
                    }
                }
            });
        }
        service.shutdown();
    }

    public void doStop() {
        shutdown = true;
    }

    public void process(Packet packet) {
        queueOfPackets.add(packet);
    }

    private void command(Packet packet) throws Exception {
        UserCommand command = UserCommand.values()[packet.getBMsg().getCType()];
        JSONObject jsonObject = new JSONObject(IOUtils.toString(packet.getBMsg().getMessage()));

//        UserCommand answerCommand = UserCommand.ANSWER;
//        byte[] answer = new byte[0];
//
//        try {
//            switch (command) {
//                case PRODUCT_INSERT -> {
//                    synchronized (db) {
//                        db.insertProduct(
//                                String.valueOf(jsonObject.get("ProductName")),
//                                jsonObject.getInt("ProductAmount"),
//                                jsonObject.getDouble("ProductPrice"),
//                                String.valueOf(jsonObject.get("GroupName")));
//                    }
//                    System.out.println("User " + packet.getBPktId() + "send command: " + command + "Info = " + jsonObject);
//                }
//                case PRODUCT_UPDATE -> {
//                    synchronized (db) {
//                        answer = parseStringToByte(db.readProduct(
//                                String.valueOf(jsonObject.get("ProductName"))).toString());
//                    }
//
//                }
//                case PRODUCT_DELETE -> {
//                    synchronized (db) {
//                        db.deleteProduct(
//                                String.valueOf(jsonObject.get("ProductName")));
//                    }
//                }
//                case PRODUCT_LIST -> {
//                    synchronized (db) {
//                        answer = parseStringToByte(db.getProductList(
//                                String.valueOf(jsonObject.get("Criteria"))).toString());
//                    }
//                }
//                case PRODUCT_NAME -> {
//                    synchronized (db) {
//                        db.updateProductName(
//                                String.valueOf(jsonObject.get("ProductName")),
//                                String.valueOf(jsonObject.get("NewProductName")));
//                    }
//                }
//                case PRODUCT_PRICE -> {
//                    synchronized (db) {
//                        db.updateProductPrice(
//                                String.valueOf(jsonObject.get("ProductName")),
//                                jsonObject.getDouble("ProductPrice"));
//                    }
//                }
//                case PRODUCT_AMOUNT -> {
//                    synchronized (db) {
//                        db.updateProductAmount(
//                                String.valueOf(jsonObject.get("ProductName")),
//                                jsonObject.getInt("ProductAmount"));
//                    }
//                }
//                case PRODUCT_GROUP -> {
//                    synchronized (db) {
//                        db.updateProductGroup(
//                                String.valueOf(jsonObject.get("ProductName")),
//                                String.valueOf(jsonObject.get("GroupName")));
//                    }
//                }
//                case GROUP_INSERT -> {
//                    synchronized (db) {
//                        db.insertGroup(
//                                String.valueOf(jsonObject.get("GroupName")));
//                    }
//                }
//                case GROUP_READ -> {
//                    synchronized (db) {
//                        answer = parseStringToByte(db.readGroup(
//                                String.valueOf(jsonObject.get("GroupName"))).toString());
//                    }
//                }
//                case GROUP_DELETE -> {
//                    synchronized (db) {
//                        db.deleteGroup(
//                                String.valueOf(jsonObject.get("GroupName")));
//                    }
//                }
//                case GROUP_LIST -> {
//                    synchronized (db) {
//                        answer = parseStringToByte(db.getGroupList(
//                                String.valueOf(jsonObject.get("Criteria"))).toString());
//                    }
//                }
//                case GROUP_NAME -> {
//                    synchronized (db) {
//                        db.updateGroupName(
//                                String.valueOf(jsonObject.get("GroupName")),
//                                String.valueOf(jsonObject.get("NewGroupName")));
//                    }
//                }
//                default -> throw new SQLException("Unexpected command. ");
//            }
//        } catch (SQLiteException e) {
//            answerCommand = UserCommand.ERROR;
//        } catch (SQLException e) {
//            answerCommand = UserCommand.ERROR;
//        }
        System.out.println("User " + packet.getBPktId() + " send command: " + command + " Info = " + jsonObject);

        byte[] packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.SUCCESS, packet.getBMsg().getMessage()));
        sender.sendMessage(packet.getBMsg().getBUserId(), packetAnswer);
    }

    private Packet sendAnswer(Packet packet, OtherCommand command, byte[] message) {

        final int COMMAND_SIZE = message.length;
        final int MESSAGE_SIZE = Integer.BYTES + Integer.BYTES + COMMAND_SIZE;

        ByteBuffer bufferMsg = ByteBuffer.allocate(MESSAGE_SIZE);
        bufferMsg.putInt(command.ordinal())
                .putInt(packet.getBMsg().getBUserId())
                .put(message);

        bufferMsg.position(0);
        Message messageAnswer = new Message(bufferMsg, MESSAGE_SIZE);
        Packet packetAnswer = new Packet();

        packetAnswer.setBSrc(packet.getBSrc());
        packetAnswer.setBPktId(packet.getBPktId());
        packetAnswer.setWLen(MESSAGE_SIZE);

        short wCrc16Pkt = packetAnswer.getCrc16Pkt();
        packetAnswer.setWCrc16(wCrc16Pkt);

        packetAnswer.setBMsg(messageAnswer);
        short wCrc16Msg = packetAnswer.getBMsg().getCrc16Msg();
        packetAnswer.setWCrc16Msg(wCrc16Msg);

        return packetAnswer;
    }

    private byte[] parseStringToByte(String str) {
        String[] byteValues = str.substring(1, str.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

}
