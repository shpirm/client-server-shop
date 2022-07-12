package Structure.Client;

import Interface.GUI.ConnectionsPanel;
import Interface.GUI.CurrentPanel;
import Interface.GUI.StorePanel;
import Interface.Program.Group;
import Structure.Commands.OtherCommand;
import Structure.Commands.UserCommand;
import Structure.Database.Connections;
import Structure.Packet.Packet;
import Structure.Server.ServerSender;
import Structure.Utility.Encryptor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientProcessor extends Thread {

    private boolean shutdown;
    private ConcurrentLinkedQueue<Packet> queueOfPackets;

    public ClientProcessor() throws InterruptedException {
        queueOfPackets = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        shutdown = false;

        while (!shutdown) {
            if (queueOfPackets.size() != 0) {
                try {
                    command(queueOfPackets.poll());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void doStop() {
        shutdown = true;
    }

    public void process(Packet packet) {
        queueOfPackets.add(packet);
    }

    private void command(Packet packet) throws Exception {
        OtherCommand command = OtherCommand.values()[packet.getBMsg().getCType()];
        JSONObject jsonObject = new JSONObject(IOUtils.toString(packet.getBMsg().getMessage()));
        System.out.println("User " + packet.getBPktId() + " send command: " + command + " Info = " + jsonObject);

        switch (command) {
            case CONNECT_SUCCESS -> {
                ConnectionsPanel panel = (ConnectionsPanel) CurrentPanel.getInstance().getPanel();
                panel.getPasswordWindow().setVisible(false);
                Connections connection = new Connections(
                        String.valueOf(jsonObject.get("login")),
                        String.valueOf(jsonObject.get("host")),
                        jsonObject.getInt("port"));
                panel.getProgramWindow().getUsers().add(connection);
                panel.getProgramWindow().remove(panel);
                panel.getProgramWindow().openConnectionPanel();

                JFrame window = new JFrame();
                window.setSize(500, 80);
                window.setLocationRelativeTo(null);
                JPanel info = new JPanel(new GridLayout(1, 1));
                info.setBackground(new Color(198, 233, 243));

                JPanel forTitle = new JPanel(new FlowLayout());

                JLabel label = new JLabel(String.valueOf(jsonObject.get("answer")));
                label.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                forTitle.add(label);

                info.add(forTitle);
                window.add(info);

                window.setVisible(true);
            }
            case CONNECT_ERROR -> {
                ConnectionsPanel panel = (ConnectionsPanel) CurrentPanel.getInstance().getPanel();
                panel.getPasswordWindow().setVisible(false);

                JFrame window = new JFrame();
                window.setSize(500, 80);
                window.setLocationRelativeTo(null);
                JPanel info = new JPanel(new GridLayout(1, 1));
                info.setBackground(new Color(198, 233, 243));

                JPanel forTitle = new JPanel(new FlowLayout());

                JLabel label = new JLabel(String.valueOf(jsonObject.get("answer")));
                label.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                forTitle.add(label);

                info.add(forTitle);
                window.add(info);

                window.setVisible(true);

                ClientTCP.clientMap.remove(packet.getBMsg().getBUserId());
            }
            case ACCESS_SUCCESS -> {
                ConnectionsPanel panel = (ConnectionsPanel) CurrentPanel.getInstance().getPanel();
                panel.getPasswordUserWindow().setVisible(false);

                JFrame window = new JFrame();
                window.setSize(500, 80);
                window.setLocationRelativeTo(null);
                JPanel info = new JPanel(new GridLayout(1, 1));
                info.setBackground(new Color(198, 233, 243));

                JPanel forTitle = new JPanel(new FlowLayout());

                JLabel label = new JLabel(String.valueOf(jsonObject.get("answer")));
                label.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
                forTitle.add(label);

                info.add(forTitle);
                window.add(info);

                window.setVisible(true);

                panel.getProgramWindow().remove(panel);
                panel.getProgramWindow().openStoreWindow();
            }
//            case USER_LIST -> {
//                JSONArray array = jsonObject.getJSONArray("users");
//                ArrayList<Connections> list = new ArrayList();
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject obj = array.getJSONObject(i);
//                    list.add(new Connections(
//                            String.valueOf(obj.get("login")),
//                            String.valueOf(obj.get("host")),
//                            obj.getInt("port")));
//                }
//
//                ConnectionsPanel panel = (ConnectionsPanel) CurrentPanel.getInstance().getPanel();
//                panel.setUsers(list);
//
//                panel.removeAll();
//                panel.revalidate();
//            }
        }

    }
}