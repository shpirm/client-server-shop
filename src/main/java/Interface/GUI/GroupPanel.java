package Interface.GUI;

import Interface.Program.Group;
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
import java.util.ArrayList;

public class GroupPanel extends JPanel {
    private Group group;

    public ProgramWindow getProgramWindow() {
        return programWindow;
    }

    private ProgramWindow programWindow;

    GroupPanel(Group group, ProgramWindow programWindow) {
        this.programWindow = programWindow;
        this.group = group;
        this.setLayout(new BorderLayout());
        init();
    }

    private void init() {
        setBackground(new Color(198, 233, 243));
        add(northPanel(), "North");
        add(centerPanel(), "Center");
        add(southPanel(), "South");
    }

    private JPanel northPanel() {
        JPanel northPanel = new JPanel(new GridLayout(3, 1));
        northPanel.add(titleProductPanel());
        northPanel.add(searchPanel());
        northPanel.add(productsTitle());
        return northPanel;
    }

    private JPanel titleProductPanel() {
        JPanel titlePanel = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(198, 233, 243));
        JLabel title = new JLabel(group.getName());
        title.setForeground(new Color(47, 46, 50));
        title.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        titlePanel.add(title);
        return titlePanel;
    }


    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel((new FlowLayout()));
        searchPanel.setBackground(new Color(198, 233, 243));
        JTextArea searchText = new JTextArea(1, 30);
        searchText.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        searchText.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JButton searchButton = new JButton("Пошук"); //Шукає середвсіх груп та всіх продуктів
        searchButton.setPreferredSize(new Dimension(110, 33));
        searchButton.setBackground(new Color(128, 118, 146));
        searchButton.setForeground(new Color(255, 253, 253));
        searchButton.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_FIND,
                            new JSONObject().put("searchText", searchText.getText()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchPanel.add(searchText);
        searchPanel.add(searchButton);
        return searchPanel;
    }


    private JPanel productsTitle() {
        JPanel productsTitle = new JPanel(new FlowLayout());
        productsTitle.setBackground(new Color(198, 233, 243));
        JLabel products = new JLabel("Наявні товари");
        products.setForeground(new Color(47, 46, 50));
        products.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
        productsTitle.add(products);
        return productsTitle;
    }

    private JPanel centerPanel() {
        JPanel centrePanel = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(scrollProductsPanel());
        return centrePanel;
    }

    private JScrollPane scrollProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(198, 233, 243));
        panel.setBackground(Color.WHITE);
        panel.add(header(), BorderLayout.NORTH);
        JPanel prodPanel = new JPanel(new GridLayout(0, 1));

        ArrayList<Product> productList = group.getProducts();
        if (productList != null) {
            for (int i = 0; i < productList.size(); i++) {

                prodPanel.add(oneProductPanel(productList.get(i)));
            }
            if (productList.size() < 9) {
                for (int n = 0; n < 9 - productList.size();
                     n++) {
                    prodPanel.add(falseProductPanel());
                }
            }
        }

        panel.add(prodPanel, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBackground(new Color(198, 233, 243));
        scroll.setPreferredSize(new Dimension(700, 400));
        return scroll;
    }

    private JPanel falseProductPanel() {
        JPanel oneProductPanel = new JPanel(new BorderLayout());
      //  oneProductPanel.setToolTipText(product.getDescription());
        oneProductPanel.setBorder(new LineBorder(Color.WHITE));
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(255, 253, 253));
        return oneProductPanel;
    }

    private JPanel header() {
        JPanel headerTable = new JPanel(new GridLayout(1, 0));
        headerTable.setPreferredSize(new Dimension(200, 40));
        headerTable.setBackground(new Color(128, 118, 146));
        JLabel productName = new JLabel("Назва");
        productName.setHorizontalAlignment(SwingConstants.CENTER);
        productName.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productName.setFont(new Font("Century", Font.PLAIN, 15));
        productName.setForeground(Color.white);
        JLabel productPrice = new JLabel("Ціна");
        productPrice.setHorizontalAlignment(SwingConstants.CENTER);
        productPrice.setSize(30, 20);
        productPrice.setFont(new Font("Century", Font.PLAIN, 15));
        productPrice.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productPrice.setForeground(Color.white);
        JLabel productNumber = new JLabel("Кількість");
        productNumber.setHorizontalAlignment(SwingConstants.CENTER);
        productNumber.setFont(new Font("Century", Font.PLAIN, 15));
        productNumber.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productNumber.setForeground(Color.white);
        JLabel productBrand = new JLabel("Виробник");
        productBrand.setHorizontalAlignment(SwingConstants.CENTER);
        productBrand.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productBrand.setFont(new Font("Century", Font.PLAIN, 15));
        productBrand.setForeground(Color.white);
        JButton statisticsButton = new JButton("Статистика");
        statisticsButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
        statisticsButton.setPreferredSize(new Dimension(120, 15));
        statisticsButton.setBackground(new Color(80, 80, 80));
        statisticsButton.setForeground(new Color(255, 253, 253));
        statisticsButton.setFont(new Font("Century", Font.PLAIN,
                15));
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  //     programWindow.openGroupStatisticsWindow(group);
            }
        });
        headerTable.add(productName);
        headerTable.add(productPrice);
        headerTable.add(productNumber);
        headerTable.add(productBrand);
        headerTable.add(statisticsButton);
        return headerTable;
    }

    private JPanel fullProductPanel(Product product) {
        JPanel productPanel = new JPanel(new GridLayout(1, 0));
        productPanel.setPreferredSize(new Dimension(200, 20));
        productPanel.setBackground(Color.WHITE);
        JLabel productName = new JLabel(" " + product.getName());
        productName.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productName.setFont(new Font("Century", Font.PLAIN, 16));
        JLabel productPrice = new JLabel("" + product.getPrice() + " грн");
        productPrice.setHorizontalAlignment(SwingConstants.CENTER);
        productPrice.setSize(30, 20);
        productPrice.setFont(new Font("Century", Font.PLAIN, 16));
        productPrice.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productPrice.setBackground(Color.WHITE);
        JLabel productNumber = new JLabel(+product.getNumber() + "");
        productNumber.setHorizontalAlignment(SwingConstants.CENTER);
        productNumber.setFont(new Font("Century", Font.PLAIN, 16));
        productNumber.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JLabel productBrand = new JLabel(product.getBrand());
        productBrand.setHorizontalAlignment(SwingConstants.CENTER);
        productBrand.setBorder(new LineBorder(Color.LIGHT_GRAY));
        productBrand.setFont(new Font("Century", Font.PLAIN, 16));
        JButton button = new JButton("Детальніше");
        button.setBorder(new LineBorder(Color.LIGHT_GRAY));
        button.setPreferredSize(new Dimension(120, 15));
        button.setBackground(new Color(128, 118, 146));
        button.setForeground(new Color(255, 253, 253));
        button.setFont(new Font("Century", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupPanel.this.removeAll();
                programWindow.openProductWindow(product);
            }
        });
        productPanel.add(productName);
        productPanel.add(productPrice);
        productPanel.add(productNumber);
        productPanel.add(productBrand);
        productPanel.add(button);
        return productPanel;
    }

    private JPanel oneProductPanel(Product product) {
        JPanel oneProductPanel = new JPanel(new BorderLayout());
        oneProductPanel.setBorder(new LineBorder(Color.WHITE));
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(255, 253, 253));
        oneProductPanel.add(fullProductPanel(product),
                BorderLayout.CENTER);
        return oneProductPanel;
    }

    private JPanel southPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 0));
        southPanel.setBackground(new Color(198, 233, 243));
        southPanel.setPreferredSize(new Dimension(1200, 30));
        southPanel.add(getBackButton());
        southPanel.add(updateButton());
        southPanel.add(addProductButton());
        southPanel.add(deleteCategoryButton());
        return southPanel;
    }

    private JButton getBackButton() {
        JButton returnBack = new JButton("Назад");
        returnBack.setPreferredSize(new Dimension(120, 25));
        returnBack.setBackground(new Color(128, 118, 146));
        returnBack.setForeground(new Color(255, 253, 253));
        returnBack.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        returnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                CurrentPanel.getInstance().setPanel(getProgramWindow().getStorePanel());
                getProgramWindow().openStoreWindow();
                revalidate();
            }
        });
        return returnBack;
    }

    private JButton updateButton() {
        JButton saveData = new JButton("Оновити");
        saveData.setPreferredSize(new Dimension(120, 25));
        saveData.setBackground(new Color(128, 118, 146));
        saveData.setForeground(new Color(255, 253, 253));
        saveData.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        saveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    User.getInstance().getConnection().sendMessage(UserCommand.GROUP_PRODUCT_LIST,
                            new JSONObject().put("group name", group.getName()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return saveData;
    }

    private JButton addProductButton() {
        JButton addProduct = new JButton("Додати"); //Переносить на сторінку "Додати продукт"
        addProduct.setPreferredSize(new Dimension(120, 25));
        addProduct.setBackground(new Color(128, 118, 146));
        addProduct.setForeground(new Color(255, 253, 253));
        addProduct.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupPanel.this.removeAll();
                add(addProductPanel());
            }
        });
        return addProduct;
    }

    private JButton deleteCategoryButton() {
        JButton deleteCategory = new JButton("Видалити");
        deleteCategory.setPreferredSize(new Dimension(120, 25));
        deleteCategory.setBackground(new Color(128, 118, 146));
        deleteCategory.setForeground(new Color(255, 253, 253));
        deleteCategory.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        deleteCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //"Ви дійсно хочете видалити цю категорію?"
                JFrame ad1 = new JFrame();
                ad1.setSize(400, 150);
                ad1.setLocationRelativeTo(null);
                JPanel northPanel1 = new JPanel();
                JPanel southPanel1 = new JPanel(new GridLayout(1,
                        1));
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
                        removeAll();
                        ad1.setVisible(false);
                        try {
                            User.getInstance().getConnection().sendMessage(UserCommand.GROUP_DELETE,
                                    new JSONObject().put("group name", group.getName()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
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
        });
        return deleteCategory;
    }

    private JTextArea nameArea;
    private JTextArea priceArea;
    private JTextArea numberArea;
    private JTextArea brandArea;
    private JTextArea descriptionArea;

    private JPanel addProductPanel() {
        repaint();
        revalidate();
        JPanel addProductPanel = new JPanel(new BorderLayout());
        addProductPanel.setBackground(new Color(198, 233, 243));
        addProductPanel.add(southProductPanel(), BorderLayout.SOUTH);
        addProductPanel.add(northProductPanel(), BorderLayout.NORTH);
        addProductPanel.add(centerProductPanel(),
                BorderLayout.CENTER);
        return addProductPanel;
    }

    private JPanel northProductPanel() {
        JPanel northProductPanel = new JPanel(new GridLayout(3, 1));
        northProductPanel.setBackground(new Color(198, 233, 243));
        northProductPanel.add(titleProdPanel());
        return northProductPanel;
    }

    private JPanel titleProdPanel() {
        JPanel titlePanel = new JPanel(new
                FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(198, 233, 243));
        JLabel title = new JLabel("Додати товар");
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
        infoPanel.setPreferredSize(new Dimension(700, 350));
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.add(namePanel());
        info.add(pricePanel());
        info.add(numberPanel());
        info.add(brandPanel());
        info.add(falsePanel());
        infoPanel.add(info, BorderLayout.NORTH);
        infoPanel.add(descriptionPanel(), BorderLayout.CENTER);
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
        nameArea = new JTextArea();
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
        priceArea = new JTextArea();
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
        numberArea = new JTextArea();
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
        brandArea = new JTextArea();
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
        descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font(Font.SERIF, Font.PLAIN,
                18));
        descriptionArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
        descriptionArea.setSize(300, 100);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);
        return descriptionPanel;
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
                getProgramWindow().openGroupWindow(group);
                revalidate();
            }
        });
        JButton saveButton = new JButton("Зберегти");
        saveButton.setBackground(new Color(128, 118, 146));
        saveButton.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        saveButton.setForeground(new Color(250, 250, 250));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    removeAll();
                    User.getInstance().getConnection().sendMessage(UserCommand.PRODUCT_INSERT,
                            new JSONObject().put("group", group.getName())
                                    .put("name", nameArea.getText())
                                    .put("amount", numberArea.getText())
                                    .put("price", priceArea.getText())
                                    .put("brand", brandArea.getText())
                                    .put("description", descriptionArea.getText())
                    );
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonsPanel.add(getBackButton);
        buttonsPanel.add(saveButton);
        return buttonsPanel;
    }

    //    private void addProduct() throws NumberFormatException {
