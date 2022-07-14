package Structure.Client;

import Interface.GUI.*;
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
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientProcessor extends Thread {

    private boolean shutdown;
    private ConcurrentLinkedQueue<Packet> queueOfPackets;

    public ClientProcessor() {
        queueOfPackets = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        shutdown = false;

        while (!shutdown) {
            try {
                synchronized (queueOfPackets) {
                    if (queueOfPackets.size() != 0)
                        command(queueOfPackets.poll());
                }
            } catch (Exception e) {
                System.out.println(e);
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
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_LIST, new JSONObject().put(" ", " "));
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
            case GROUP_INSERT_SUCCESS -> {
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

                panel.removeAll();
                panel.getProgramWindow().openStoreWindow();
            }
            case GROUP_LIST -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();

                panel.getProgramWindow().getStore().getGroups().clear();

                JSONArray array = jsonObject.getJSONArray("groups");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    panel.getProgramWindow().getStore().getGroups().add(new Group(
                            String.valueOf(obj.get("name")),
                            String.valueOf(obj.get("description"))
                    ));
                }

                panel.removeAll();
                panel.getProgramWindow().openStoreWindow();
            }

            case GROUP_PRODUCT_LIST -> {
                GroupPanel panel = (GroupPanel) CurrentPanel.getInstance().getPanel();
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

                panel.removeAll();
                panel.getProgramWindow().openGroupWindow(
                        panel.getProgramWindow().getStore().getGroup(groupName));
                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getGroupPanel());
            }
            case PRODUCT_INSERT_SUCCESS -> {
                GroupPanel panel = (GroupPanel) CurrentPanel.getInstance().getPanel();
                Product product = new Product(
                        String.valueOf(jsonObject.get("name")),
                        jsonObject.getInt("amount"),
                        jsonObject.getDouble("price"),
                        String.valueOf(jsonObject.get("brand")),
                        String.valueOf(jsonObject.get("description")));

                panel.getProgramWindow().getStore().getGroup(String.valueOf(jsonObject.get("group")))
                        .getProducts().add(product);

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

                panel.removeAll();

                panel.getProgramWindow().openGroupWindow(
                        panel.getProgramWindow().getStore().getGroup(
                                String.valueOf(jsonObject.get("group"))));
            }
            case GROUP_DELETE -> {
                GroupPanel panel = (GroupPanel) CurrentPanel.getInstance().getPanel();
                panel.removeAll();

                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getStorePanel());
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_LIST, new JSONObject().put(" ", " "));
            }
            case PRODUCT_FIND_LIST -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();
                panel.getProgramWindow().openSearchWindow();
                panel.getProgramWindow().getStore().searchedProducts = new ArrayList<>();

                JSONArray array = jsonObject.getJSONArray("products");
                for (int i = 0; i < array.length(); i++) {
                    jsonObject = array.getJSONObject(i);
                    panel.getProgramWindow().getStore().findProducts().add(new Product(
                            String.valueOf(jsonObject.get("name")),
                            jsonObject.getInt("amount"),
                            jsonObject.getDouble("price"),
                            String.valueOf(jsonObject.get("brand")),
                            String.valueOf(jsonObject.get("description"))
                    ));
                }
                panel.removeAll();
                panel.getProgramWindow().openSearchWindow();
            }
            case PRODUCT_UPDATE_SUCCESS -> {
                ProductPanel panel = (ProductPanel) CurrentPanel.getInstance().getPanel();

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
            case PRODUCT_AMOUNT_ADD -> {
                ProductPanel panel = (ProductPanel) CurrentPanel.getInstance().getPanel();
                panel.getAdProductWindow().setVisible(false);

                panel.removeAll();
                panel.getProgramWindow().openGroupWindow(panel.getProgramWindow().getCurrentGroup());

                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getGroupPanel());
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_PRODUCT_LIST,
                        new JSONObject().put("group name", panel.getProgramWindow().getCurrentGroup().getName()));
            }
            case PRODUCT_AMOUNT_REDUCE_ERROR -> {
                ProductPanel panel = (ProductPanel) CurrentPanel.getInstance().getPanel();
                panel.getReduceProductWindow().setVisible(false);

                JFrame window = new JFrame();
                window.setSize(650, 80);
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

                panel.getProgramWindow().openProductWindow(panel.getProgramWindow().getCurrentProduct());
            }
            case PRODUCT_AMOUNT_REDUCE_SUCCESS -> {
                ProductPanel panel = (ProductPanel) CurrentPanel.getInstance().getPanel();
                panel.getReduceProductWindow().setVisible(false);

                panel.removeAll();
                panel.getProgramWindow().openGroupWindow(panel.getProgramWindow().getCurrentGroup());

                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getGroupPanel());
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_PRODUCT_LIST,
                        new JSONObject().put("group name", panel.getProgramWindow().getCurrentGroup().getName()));
            }
            case SHOP_UPDATE_LIST -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();
                panel.getProgramWindow().getStore().getGroups().clear();

                JSONArray shop = jsonObject.getJSONArray("shop");
                for (int i = 0; i < shop.length(); i++) {
                    JSONObject group = shop.getJSONObject(i);
                    Group currentGroup = new Group(
                            String.valueOf(group.get("name")),
                            String.valueOf(group.get("description"))
                    );
                    panel.getProgramWindow().getStore().getGroups().add(currentGroup);
                    JSONArray array = group.getJSONArray("products");
                    for (int j = 0; j < array.length(); j++) {
                        JSONObject obj = array.getJSONObject(j);
                        panel.getProgramWindow().getStore().getGroup(currentGroup.getName()).getProducts().add(new Product(
                                String.valueOf(obj.get("name")),
                                obj.getInt("amount"),
                                obj.getDouble("price"),
                                String.valueOf(obj.get("brand")),
                                String.valueOf(obj.get("description"))
                        ));
                    }
                }
                panel.getProgramWindow().openStatisticsWindow();
            }
            case PRODUCT_DELETE -> {
                ProductPanel panel = (ProductPanel) CurrentPanel.getInstance().getPanel();

                panel.removeAll();
                panel.getProgramWindow().openGroupWindow(panel.getProgramWindow().getCurrentGroup());

                CurrentPanel.getInstance().setPanel(panel.getProgramWindow().getGroupPanel());
                User.getInstance().getConnection().sendMessage(UserCommand.GROUP_PRODUCT_LIST,
                        new JSONObject().put("group name", panel.getProgramWindow().getCurrentGroup().getName()));
            }
            case GROUP_INSERT_ERROR -> {
                StorePanel panel = (StorePanel) CurrentPanel.getInstance().getPanel();

                JFrame window = new JFrame();
                window.setSize(650, 80);
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

                panel.removeAll();
                panel.getProgramWindow().openStoreWindow();
            }
            case PRODUCT_INSERT_ERROR -> {
                GroupPanel panel = (GroupPanel) CurrentPanel.getInstance().getPanel();

                JFrame window = new JFrame();
                window.setSize(650, 80);
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

                panel.removeAll();

                panel.getProgramWindow().openGroupWindow(
                        panel.getProgramWindow().getStore().getGroup(
                                String.valueOf(jsonObject.get("group"))));
            }
            case PRODUCT_UPDATE_ERROR -> {
                JFrame window = new JFrame();
                window.setSize(650, 80);
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
        }
    }
}
