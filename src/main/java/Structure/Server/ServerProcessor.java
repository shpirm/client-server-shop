package Structure.Server;

import Database.Criteria;
import Database.ShopDatabase;
import Interface.Program.Group;
import Interface.Program.Product;
import Structure.Commands.OtherCommand;
import Structure.Commands.UserCommand;
import Database.LoginDatabase;
import Structure.Packet.Message;
import Structure.Packet.Packet;
import Structure.Utility.Encryptor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerProcessor extends Thread {
    private static final int THREAD_AMOUNT = 10;

    private boolean shutdown;
    private ExecutorService service;

    private ServerSender sender;
    private ConcurrentLinkedQueue<Packet> queueOfPackets;

    private LoginDatabase loginDatabase;

    private ShopDatabase shopDatabase;

    public ServerProcessor() {
        loginDatabase = new LoginDatabase();
        loginDatabase.initialization();

        shopDatabase = new ShopDatabase();
        shopDatabase.initialization();

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
                        System.out.println(e);
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
        System.out.println("Server received packet from User " + packet.getBMsg().getBUserId() +
                " { Command: " + command + ", Info: " + jsonObject + " }");

        JSONObject objAnswer = new JSONObject();
        byte[] packetAnswer = new byte[0];

            switch (command) {
                case CONNECT_REQUEST -> {
                    if (equalPassword(String.valueOf(jsonObject.get("server password")))) {
                        String str = "Connect gained!";
                        objAnswer.put("answer", str);

                        objAnswer.put("login", String.valueOf(jsonObject.get("login")));
                        objAnswer.put("host", String.valueOf(jsonObject.get("host")));
                        objAnswer.put("port", jsonObject.getInt("port"));

                        synchronized (loginDatabase) {
                            try {
                                loginDatabase.insertUser(
                                        String.valueOf(jsonObject.get("login")),
                                        String.valueOf(jsonObject.get("password")),
                                        String.valueOf(jsonObject.get("host")),
                                        jsonObject.getInt("port")
                                );
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                        }

                        packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.CONNECT_SUCCESS,
                                objAnswer.toString().getBytes(StandardCharsets.UTF_8)));

                    } else {
                        String str = "Connect rejected!";
                        objAnswer.put("answer", str);

                        packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.CONNECT_ERROR,
                                objAnswer.toString().getBytes(StandardCharsets.UTF_8)));

                       // ServerTCP.getInstance(3333, "123").stopConnection(packet.getBMsg().getBUserId());
                    }
                }
                case ACCESS_REQUEST -> {
                    String login = String.valueOf(jsonObject.get("login"));
                    String password = String.valueOf(jsonObject.get("password"));
                    synchronized (loginDatabase) {
                        if (Objects.equals(password, loginDatabase.getPassword(login))) {
                            String str = "Access gained!";
                            objAnswer.put("answer", str);

                            packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.ACCESS_SUCCESS,
                                    objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                        } else {
                            String str = "Access rejected!";
                            objAnswer.put("answer", str);

                            packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.ACCESS_ERROR,
                                    objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                }
//                case USER_LIST -> {
//                    JSONArray arr = new JSONArray();
//                    ArrayList<Connections> array = new ArrayList<>();
//
//                    synchronized (loginDatabase) {
//                        array = loginDatabase.getUserList();
//                    }
//                    System.out.println(array);
//                    for (Connections user : array) {
//                        JSONObject userObj = new JSONObject();
//                        userObj.put("login", user.getLogin());
//                        userObj.put("host", user.getHost());
//                        userObj.put("port", user.getPort());
//                        arr.put(userObj);
//                    }
//
//                    JSONObject connection = new JSONObject();
//                    connection.put("users", arr);
//                    System.out.println(connection);
//
//                    packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.USER_LIST,
//                            connection.toString().getBytes(StandardCharsets.UTF_8)));
//                    sender.sendMessage(packet.getBMsg().getBUserId(), packetAnswer);
//                }
                case GROUP_INSERT -> {
                    String name = String.valueOf(jsonObject.get("name"));
                    String description = String.valueOf(jsonObject.get("description"));
                    synchronized (shopDatabase) {
                        shopDatabase.insertGroup(name, description);
                    }

                    objAnswer.put("name", name);
                    objAnswer.put("description", description);
                    objAnswer.put("answer", "Group " + name + " added!");
                    packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.INSERT_GROUP_SUCCESS,
                            objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                }
                case GROUP_LIST -> {
                    JSONArray arr = new JSONArray();
                    ArrayList<Group> array = new ArrayList<>();

                    synchronized (shopDatabase) {
                        array = shopDatabase.getGroupList();
                    }

                    for (Group group : array) {
                        JSONObject userObj = new JSONObject();
                        userObj.put("name", group.getName());
                        userObj.put("description", group.getDescription());
                        arr.put(userObj);
                    }
                    
                    objAnswer.put("groups", arr);

                    packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.GROUP_LIST,
                            objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                }
                case GROUP_PRODUCT_LIST -> {
                    try {
                        JSONArray arr = new JSONArray();
                        ArrayList<Product> array = new ArrayList<>();

                        synchronized (shopDatabase) {
                            array = shopDatabase.getAllProductsInGroup(String.valueOf(jsonObject.get("group name")));
                        }

                        for (Product product : array) {
                            JSONObject userObj = new JSONObject();
                            userObj.put("name", product.getName());
                            userObj.put("amount", product.getNumber());
                            userObj.put("price", product.getPrice());
                            userObj.put("brand", product.getBrand());
                            userObj.put("description", product.getDescription());
                            arr.put(userObj);
                        }
                        
                        objAnswer.put("products", arr);
                        objAnswer.put("group", String.valueOf(jsonObject.get("group name")));

                        packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.GROUP_PRODUCT_LIST,
                                objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                    } catch (SQLException e) { System.out.println(e); }
                }
                case PRODUCT_FIND ->{
                    JSONArray arr = new JSONArray();
                    ArrayList<Product> array = new ArrayList<>();

                    synchronized (shopDatabase) {
                        array = shopDatabase.productListByCriteria(new Criteria().setName(jsonObject.getString("searchText")));
                    }
                    for (Product product : array) {
                        JSONObject userObj = new JSONObject();
                        userObj.put("name", product.getName());
                        userObj.put("amount", product.getNumber());
                        userObj.put("price", product.getPrice());
                        userObj.put("brand", product.getBrand());
                        userObj.put("description", product.getDescription());
                        arr.put(userObj);
                    }
                    objAnswer.put("products", arr);
                    packetAnswer = Encryptor.encrypt(sendAnswer(packet, OtherCommand.PRODUCT_FIND_LIST,
                            objAnswer.toString().getBytes(StandardCharsets.UTF_8)));
                }
            }

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
        sender.sendMessage(packet.getBMsg().getBUserId(), packetAnswer);
    }

    public boolean equalPassword(String checkPassword) throws Exception {
        return Objects.equals(ServerTCP.getInstance(3333, "123").getPassword(), checkPassword);
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
