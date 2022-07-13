package Structure.Client;

import Interface.GUI.ConnectionsPanel;
import Interface.GUI.CurrentPanel;
import Interface.GUI.StorePanel;
import Interface.Program.Group;
import Interface.Program.Product;
import Structure.Commands.OtherCommand;
import Database.Connections;
import Structure.Commands.UserCommand;
import Structure.Packet.Packet;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        System.out.println("User " + packet.getBMsg().getBUserId() + " received packet: { Command: " + command + ", Info: " + jsonObject + " }");

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

                ClientTCP.clientMapID.remove(packet.getBMsg().getBUserId());
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

                panel.getProgramWindow().openStoreWindow();
                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getStorePanel());

                User.getInstance().setConnection(ClientTCP.clientMapID.get(packet.getBMsg().getBUserId()));
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_LIST, new JSONObject().put("text","text"));
            }
            case ACCESS_ERROR -> {
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
            }
            case INSERT_GROUP_SUCCESS -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();
                Group group = new Group(
                        String.valueOf(jsonObject.get("name")),
                        String.valueOf(jsonObject.get("description")));

                panel.getProgramWindow().getStore().getGroups().add(group);

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

                panel.getProgramWindow().remove(panel.getProgramWindow().getStorePanel());
                panel.getProgramWindow().openStoreWindow();
            }
            case GROUP_LIST -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();

                panel.getProgramWindow().getStore().getGroups().clear();

                JSONArray array = jsonObject.getJSONArray("groups");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    panel.getProgramWindow().getStore().getGroups().add(new Group (
                            String.valueOf(obj.get("name")),
                            String.valueOf(obj.get("description"))
                    ));
                }

                panel.getProgramWindow().remove(panel.getProgramWindow().getStorePanel());
                panel.getProgramWindow().openStoreWindow();
            }
            case GROUP_PRODUCT_LIST -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();
                String groupName = String.valueOf(jsonObject.get("group"));
                panel.getProgramWindow().getStore().getGroup(groupName).getProducts().clear();

                JSONArray array = jsonObject.getJSONArray("products");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    panel.getProgramWindow().getStore().getGroup(groupName).getProducts().add(new Product(
                            String.valueOf(obj.get("name")),
                            obj.getInt("amount"),
                            obj.getDouble("price"),
                            String.valueOf(obj.get("brand")),
                            String.valueOf(obj.get("description"))
                    ));
                }

                panel.getProgramWindow().openGroupWindow(
                        panel.getProgramWindow().getStore().getGroup(groupName));
            }
        }
    }
}