package Structure.Server;

import Structure.Utility.Cypher;
import Structure.Utility.Descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTCP extends Thread {

    private String password;

    private static ServerTCP instance;
    private boolean shutdown;
    private ServerSocket serverSocket;

    private ServerProcessor processor;
    public Map<Integer, Socket> clientMap;

    private ServerTCP(int port, String password) throws Exception {
        serverSocket = new ServerSocket(port);
        clientMap = new ConcurrentHashMap<>();
        processor = new ServerProcessor();

        this.password = Cypher.getEncryptedBytes(password);
    }

    public static ServerTCP getInstance(int port, String password) throws Exception {
        if (instance == null) {
            instance = new ServerTCP(port, password);
        }
        return instance;
    }

    public void run() {
        shutdown = false;
        processor.start();

        while (!shutdown) {
            try {
                new ClientHandler(serverSocket.accept(),clientMap,  processor ).start();
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

    public void stopConnection(int userId) throws IOException {
        Socket socket = clientMap.get(userId);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        clientMap.remove(userId);
        socket.close();
        in.close();
    }

    public void doStop() {
        shutdown = true;
    }

    public Map<Integer, Socket> getClientMap() {
        return clientMap;
    }

    public String getPassword() {
        return password;
    }

    public static void main(String[] args) throws Exception {
        try {
            ServerTCP server = ServerTCP.getInstance(3333, "123");
            server.start();
        } catch (BindException e) {
            System.out.println("Error create server!");
        }
    }
}