//        ArrayList<String> unicProdNames = programWindow.getStore().getProductNames();
//        System.out.println(unicProdNames);
//        try {
//            if
//            (!unicProdNames.contains(nameArea.getText().toLowerCase())) {
//                unicProdNames.add(nameArea.getText().toLowerCase());
//                String name = nameArea.getText();
//                int price = Integer.valueOf(priceArea.getText());
//                int number = Integer.valueOf(numberArea.getText());
//                String brand = brandArea.getText();
//                String description = descriptionArea.getText();
//                new Connector().insertProduct(name, number, price,programWindow.getCurrentGroup().getName(), brand, description);
//            } else {
//                showUnicNameError();
//            }
//        } catch (NumberFormatException exception) {
//            showIllegalFormat();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
    private void showIllegalFormat() {
        JFrame errorMessage = new JFrame();
        errorMessage.setSize(500, 150);
        errorMessage.setLocationRelativeTo(null);
        JPanel northPanel1 = new JPanel();
        JPanel southPanel1 = new JPanel(new GridLayout(1, 1));
        errorMessage.add(northPanel1, BorderLayout.CENTER);
        errorMessage.add(southPanel1, BorderLayout.SOUTH);
        northPanel1.setBackground(new Color(250, 250, 250));
        southPanel1.setBackground(new Color(236, 234, 232));
        JLabel label = new JLabel("Дані введено некоректно.");
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
                numberArea.setText("");
            }
        });
    }

    private void showUnicNameError() {
        JFrame errorMessage = new JFrame();
        errorMessage.setSize(500, 150);
        errorMessage.setLocationRelativeTo(null);
        JPanel northPanel1 = new JPanel();
        JPanel southPanel1 = new JPanel(new GridLayout(1, 1));
        errorMessage.add(northPanel1, BorderLayout.CENTER);
        errorMessage.add(southPanel1, BorderLayout.SOUTH);
        northPanel1.setBackground(new Color(250, 250, 250));
        southPanel1.setBackground(new Color(236, 234, 232));
        JLabel label = new JLabel("Товар з такою назвою вже створений.");
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
                numberArea.setText("");
            }
        });
    }
}