package Interface.GUI;

import Interface.Program.Group;
import Interface.Program.Store;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StorePanel extends JPanel {
    private Store store;
    private ProgramWindow programWindow;
    public StorePanel(Store store, ProgramWindow programWindow) {
        this.store = store;
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
        northPanel.add(titlePanel());
        northPanel.add(searchPanel());
        northPanel.add(categoriesTitlePanel());
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
        JButton searchButton = new JButton("Пошук"); //Шукає середвсіх груп та всіх продуктів
        searchButton.setPreferredSize(new Dimension(110, 33));
        searchButton.setBackground(new Color(128, 118, 146));
        searchButton.setForeground(new Color(255, 253, 253));
        searchButton.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.openSearchWindow(searchText.getText());
            }
        });
        searchPanel.add(searchText);
        searchPanel.add(searchButton);
        return searchPanel;
    }
    private JPanel categoriesTitlePanel() {
        JPanel categoriesTitle = new JPanel(new FlowLayout());
        categoriesTitle.setBackground(new Color(198, 233, 243));
        JLabel types = new JLabel("Всі категорії");
        types.setForeground(new Color(47, 46, 50));
        types.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        categoriesTitle.add(types);
        return categoriesTitle;
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
        for (int i = 0; i < store.getGroups().size(); i++) {
            panel.add(oneCategoryPanel(store.getGroups().get(i)));
        }
        if (store.getProducts().size() < 9) {
            for (int n = 0; n < 9 - store.getProducts().size(); n++)
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
        oneProductPanel.setBorder(new LineBorder(Color.WHITE));
        oneProductPanel.setPreferredSize(new Dimension(100, 40));
        oneProductPanel.setBackground(new Color(255, 253, 253));
        return oneProductPanel;
    }
    private JButton oneCategoryPanel(Group group) {
        JButton button = new JButton(group.getName());
        button.setFont(new Font("Century", Font.PLAIN, 18));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.openGroupWindow(group);
            }
        });
        button.setPreferredSize(new Dimension(130, 50));
        button.setBackground(new Color(250, 250, 250));
        button.setForeground(new Color(0, 0, 0));
        button.setFont(new Font("Arial", Font.PLAIN, 17));
        button.setBorder(new LineBorder(new Color(220, 220, 220)));
        return button;
    }
    private JPanel southPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 0));
        southPanel.setBackground(new Color(198, 233, 243));
        southPanel.setBorder(new LineBorder(Color.WHITE));
        southPanel.setPreferredSize(new Dimension(getWidth(), 33));
        southPanel.add(backButton());
        southPanel.add(addCategoryButton());
        southPanel.add(createFileButton());
        southPanel.add(statisticsButton());
        return southPanel;
    }

    private JButton backButton() {
        JButton back = new JButton("Назад"); //Переносить на сторінку назад
        back.setPreferredSize(new Dimension(120, 25));
        back.setBackground(new Color(128, 118, 146));
        back.setForeground(new Color(255, 253, 253));
        back.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                programWindow.openConnectionPanel();
            }
        });
        return back;
    }

    private JButton addCategoryButton() {
        JButton addCat = new JButton("Додати"); //Переносить на сторінку "Додати категорію"
        addCat.setPreferredSize(new Dimension(120, 25));
        addCat.setBackground(new Color(128, 118, 146));
        addCat.setForeground(new Color(255, 253, 253));
        addCat.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        addCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                add(addCategoryPanel());
                revalidate();
            }
        });
        return addCat;
    }
    private JButton statisticsButton() {
        JButton addCat = new JButton("Статистика");
        addCat.setPreferredSize(new Dimension(120, 25));
        addCat.setBackground(new Color(128, 118, 146));
        addCat.setForeground(new Color(255, 253, 253));
        addCat.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        addCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.openStatisticsWindow();
            }
        });
        return addCat;
    }
    private JButton createFileButton() {
        JButton addCat = new JButton("Зберегти");
        addCat.setPreferredSize(new Dimension(120, 25));
        addCat.setBackground(new Color(128, 118, 146));
        addCat.setForeground(new Color(255, 253, 253));
        addCat.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        addCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programWindow.createStoreFile();
            }
        });
        return addCat;
    }

    private JPanel addCategoryPanel() {
        JPanel addCategoryPanel = new JPanel(new BorderLayout());
        addCategoryPanel.setBackground(new Color(198, 233, 243));
        addCategoryPanel.add(addCatNorthPanel(), BorderLayout.NORTH);
        addCategoryPanel.add(addCatMenu(), BorderLayout.CENTER);
        addCategoryPanel.add(buttonsPanel(), BorderLayout.SOUTH);
        return addCategoryPanel;
    }
    private JPanel addCatNorthPanel() {
        JPanel addCatNorthPanel = new JPanel(new BorderLayout());
        addCatNorthPanel.setBackground(new Color(128, 118, 146));
        addCatNorthPanel.setPreferredSize(new Dimension(200, 70));
        JLabel store = new JLabel("Додати категорію");
        store.setForeground(Color.white);
        store.setBorder(new LineBorder(Color.LIGHT_GRAY));
        store.setHorizontalAlignment(SwingConstants.CENTER);
        store.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        addCatNorthPanel.add(store, BorderLayout.CENTER);
        return addCatNorthPanel;
    }
    private JPanel addCatMenu() {
        JPanel menu = new JPanel();
        menu.setBackground(new Color(198, 233, 243));
        menu.add(addCategoryName());
        menu.add(addCategoryDetails());
        return menu;
    }
    JTextArea newCategory;
    private JPanel addCategoryName() {
        JPanel namePanel = new JPanel(new GridLayout(0, 1));
        namePanel.setPreferredSize(new Dimension(700, 150));
        namePanel.setBackground(new Color(198, 233, 243));
        JLabel addCategory = new JLabel("Введіть назву");
        addCategory.setHorizontalAlignment(SwingConstants.CENTER);
        addCategory.setVerticalAlignment(SwingConstants.CENTER);
        addCategory.setForeground(Color.BLACK);
        addCategory.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        newCategory = new JTextArea(1, 20);
        newCategory.setBorder(new LineBorder(Color.LIGHT_GRAY));
        newCategory.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        namePanel.add(addCategory, BorderLayout.NORTH);
        namePanel.add(newCategory, BorderLayout.SOUTH);
        return namePanel;
    }
    JTextArea newDetails;
    private JPanel addCategoryDetails() {
        JPanel namePanel = new JPanel(new GridLayout(0, 1));
        namePanel.setPreferredSize(new Dimension(700, 150));
        namePanel.setBackground(new Color(198, 233, 243));
        JLabel addCategory = new JLabel("Введіть опис");
        addCategory.setBackground(new Color(128, 118, 146));
        addCategory.setHorizontalAlignment(SwingConstants.CENTER);
        addCategory.setForeground(Color.BLACK);
        addCategory.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        newDetails = new JTextArea(3, 20);
        newDetails.setBorder(new LineBorder(Color.LIGHT_GRAY));
        newDetails.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(newDetails);
        scroll.setBackground(new Color(198, 233, 243));
        scroll.setPreferredSize(new Dimension(700, 250));
        namePanel.add(addCategory, BorderLayout.NORTH);
        namePanel.add(scroll, BorderLayout.SOUTH);
        return namePanel;
    }
    private JPanel buttonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
        buttonsPanel.setPreferredSize(new Dimension(getWidth(), 30));
        buttonsPanel.add(getBackButton());
        buttonsPanel.add(saveNewCategoryButton());
        return buttonsPanel;
    }
    private JButton getBackButton() {
        JButton getBack = new JButton("Назад");
        getBack.setBackground(new Color(128, 118, 146));
        getBack.setForeground(Color.white);
        getBack.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        getBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                programWindow.openStoreWindow();
                revalidate();
            }
        });
        return getBack;
    }


    private JButton saveNewCategoryButton() {
        JButton saveCategory = new JButton("Зберегти");
        saveCategory.setBackground(new Color(128, 118, 146));
        saveCategory.setForeground(Color.white);
        saveCategory.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        saveCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> unicCatNames = programWindow.getStore().getGroupsNames();
                System.out.println(unicCatNames);
                if
                (!unicCatNames.contains(newCategory.getText().toLowerCase()) &&
                        !newCategory.getText().equals("")
                        && !newDetails.getText().equals("")) {

                    unicCatNames.add(newCategory.getText().toLowerCase());
                    Group newGroup = new Group(newCategory.getText(),
                            newDetails.getText());
                    newGroup.setDescription(newDetails.getText());
                    programWindow.getStore().addGroup(newGroup);
                    programWindow.remove(StorePanel.this);
                    programWindow.openStoreWindow();
                } else if ((newCategory.getText().equals("") ||
                        newDetails.getText().equals(""))
                        &&!unicCatNames.contains(newCategory.getText().toLowerCase())) {
                    showEmptyAreaError();
                } else {
                    showUnicNameError();
                }
            }
        });
        return saveCategory;
    }
    private void showEmptyAreaError(){
        JFrame errorMessage = new JFrame();
        errorMessage.setSize(500, 150);
        errorMessage.setLocationRelativeTo(null);
        JPanel northPanel1 = new JPanel();
        JPanel southPanel1 = new JPanel(new GridLayout(1, 1));
        errorMessage.add(northPanel1, BorderLayout.CENTER);
        errorMessage.add(southPanel1, BorderLayout.SOUTH);
        northPanel1.setBackground(new Color(250, 250, 250));
        southPanel1.setBackground(new Color(236, 234, 232));
        JLabel label = new JLabel("Введіть назву та опис.");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
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
                newCategory.setText("");
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
        JLabel label = new JLabel("Категорія товарів з такою назвою вже створена.");
                label.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
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
                newCategory.setText("");
            }
        });
    }
}