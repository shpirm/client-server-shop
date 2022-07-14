package Interface.GUI;

import Interface.Program.Product;
import Interface.Program.Store;
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
public class SearchPanel extends JPanel {
    Store store;

    public ProgramWindow getProgramWindow() {
        return programWindow;
    }

    ProgramWindow programWindow;
    String searchText;
    public SearchPanel(Store store, ProgramWindow programWindow) {
        this.store = store;
        this.programWindow = programWindow;
        setLayout(new BorderLayout());
        init();
    }
    public SearchPanel(Store store, ProgramWindow programWindow,
                       String searchText) {
        this.store = store;
        this.programWindow = programWindow;
        this.searchText = searchText;
        setLayout(new BorderLayout());
        init();
    }
    private void init() {
        setBackground(new Color(198, 233, 243));
        setBackground(new Color(198, 233, 243));
        add(northPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
        add(southPanel(), BorderLayout.SOUTH);
    }
    private JPanel northPanel() {
        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(titlePanel());
        northPanel.add(searchPanel());
        return northPanel;
    }
    private JPanel titlePanel() {
        JPanel title = new JPanel(new FlowLayout());
        title.setBackground(new Color(198, 233, 243));
        JLabel store = new JLabel("Склад");
        store.setForeground(new Color(47, 46, 50));
        store.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        title.add(store);
        return title;
    }
    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(new Color(198, 233, 243));
        JTextArea searchText = new JTextArea(1, 30);
        searchText.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
        searchText.setBorder(new LineBorder(Color.LIGHT_GRAY));
        searchText.setToolTipText("Введіть назву групи або товару");
        JButton searchButton = new JButton("Пошук"); //Шукає серед всіх груп та всіх продуктів
        searchButton.setPreferredSize(new Dimension(110, 33));
        searchButton.setBackground(new Color(128, 118, 146));
        searchButton.setForeground(new Color(255, 253, 253));
        searchButton.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.removeAll();
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
    private JPanel centerPanel() {
        JPanel centrePanel = new JPanel(new GridLayout(1, 1));
        centrePanel.setBackground(new Color(198, 233, 243));
        centrePanel.add(searchResultsPanel());
        return centrePanel;
    }
    private JPanel searchResultsPanel() {
        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setBackground(new Color(198, 233, 243));
        searchResultsPanel.add(scrollProductsPanel());
        return searchResultsPanel;
    }
    private JScrollPane scrollProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(198, 233, 243));
        panel.setBackground(Color.WHITE);
        panel.add(header(), BorderLayout.NORTH);
        JPanel prodPanel = new JPanel(new GridLayout(0, 1));
        ArrayList<Product> results = store.findProducts();
        if (results != null) {
            for (int i = 0; i < results.size(); i++) {
                prodPanel.add(oneProductPanel(results.get(i)));
            }
        }
        if (results.size()<9){
            for (int n = 0; n< 9 - results.size(); n++){
                prodPanel.add(falseProductPanel());
            }
        }
        panel.add(prodPanel, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBackground(new Color(198, 233, 243));
        scroll.setPreferredSize(new Dimension(700, 400));
        return scroll;
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
        JLabel emptyButton = new JLabel();
        emptyButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
        emptyButton.setPreferredSize(new Dimension(120, 15));
        emptyButton.setBackground(new Color(80, 80, 80));
        emptyButton.setForeground(new Color(255, 253, 253));
        emptyButton.setFont(new Font("Century", Font.PLAIN, 15));
        headerTable.add(productName);
        headerTable.add(productPrice);
        headerTable.add(productNumber);
        headerTable.add(productBrand);
        headerTable.add(emptyButton);
        return headerTable;
    }
    private JPanel falseProductPanel(){
        JPanel oneProductPanel = new JPanel(new BorderLayout());
// oneProductPanel.setToolTipText(product.getDescription());
        oneProductPanel.setBorder(new LineBorder(Color.WHITE));
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(255, 253, 253));
        return oneProductPanel;
    }
    private JPanel oneProductPanel(Product product) {
        JPanel oneProductPanel = new JPanel(new BorderLayout());
// oneProductPanel.setToolTipText(product.getDescription());
        oneProductPanel.setBorder(new LineBorder(Color.WHITE));
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(255, 253, 253));
        oneProductPanel.add(fullProductPanel(product),
                BorderLayout.CENTER);
        return oneProductPanel;
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
                SearchPanel.this.removeAll();
                programWindow.openProductWindow(product);
                CurrentPanel.getInstance().setPanel(getProgramWindow().getProductPanel());
            }
        });
        productPanel.add(productName);
        productPanel.add(productPrice);
        productPanel.add(productNumber);
        productPanel.add(productBrand);
        productPanel.add(button);
        return productPanel;
    }
    private JPanel southPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 0));
        southPanel.setBackground(new Color(198, 233, 243));
        southPanel.setPreferredSize(new Dimension(1200, 30));
        southPanel.add(getBackButton());
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
                SearchPanel.this.removeAll();
                programWindow.openStoreWindow();
            }
        });
        return returnBack;
    }
}