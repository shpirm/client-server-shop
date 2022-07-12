package Interface.GUI;

import Interface.Program.Group;
import Interface.Program.Product;
import Interface.Program.Store;
import Structure.Client.ClientTCP;
import Structure.Client.User;
import Structure.Commands.UserCommand;
import Structure.Database.Connections;
import Structure.Utility.Cypher;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ConnectionsPanel extends JPanel {
    private Store store;
    private ArrayList<Connections> users;

    public ProgramWindow getProgramWindow() {
        return programWindow;
    }

    private ProgramWindow programWindow;

    public JFrame getPasswordWindow() {
        return passwordWindow;
    }

    private JFrame passwordWindow;

    public JFrame getPasswordUserWindow() {
        return passwordUserWindow;
    }

    private JFrame passwordUserWindow;
    public ConnectionsPanel(Store store, ProgramWindow programWindow) {
        this.store = store;
        this.users = programWindow.getUsers();

        this.programWindow = programWindow;
        this.setLayout(new BorderLayout());
        init();
        setVisible(true);

        CurrentPanel.getInstance().setPanel(this);
    }
    private void init() {
        setBackground(new Color(198, 233, 243));
        add(northPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
        add(southPanel(), BorderLayout.SOUTH);
    }
    private JPanel northPanel() {
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        northPanel.add(titlePanel());
        northPanel.add(falseProductPanel());
        northPanel.add(categoriesTitlePanel());
        return northPanel;
    }
    private JPanel titlePanel() {
        JPanel title = new JPanel(new FlowLayout());
        title.setBackground(new Color(198, 233, 243));
        JLabel store = new JLabel("Підключення");
        store.setForeground(new Color(47, 46, 50));
        store.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        title.add(store);
        return title;
    }
    private JPanel categoriesTitlePanel() {
        JPanel forTitle = new JPanel(new FlowLayout());
        forTitle.setBackground(new Color(198, 233, 243));
        JPanel titles = new JPanel(new GridLayout(1, 2));
        titles.setBackground(new Color(198, 233, 243));
        JLabel login = new JLabel("Login");
        JLabel address = new JLabel("Address");
        login.setForeground(new Color(47, 46, 50));
        login.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        address.setForeground(new Color(47, 46, 50));
        address.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        titles.add(login);
        titles.add(address);
        forTitle.add(titles);
        return forTitle;
    }
    private JPanel centerPanel() {
        JPanel centrePanel = new JPanel(new GridLayout(1, 1));
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(categoriesPanel());
        return centrePanel;
    }
    private JPanel categoriesPanel() {
        JPanel categories = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        categories.setBackground(new Color(198, 233, 243));
        categories.add(scroll());
        return categories;
    }
    private JScrollPane scroll() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setForeground(new Color(255, 253, 253));
        for (int i = 0; i < users.size(); i++) {
            panel.add(oneLinePanel(users.get(i)));
        }
       if (users.size() < 9) {
            for (int n = 0; n < 9 - users.size(); n++)
            {
                panel.add(falseProductPanel());
            }
        }
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBackground(new Color(198, 233, 243));
        scroll.setPreferredSize(new Dimension(700, 350));
        return scroll;
    }
    private JPanel falseProductPanel() {
        JPanel oneProductPanel = new JPanel(new BorderLayout());
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(198, 233, 243));
        return oneProductPanel;
    }
    public JPanel oneLinePanel(Connections connection) {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton login = new JButton(connection.getLogin());
        JButton address = new JButton(connection.getHost() + ": " + connection.getPort());
        login.setFont(new Font("Century", Font.PLAIN, 18));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPasswordUserWindow(connection.getLogin());
            }
        });
        login.setPreferredSize(new Dimension(130, 50));
        login.setBackground(new Color(250, 250, 250));
        login.setForeground(new Color(0, 0, 0));
        login.setFont(new Font("Arial", Font.PLAIN, 17));
        login.setBorder(new LineBorder(new Color(220, 220, 220)));

        address.setPreferredSize(new Dimension(130, 50));
        address.setBackground(new Color(250, 250, 250));
        address.setForeground(new Color(0, 0, 0));
        address.setFont(new Font("Arial", Font.PLAIN, 17));
        address.setBorder(new LineBorder(new Color(220, 220, 220)));

        panel.add(login);
        panel.add(address);
        return panel;
    }

    private JPanel southPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 0));
        southPanel.setBackground(new Color(198, 233, 243));
        southPanel.setBorder(new LineBorder(Color.WHITE));
        southPanel.setPreferredSize(new Dimension(getWidth(), 33));
        southPanel.add(addCategoryButton());
        southPanel.add(deleteAddress());
        return southPanel;
    }
    private JButton addCategoryButton() {
        JButton addCat = new JButton("Додати");
        addCat.setPreferredSize(new Dimension(120, 25));
        addCat.setBackground(new Color(128, 118, 146));
        addCat.setForeground(new Color(255, 253, 253));
        addCat.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        addCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                add(addAddressPanel());
                revalidate();
            }
        });
        return addCat;
    }

    private JButton deleteAddress() {
        JButton deleteAdd = new JButton("Видалити");
        deleteAdd.setPreferredSize(new Dimension(120, 25));
        deleteAdd.setBackground(new Color(128, 118, 146));
        deleteAdd.setForeground(new Color(255, 253, 253));
        deleteAdd.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        deleteAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User.getInstance().getConnection().
                            sendMessage(UserCommand.USER_LIST, new JSONObject().put("text", "text"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return deleteAdd;
    }

    private JTextArea loginArea;
    private JTextArea passwordArea;
    private JTextArea hostnameArea;
    private JTextArea portArea;

    private JPanel addAddressPanel() {
        repaint();
        revalidate();
        JPanel newAddressPanel = new JPanel(new BorderLayout());
        newAddressPanel.setBackground(new Color(198, 233, 243));
        newAddressPanel.add(southProductPanel(), BorderLayout.SOUTH);
        newAddressPanel.add(northProductPanel(), BorderLayout.NORTH);
        newAddressPanel.add(centerProductPanel(),
                BorderLayout.CENTER);
        return newAddressPanel;
    }
    private JPanel northProductPanel() {
        JPanel northProductPanel = new JPanel(new GridLayout(3, 1));
        northProductPanel.setBackground(new Color(198, 233, 243));
        northProductPanel.add(titleAddressPanel());
        return northProductPanel;
    }
    private JPanel titleAddressPanel() {
        JPanel titlePanel = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(198, 233, 243));
        JLabel title = new JLabel("Додати адресу");
        title.setForeground(new Color(47, 46, 50));
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        titlePanel.add(title);
        return titlePanel;
    }
    private JPanel centerProductPanel() {
        JPanel centrePanel = new JPanel();
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(infoPanel());
        return centrePanel;
    }
    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(198, 233, 243));
        infoPanel.setPreferredSize(new Dimension(700, 350));
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.add(loginPanel());
        info.add(falsePanel());
        info.add(passwordPanel());
        info.add(falsePanel());
        info.add(hostnamePanel());
        info.add(falsePanel());
        info.add(portPanel());
        info.add(falsePanel());
        info.add(falsePanel());
        infoPanel.add(info, BorderLayout.NORTH);
        return infoPanel;
    }
    private JPanel loginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(1, 0));
        loginPanel.setBackground(new Color(236, 234, 232));
        JLabel loginLabel = new JLabel("Login: ");
        loginLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        loginArea = new JTextArea();
        loginArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        loginArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        loginPanel.add(loginLabel);
        loginPanel.add(loginArea);
        return loginPanel;
    }
    private JPanel passwordPanel() {
        JPanel passwordPanel = new JPanel(new GridLayout(1, 0));
        passwordPanel.setBackground(new Color(236, 234, 232));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        passwordArea = new JTextArea();
        passwordArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        passwordArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordArea);
        return passwordPanel;
    }
    private JPanel hostnamePanel() {
        JPanel hostnamePanel = new JPanel(new GridLayout(1, 0));
        hostnamePanel.setBackground(new Color(236, 234, 232));
        JLabel hostnameLabel = new JLabel("Hostname: ");
        hostnameLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        hostnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostnameLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        hostnameArea = new JTextArea();
        hostnameArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        hostnameArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        hostnamePanel.add(hostnameLabel);
        hostnamePanel.add(hostnameArea);
        return hostnamePanel;
    }
    private JPanel portPanel() {
        JPanel portPanel = new JPanel(new GridLayout(1, 0));
        portPanel.setBackground(new Color(236, 234, 232));
        JLabel portLabel = new JLabel("Port: ");
        portLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        portArea = new JTextArea();
        portArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        portArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        portPanel.add(portLabel);
        portPanel.add(portArea);
        return portPanel;
    }

    private JPanel falsePanel() {
        JPanel falsePanel = new JPanel(new GridLayout(0, 1));
        falsePanel.setBackground(new Color(198, 233, 243));
        return falsePanel;
    }
    private JPanel southProductPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
        buttonsPanel.setBackground(new Color(198, 233, 243));
        JButton getBackButton = new JButton("Назад");
        getBackButton.setBackground(new Color(128, 118, 146));
        getBackButton.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        getBackButton.setForeground(new Color(250, 250, 250));
        getBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                programWindow.openConnectionPanel();
            }
        });
        JButton saveButton = new JButton("Зберегти");
        saveButton.setBackground(new Color(128, 118, 146));
        saveButton.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        saveButton.setForeground(new Color(250, 250, 250));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPasswordWindow();
            }
        });
        buttonsPanel.add(getBackButton);
        buttonsPanel.add(saveButton);
        return buttonsPanel;
    }

    private void addAddress() throws NumberFormatException {
        //TODO!!!!!!!!!
    }

    private void showPasswordWindow(){
        passwordWindow = new JFrame();
        passwordWindow.setSize(500, 200);
        passwordWindow.setLocationRelativeTo(null);
        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(new Color(198, 233, 243));

        JPanel forTitle = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Введіть пароль");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        forTitle.add(label);

        JPanel forArea = new JPanel(new FlowLayout());
        JTextArea passwordAreaServer = new JTextArea();
        passwordAreaServer.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        passwordAreaServer.setBorder(new LineBorder(Color.LIGHT_GRAY));
        forArea.add(passwordAreaServer);

        JPanel forButton = new JPanel(new FlowLayout());
        JButton yes = new JButton("Ок");
        yes.setBackground(new Color(128, 118, 146));
        yes.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        yes.setForeground(new Color(250, 250, 250));
        forButton.add(yes);

        info.add(forTitle);
        info.add(passwordAreaServer);
        info.add(yes);

        passwordWindow.add(info);

        passwordWindow.setVisible(true);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientTCP clientTCP = new ClientTCP();
                try {
                    clientTCP.startConnection(
                            loginArea.getText(),
                            passwordArea.getText(),
                            hostnameArea.getText(),
                            Integer.parseInt(portArea.getText()),
                            passwordAreaServer.getText());

                    User.getInstance().setConnection(clientTCP);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void showPasswordUserWindow(String login) {
        passwordUserWindow = new JFrame();
        passwordUserWindow.setSize(500, 200);
        passwordUserWindow.setLocationRelativeTo(null);
        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(new Color(198, 233, 243));

        JPanel forTitle = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Введіть пароль від підлючення: ");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        forTitle.add(label);

        JPanel forArea = new JPanel(new FlowLayout());
        JTextArea passwordAreaServer = new JTextArea();
        passwordAreaServer.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        passwordAreaServer.setBorder(new LineBorder(Color.LIGHT_GRAY));
        forArea.add(passwordAreaServer);

        JPanel forButton = new JPanel(new FlowLayout());
        JButton yes = new JButton("Ок");
        yes.setBackground(new Color(128, 118, 146));
        yes.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        yes.setForeground(new Color(250, 250, 250));
        forButton.add(yes);

        info.add(forTitle);
        info.add(passwordAreaServer);
        info.add(yes);

        passwordUserWindow.add(info);

        passwordUserWindow.setVisible(true);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User.getInstance().setConnection(ClientTCP.clientMap.get(login));
                    User.getInstance().getConnection().sendMessage(UserCommand.ACCESS_REQUEST,
                            new JSONObject().put("password", Cypher.getEncryptedBytes(passwordAreaServer.getText()))
                                    .put("login", login));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}