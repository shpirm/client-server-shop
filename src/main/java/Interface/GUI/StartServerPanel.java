package Interface.GUI;

import Interface.Program.Store;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartServerPanel extends JPanel {
    private ProgramWindow programWindow;
    private Store store;

    public StartServerPanel(Store store, ProgramWindow programWindow) {
        this.store = store;
        this.programWindow = programWindow;
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(600, 600));
        init();
    }

    public void init() {

        setBackground(new Color(198, 233, 243));
        add(northPanel(), BorderLayout.NORTH);
        add(centerProductPanel(), BorderLayout.CENTER);
        add(southProductPanel(), BorderLayout.SOUTH);
    }

    private JPanel northPanel() {
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        northPanel.setBackground(new Color(198, 233, 243));
        northPanel.add(titlePanel());
        return northPanel;
    }

    private JPanel titlePanel() {
        JPanel title = new JPanel(new FlowLayout());
        title.setBackground(new Color(198, 233, 243));
        JLabel store = new JLabel("Start Server");
        store.setForeground(new Color(47, 46, 50));
        store.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        title.add(store);
        return title;
    }

    private JPanel centerProductPanel() {
        JPanel centrePanel = new JPanel();
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(infoPanel());
        return centrePanel;
    }
    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(700, 350));
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.add(portPanel());
        info.add(passwordPanel());
        infoPanel.add(info);
        return infoPanel;
    }

    private JPanel portPanel() {
        JPanel namePanel = new JPanel(new GridLayout(1, 0));
        namePanel.setBackground(new Color(236, 234, 232));
        JLabel portLabel = new JLabel("Port: ");
        portLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JTextArea portArea = new JTextArea();
        portArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        portArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        namePanel.add(portLabel);
        namePanel.add(portArea);
        return namePanel;
    }
    private JPanel passwordPanel() {
        JPanel passwordPanel = new JPanel(new GridLayout(1, 0));
        passwordPanel.setBackground(new Color(236, 234, 232));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JTextArea passwordArea = new JTextArea();
        passwordArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        passwordArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordArea);
        return passwordPanel;
    }

    private JPanel falsePanel() {
        JPanel falsePanel = new JPanel(new GridLayout(0, 1));
        falsePanel.setBackground(new Color(198, 233, 243));
        return falsePanel;
    }

    private JPanel southProductPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
        buttonsPanel.setBackground(new Color(198, 233, 243));
        JButton saveButton = new JButton("Зберегти");
        saveButton.setBackground(new Color(128, 118, 146));
        saveButton.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        saveButton.setForeground(new Color(250, 250, 250));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.openConnectionPanel();
            }
        });
        buttonsPanel.add(saveButton);
        return buttonsPanel;
    }
}
