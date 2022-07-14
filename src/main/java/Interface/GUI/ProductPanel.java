package Interface.GUI;

import Interface.Program.Product;
import Structure.Client.User;
import Structure.Commands.UserCommand;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProductPanel extends JPanel {
    private Product product;

    public ProgramWindow getProgramWindow() {
        return programWindow;
    }

    private ProgramWindow programWindow;
    private JTextArea nameArea;
    private JTextArea priceArea;
    private JTextArea numberArea;
    private JTextArea brandArea;
    private JTextArea descriptionArea;

    public JFrame getReduceProductWindow() {
        return reduceProductWindow;
    }

    private JFrame reduceProductWindow;

    public JFrame getAdProductWindow() {
        return adProductWindow;
    }

    private JFrame adProductWindow;

    ProductPanel(Product product, ProgramWindow programWindow) {
        this.product = product;
        this.programWindow = programWindow;
        this.setLayout(new BorderLayout());
        init();
    }

    private void init() {
        setBackground(new Color(198, 233, 243));
        add(northPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
        add(southPanel(), BorderLayout.SOUTH);
    }

    private JPanel northPanel() {
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        northPanel.setBackground(new Color(198, 233, 243));
        northPanel.add(titlePanel());
        return northPanel;
    }

    private JPanel titlePanel() {
        JPanel titlePanel = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(198, 233, 243));
        JLabel title = new JLabel(product.getName());
        title.setForeground(new Color(47, 46, 50));
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        titlePanel.add(title);
        return titlePanel;
    }

    private JPanel centerPanel() {
        JPanel centrePanel = new JPanel();
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(infoPanel());
        return centrePanel;
    }

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(700, 350));
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.add(namePanel());
        info.add(pricePanel());
        info.add(numberPanel());
        info.add(brandPanel());
        info.add(falsePanel());
        infoPanel.add(info, BorderLayout.NORTH);
        infoPanel.add(descriptionPanel(), BorderLayout.CENTER);
        infoPanel.add(savePanel(), BorderLayout.SOUTH);
        return infoPanel;
    }

    private JPanel namePanel() {
        JPanel namePanel = new JPanel(new GridLayout(1, 0));
        namePanel.setBackground(new Color(236, 234, 232));
        JLabel nameLabel = new JLabel("Назва: ");
        nameLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        nameArea = new JTextArea("" + product.getName() +
                "");
        nameArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        nameArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        namePanel.add(nameLabel);
        namePanel.add(nameArea);
        return namePanel;
    }

    private JPanel pricePanel() {
        JPanel pricePanel = new JPanel(new GridLayout(1, 0));
        pricePanel.setBackground(new Color(236, 234, 232));
        JLabel priceLabel = new JLabel("Ціна: ");
        priceLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        priceArea = new JTextArea("" + product.getPrice()
                + "");
        priceArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        priceArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        pricePanel.add(priceLabel);
        pricePanel.add(priceArea);
        return pricePanel;
    }

    private JPanel numberPanel() {
        JPanel numberPanel = new JPanel(new GridLayout(1, 0));
        numberPanel.setBackground(new Color(236, 234, 232));
        JLabel numberLabel = new JLabel("Кількість: ");
        numberLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        numberArea = new JTextArea("" +
                product.getNumber() + "");
        numberArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        numberArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        numberPanel.add(numberLabel);
        numberPanel.add(numberArea);
        return numberPanel;
    }

    private JPanel brandPanel() {
        JPanel brandPanel = new JPanel(new GridLayout(1, 0));
        brandPanel.setBackground(new Color(236, 234, 232));
        JLabel brandLabel = new JLabel("Виробник: ");
        brandLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        brandLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        brandArea = new JTextArea("" + product.getBrand()
                + "");
        brandArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        brandArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        brandPanel.add(brandLabel);
        brandPanel.add(brandArea);
        return brandPanel;
    }

    private JPanel descriptionPanel() {
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(new Color(236, 234, 232));
        descriptionPanel.setPreferredSize(new Dimension(500, 100));
        JLabel descriptionLabel = new JLabel("Опис: ");
        descriptionLabel.setFont(new Font(Font.SERIF, Font.PLAIN,
                20));

        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        descriptionArea = new JTextArea("" +
                product.getDescription() + "");
        descriptionArea.setFont(new Font(Font.SERIF, Font.PLAIN,
                18));
        descriptionArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        descriptionArea.setSize(300, 100);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);
        return descriptionPanel;
    }

    private JPanel savePanel() {
        JPanel savePanel = new JPanel(new GridLayout(0, 1));
        savePanel.setBackground(new Color(198, 233, 243));
        JButton saveButton = new JButton("Зберегти");
        saveButton.setBackground(new Color(128, 118, 146));
        saveButton.setFont(new Font(Font.SERIF, Font.PLAIN,
                25));
        saveButton.setForeground(new Color(250, 250, 250));
        saveButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_UPDATE,
                            new JSONObject()
                                    .put("old name", product.getName())
                                    .put("new name", nameArea.getText())
                                    .put("amount", numberArea.getText())
                                    .put("price", priceArea.getText())
                                    .put("brand", brandArea.getText())
                                    .put("description", descriptionArea.getText()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

//                if(nameArea.getText().trim().equals("")){
//                    showIllegalFormat("Назву");
//                    nameArea.setText(product.getName().trim());
//                }else product.setName(nameArea.getText().trim());
//
//                if(!(priceArea.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?"))){
//                    showIllegalFormat("Ціну");
//                    priceArea.setText(product.getPrice() + "");
//                } else product.setPrice(Double.valueOf(priceArea.getText().trim()));
//                if (!(numberArea.getText().trim().matches("[0-9]*"))) {
//                    showIllegalFormat("Кількість");
//                    numberArea.setText(product.getNumber() + "");
//                } else product.setNumber(Integer.valueOf(numberArea.getText().trim()));
//                if(brandArea.getText().trim().equals("")){
//                    showIllegalFormat("Назву бренду");
//                    brandArea.setText(product.getBrand().trim());
//                } else product.setBrand(brandArea.getText().trim());
//
//                product.setDescription(descriptionArea.getText().trim());
            }
        });
        savePanel.add(saveButton);
        return savePanel;
    }

    private JPanel falsePanel() {
        JPanel falsePanel = new JPanel(new GridLayout(0, 1));
        falsePanel.setBackground(new Color(198, 233, 243));
        return falsePanel;
    }

    private JPanel southPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
        buttonsPanel.setBackground(new Color(198, 233, 243));
        JButton getBackButton = new JButton("Назад");
        getBackButton.setBackground(new Color(128, 118, 146));
        getBackButton.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        getBackButton.setForeground(new Color(250, 250, 250));
        getBackButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
        getBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                if (programWindow.getSearchPanel() != null) {
                    programWindow.openStoreWindow();
                }
                if (programWindow.getCurrentGroup() != null) {

                    programWindow.openGroupWindow(programWindow.getCurrentGroup());
                }
            }
        });
        JButton addNumberButton = new JButton("Додати");
        addNumberButton.setBackground(new Color(128, 118, 146));
        addNumberButton.setFont(new Font(Font.SERIF, Font.PLAIN,
                16));
        addNumberButton.setForeground(new Color(250, 250, 250));
        addNumberButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
        addNumberButton.addActionListener(new ActionListener() {
            @Override //інтерфейс додавання одиниць товару
            public void actionPerformed(ActionEvent e) {
                JFrame adProductWindow = new JFrame();
                openAddProductWindow();
                revalidate();
            }
        });
        JButton deleteNumberButton = new JButton("Списати");
        deleteNumberButton.setBackground(new Color(128, 118, 146));
        deleteNumberButton.setFont(new Font(Font.SERIF, Font.PLAIN,
                16));
        deleteNumberButton.setForeground(new Color(250, 250, 250));
        deleteNumberButton.setBorder(new
                LineBorder(Color.LIGHT_GRAY));
        deleteNumberButton.addActionListener(new ActionListener() {
            @Override //інтерфейс списування товарів
            public void actionPerformed(ActionEvent e) {
                openReduceProductWindow();
                revalidate();
            }
        });
        JButton deleteProductButton = new JButton("Видалити");
        deleteProductButton.setBackground(new Color(128, 118, 146));
        deleteProductButton.setFont(new Font(Font.SERIF, Font.PLAIN,
                16));
        deleteProductButton.setForeground(new Color(250, 250, 250));
        deleteProductButton.setBorder(new
                LineBorder(Color.LIGHT_GRAY));
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //"Ви дійсно хочете видалити цей товар?"
                deleteProductWindow();
            }
        });
        buttonsPanel.add(getBackButton);
        buttonsPanel.add(addNumberButton);
        buttonsPanel.add(deleteNumberButton);
        buttonsPanel.add(deleteProductButton);
        return buttonsPanel;
    }

    private void openAddProductWindow() {
        adProductWindow = new JFrame();
        adProductWindow.setSize(400, 150);
        adProductWindow.setLocationRelativeTo(null);
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(236, 234, 232));
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.setBackground(new Color(198, 233, 243));
        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.setBackground(new Color(198, 233, 243));
        adProductWindow.add(northPanel, BorderLayout.NORTH);
        adProductWindow.add(centerPanel, BorderLayout.CENTER);
        adProductWindow.add(southPanel, BorderLayout.EAST);
        JLabel howManyLabel = new JLabel("Cкільки одиниць продукту додати?");
        howManyLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JTextField textField = new JTextField();
        textField.setSize(50, 50);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 45));
        JButton addProduct = new JButton("Додати");
        addProduct.setBackground(new Color(128, 118, 146));
        addProduct.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        addProduct.setForeground(new Color(250, 250, 250));
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_AMOUNT_ADD,
                            new JSONObject().put("product", product.getName())
                                    .put("amount", textField.getText())
                    );
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        northPanel.add(howManyLabel);
        centerPanel.add(textField);
        southPanel.add(addProduct);
        adProductWindow.setVisible(true);
    }

    private void openReduceProductWindow() {
        reduceProductWindow = new JFrame();
        reduceProductWindow.setSize(400, 150);
        reduceProductWindow.setLocationRelativeTo(null);
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(236, 234, 232));
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.setBackground(new Color(198, 233, 243));
        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.setBackground(new Color(198, 233, 243));
        reduceProductWindow.add(northPanel, BorderLayout.NORTH);
        reduceProductWindow.add(centerPanel, BorderLayout.CENTER);
        reduceProductWindow.add(southPanel, BorderLayout.EAST);
        JLabel howMany = new JLabel("Cкільки одиниць продукту списати?");
        howMany.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JTextField textField = new JTextField();
        textField.setSize(50, 50);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 45));
        JButton addProduct = new JButton("Списати");
        addProduct.setBackground(new Color(128, 118, 146));
        addProduct.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        addProduct.setForeground(new Color(250, 250, 250));
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_AMOUNT_REDUCE,
                            new JSONObject().put("product", product.getName())
                                    .put("amount", textField.getText())
                    );
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        northPanel.add(howMany);
        centerPanel.add(textField);
        southPanel.add(addProduct);
        reduceProductWindow.setVisible(true);
    }

    private void deleteProductWindow() {
        JFrame ad1 = new JFrame();
        ad1.setSize(400, 150);
        ad1.setLocationRelativeTo(null);
        JPanel northPanel1 = new JPanel();
        JPanel southPanel1 = new JPanel(new GridLayout(1, 1));
        ad1.add(northPanel1, BorderLayout.CENTER);
        ad1.add(southPanel1, BorderLayout.SOUTH);
        northPanel1.setBackground(new Color(236, 234, 232));
        southPanel1.setBackground(new Color(236, 234, 232));
        JLabel label = new JLabel("Ви впевнені?");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JButton yes = new JButton("Так");
        JButton no = new JButton("Ні");
        yes.setBackground(new Color(128, 118, 146));
        yes.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        yes.setForeground(new Color(250, 250, 250));
        northPanel1.add(label, BorderLayout.CENTER);
        southPanel1.add(yes, BorderLayout.WEST);
        southPanel1.add(no, BorderLayout.EAST);
        ad1.setVisible(true);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ad1.setVisible(false);
                removeAll();
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_DELETE,
                            new JSONObject().put("product", product.getName())
                    );
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        no.setBackground(new Color(128, 118, 146));
        no.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        no.setForeground(new Color(250, 250, 250));
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ad1.setVisible(false);
            }
        });
    }

    public void showIllegalFormat(String message) {
        JFrame errorMessage = new JFrame();
        errorMessage.setSize(500, 150);
        errorMessage.setLocationRelativeTo(null);
        JPanel northPanel1 = new JPanel();
        JPanel southPanel1 = new JPanel(new GridLayout(1, 1));
        errorMessage.add(northPanel1, BorderLayout.CENTER);
        errorMessage.add(southPanel1, BorderLayout.SOUTH);
        northPanel1.setBackground(new Color(250, 250, 250));
        southPanel1.setBackground(new Color(236, 234, 232));
        JLabel label = new JLabel(message + " введено некоректно.");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        JButton yes = new JButton("Ок");
        yes.setBackground(new Color(128, 118, 146));
        yes.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        yes.setForeground(new Color(250, 250, 250));
        northPanel1.add(label, BorderLayout.CENTER);
        southPanel1.add(yes, BorderLayout.WEST);
        errorMessage.setVisible(true);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorMessage.removeAll();
                errorMessage.setVisible(false);
            }
        });
    }
}

