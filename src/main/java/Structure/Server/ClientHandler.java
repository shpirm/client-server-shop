package Structure.Server;

import Structure.Utility.Descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.Socket;
import java.util.Map;

public class ClientHandler extends Thread {
    private static int staticUserID = 0;
    private final int userID;

    private final Socket clientSocket;
    ServerProcessor processor;
    private boolean shutdown;

    private BufferedReader in;

    public ClientHandler(Socket socket, Map<Integer, Socket> clientMap, ServerProcessor processor) {
        this.clientSocket = socket;
        this.userID = staticUserID++;
        this.processor = processor;
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
                if ((inputLine = in.readLine()) != null)
                    processor.process(Descriptor.decrypt(
                            parseStringToByte(inputLine)));

            } catch (Exception e) {
                //          System.out.println("Connection went down");
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

    private byte[] parseStringToByte(String str) {
        String[] byteValues = str.substring(1, str.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i = 0, len = bytes.length; i < len; i++)
            bytes[i] = Byte.parseByte(byteValues[i].trim());

        return bytes;
    }
}