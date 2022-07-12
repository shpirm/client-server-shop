package Structure.Client;

import Structure.Utility.Descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread {
    private boolean shutdown;
    private Socket clientSocket;
    private ClientProcessor processor;

    private BufferedReader in;
    public ClientReceiver(Socket socket) throws InterruptedException {
        this.clientSocket = socket;
        processor = new ClientProcessor();
    }
    public void run() {
        shutdown = false;
        processor.start();

        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (!shutdown) {
            try {
                String str;
                if ((str = in.readLine()) != null) {
                    processor.process(Descriptor.decrypt(parseStringToByte(str)));
                }
            } catch (Exception e) {
                //
            }
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
