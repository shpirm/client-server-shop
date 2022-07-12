package Interface.GUI;

import Interface.Program.Group;
import Interface.Program.Product;
import Interface.Program.Statistics;
import Interface.Program.Store;
import Structure.Database.Connections;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ProgramWindow extends JFrame {
    private Store store;
    private Group currentGroup;
    private Product currentProduct;
    private StorePanel storePanel;
    private GroupPanel groupPanel;
    private ProductPanel productPanel;

    public ArrayList<Connections> getUsers() {
        return users;
    }

    private ArrayList<Connections> users = new ArrayList<>();
    private SearchPanel searchPanel;
    private JPanel currentWindow;
    private StartServerPanel startServerPanel;
    private ConnectionsPanel connectionsPanel;

    public ProgramWindow(Store store) {
        super("Склад");
        this.store = store;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(120, 20);
        setPreferredSize(new Dimension(1000, 650));
        init();
        pack();
        setVisible(true);
    }

    private void init() {
//        storePanel = new StorePanel(store, this);
//        add(storePanel);

        connectionsPanel = new ConnectionsPanel(store, this);
        add(connectionsPanel);
    }

    public void openSearchWindow(String searchText) {
        currentProduct = null;
        currentGroup = null;
        if (storePanel != null) {
            remove(storePanel);
        }
        if (searchPanel != null) {
            remove(searchPanel);
        }
        if (groupPanel != null) {
            remove(groupPanel);
        }
        searchPanel = new SearchPanel(store, this, searchText);
        add(searchPanel);
        revalidate();
    }

    public void openStoreWindow() {
        currentProduct = null;
        currentGroup = null;
        if (groupPanel != null) {
            remove(groupPanel);
        }
        if (productPanel != null) {
            remove(productPanel);
        }
        if (searchPanel != null) {
            remove(searchPanel);
        }
        storePanel = new StorePanel(store, this);
        add(storePanel);
        revalidate();
    }

    public void openGroupWindow(Group group) {
        currentGroup = group;
        if (storePanel != null) {
            remove(storePanel);
        }
        if (productPanel != null) {
            remove(productPanel);
        }
        groupPanel = new GroupPanel(group, this);
        add(groupPanel);
        revalidate();
    }

    public void openProductWindow(Product product) {
        currentProduct = product;
        if (groupPanel != null) {
            remove(groupPanel);
        }
        if (storePanel != null) {
            remove(storePanel);
        }
        productPanel = new ProductPanel(product, this);
        add(productPanel);
        revalidate();
    }

    public void openConnectionPanel(){
        connectionsPanel = new ConnectionsPanel(store, this);
        add(connectionsPanel);
        revalidate();
    }

    public void openGroupStatisticsWindow(Group group) {
        Statistics.showGroupStatistics(group);
    }

    public void openStatisticsWindow() {
        Statistics.showStatistics(this.store);
    }

    public void createStoreFile() {
        Statistics.addStoreToFile(this.store);
    }

    public void createGroupFile(Group group) {
        Statistics.addGroupToFile(this.getCurrentGroup());
    }

    public Store getStore() {
        return store;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }
}