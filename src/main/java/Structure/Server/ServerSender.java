package Structure.Server;

import javafx.util.Pair;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSender extends Thread{
    private static final int THREAD_AMOUNT = 10;

    private boolean shutdown;
    private ExecutorService service;

    private ConcurrentLinkedQueue<Pair<Integer, byte[]>> queueOfPackets;

    public ServerSender() {
        queueOfPackets = new ConcurrentLinkedQueue<>();
        service = Executors.newFixedThreadPool(THREAD_AMOUNT);
    }

    public void run() {
        shutdown = false;

        queueOfPackets = new ConcurrentLinkedQueue<>();
        service = Executors.newFixedThreadPool(THREAD_AMOUNT);

        while (!shutdown) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (queueOfPackets) {
                            if (queueOfPackets.size() != 0) {
                                Pair<Integer, byte[]> pair = queueOfPackets.poll();
                                command(pair.getKey(), pair.getValue());
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            });
        }
        service.shutdown();
    }

    public void doStop() {
        shutdown = false;
    }

    public void sendMessage(int clientId, byte[] packet) {
        queueOfPackets.add(new Pair<>(clientId, packet));
    }
    private void command(Integer key, byte[] message) throws Exception {
        Socket clientSocket = ServerTCP.getInstance(3333, "123").getClientMap().get(key);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(Arrays.toString(message));
    }
}
