package Structure.ServerClient;

import Structure.Utility.Descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTCP extends Thread {

    private static ServerTCP instance;
    private boolean shutdown;
    private ServerSocket serverSocket;

    private ServerProcessor processor;
    public Map<Integer, Socket> clientMap;

    private ServerTCP() throws IOException {
        serverSocket = new ServerSocket(1337);
        clientMap = new ConcurrentHashMap<>();
        processor = new ServerProcessor();
    }

    public static ServerTCP getInstance() throws IOException {
        if (instance == null) {
            instance = new ServerTCP();
            System.out.println(11);
        }

        return instance;
    }

    public void run() {
        shutdown = false;
        processor.start();

        while (!shutdown) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doStop() {
        shutdown = true;
    }

    public Map<Integer, Socket> getClientMap() {
        return clientMap;
    }

    private class ClientHandler extends Thread {
        private int staticUserID = 0;
        private final int userID;
        private final Socket clientSocket;

        private boolean shutdown;

        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.userID = staticUserID++;

            clientMap.put(userID, clientSocket);
        }

        public void run() {
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String inputLine;

            shutdown = false;
            while (!shutdown) {
                try {
                    inputLine = in.readLine();

                    processor.process(Descriptor.decrypt(
                            parseStringToByte(inputLine)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void doStop() {
            shutdown = true;
        }
    }

    private byte[] parseStringToByte(String str) {
        String[] byteValues = str.substring(1, str.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++)
            bytes[i] = Byte.parseByte(byteValues[i].trim());

        return bytes;
    }
    public static void main(String[] args) throws IOException {
        ServerTCP server = ServerTCP.getInstance();
        server.start();
    }
}

