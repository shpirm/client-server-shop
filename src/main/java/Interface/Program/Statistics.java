package Interface.Program;

import Structure.DataBase.Connector;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Statistics {

    public static void showGroupStatistics(Group group) {
        JTextArea text = new JTextArea();
        text.setCaretPosition(0);
        text.setEditable(false);
        text.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        text.setBackground(new Color(255, 255, 255));

        String s = group.getName() + "\n";
        s += "Опис: " + group.getDescription() + "\n";

        long sumMoney = 0; //сума грошей
        int q = 0; //кількість одиниць товару
        try {
            ArrayList<Product> productList = new Connector().getAllProductsInGroup(group.getName());

        for (int d = 0; d < productList.size(); d++) {
            if (productList != null) {

                double price = productList.get(d).getPrice();
                int num = productList.get(d).getNumber();
                sumMoney += price * num;
                q+=num;
                s = s + "\n" + (d + 1) + ") " + productList.get(d).getName().toUpperCase() + "\n";
                s = s + "Ціна: " + productList.get(d).getPrice() + "\n";
                s = s + "Кількість " + Math.round(productList.get(d).getNumber()) + "\n";
                s = s + "Виробник: " + productList.get(d).getBrand() + "\n";
                s = s + "Опис: \n" + productList.get(d).getDescription() + "\n";

            }
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //todo ???????
        if (sumMoney > 100000) {
            double sumMoneyInGrands = Math.ceil(sumMoney / 1000);
            s = s + "\n\nЗагальна вартість усіх товарів: " + sumMoneyInGrands + " тис грн";

        } else if (sumMoney > 100000000) {
            double sumMoneyInGrands = Math.ceil(sumMoney / 1000000);
            s = s + "\n\nЗагальна вартість усіх товарів: " + sumMoneyInGrands + " млн грн";
        } else {
            s = s + "\n\nВ магазині товарів на суму: " + sumMoney + " гривень";
        }
        s = s + "\nКількість одиниць товару: " + q;

        s += "Загальна вартість товарів в групі: " + calculateGroupMoney(group) + "\n";

        text.setText(s);


        JScrollPane jp = new JScrollPane(text);
        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jp.setPreferredSize(new Dimension(450, 450));

        JFrame frame = new JFrame();
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 450));
        panel.setBackground(new Color(128, 118, 146));
        panel.add(jp);

        frame.add(panel);

    }

    public static void showStatistics(Store store) {

        JTextArea text = new JTextArea();
        text.setCaretPosition(0);
        text.setEditable(false);
        text.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        text.setBackground(new Color(255, 255, 255));

        String s = "Склад\n";  //всі товари
        long sumMoney = 0; //сума грошей
        int q = 0; //кількість одиниць товару
        ArrayList<Product> productList;
        for (int i = 0; i < store.getGroups().size(); i++) {
            try {
                productList = new Connector().getAllProductsInGroup(store.getGroups().get(i).getName());
                if (productList.size() != 0) {

                    s = s + "\n\n";
                    s = s + store.getGroups().get(i).getName().toUpperCase() + "\n";
                    s += "Опис: " + store.getGroups().get(i).getDescription() + "\n";
                    s += "Загальна вартість товарів в групі: " + calculateGroupMoney(store.getGroups().get(i)) + "\n";

                    for (int d = 0; d < productList.size(); d++) {

                        double price = productList.get(d).getPrice();
                        int num = productList.get(d).getNumber();
                        sumMoney += price * num;
                        q += num;
                        s = s + "\n" + productList.get(d).getName().toUpperCase() + "\n";
                        s = s + "Ціна: " + productList.get(d).getPrice() + "\n";
                        s = s + "Кількість " + productList.get(d).getNumber() + "\n";
                        s = s + "Виробник: " + productList.get(d).getBrand() + "\n";
                        s = s + "Опис: " + productList.get(d).getDescription() + "\n";
                    }


                } else {
                    s = s + "\n" + store.getGroups().get(i).getName().toUpperCase() + "\n ця група пуста" + "\n\n";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (sumMoney > 100000) {
            double sumMoneyInGrands = Math.ceil(sumMoney / 1000);
            s = s + "\n\nЗагальна вартість усіх товарів: " + sumMoneyInGrands + " тис грн";

        } else if (sumMoney > 100000000) {
            double sumMoneyInGrands = Math.ceil(sumMoney / 1000000);
            s = s + "\n\nЗагальна вартість усіх товарів: " + sumMoneyInGrands + " млн грн";
        } else {
            s = s + "\n\nВ магазині товаів на суму: " + sumMoney + " гривень";
        }
        s = s + "\nКількість одиниць товару: " + q;

        text.setText(s);


        JScrollPane jp = new JScrollPane(text);
        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jp.setPreferredSize(new Dimension(450, 450));

        JFrame frame = new JFrame();
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 450));
        panel.setBackground(new Color(128, 118, 146));
        panel.add(jp);

        frame.add(panel);

    }


    //повертає, скільки на яку суму грошей міститься товару в групі
    public static long calculateMoney(Group group) {
        long sumMoney = 0; //сума грошей
        try {
            ArrayList<Product> productList = new Connector().getAllProductsInGroup(group.getName());
            if (productList != null) {
                for (int i = 0; i < productList.size(); i++)
                    sumMoney += (long) (productList.get(i).getPrice() * productList.get(i).getNumber());
            } else sumMoney = 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return sumMoney;
    }

    //повертає кількість одиниць товару в групі
    public static int calculateQuantity(Group group) {
        int q = 0; //сума грошей
        try {
            ArrayList<Product> productList = new Connector().getAllProductsInGroup(group.getName());
            if (productList != null) {
                for (int i = 0; i < productList.size(); i++)
                    q += productList.get(i).getNumber();
            } else q = 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return q;
    }

    public static double calculateGroupMoney(Group group){
        double q = 0;
        try {
            ArrayList<Product> productList = new Connector().getAllProductsInGroup(group.getName());
        if(productList != null){
            for(int i = 0; i < productList.size(); i++) {
                q += productList.get(i).getPrice() * productList.get(i).getNumber();
            }
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return q;
    }

    //записує все в файл
    //todo ????????? видалити?
    public static void addGroupToFile(Group group) {

        /*try (BufferedWriter bw = new BufferedWriter(new FileWriter("./" + group.getName() + ".txt"))) {
            bw.write(group.getName().toUpperCase());
            bw.newLine();
            bw.write("Опис: " + group.getDescription());
            bw.newLine();
            bw.write("Вартість товарів в цій групі: " + calculateGroupMoney(group));
            bw.newLine();
            ArrayList<Product> productList = new Connector().getAllProductsInGroup(group.getName());
            if (productList != null) {
                for (int d = 0; d < productList.size(); d++) {
                    bw.newLine();
                    bw.write(productList.get(d).getName().toUpperCase());
                    bw.write(productList.get(d).getName().toUpperCase());
                    bw.newLine();
                    bw.write("Ціна: " + productList.get(d).getPrice());
                    bw.newLine();
                    bw.write("Кількість: " + Math.round(productList.get(d).getNumber()));
                    bw.newLine();
                    bw.write("Виробник: " + productList.get(d).getBrand());
                    bw.newLine();
                    bw.write("Опис: " + productList.get(d).getDescription());
                    bw.newLine();
                }
                bw.newLine();
                bw.write("Товари на суму: " + calculateMoney(group) + " гривень");
                bw.newLine();
                bw.write("Кількість одиниць товару: " + calculateQuantity(group));
            } else bw.write("Ця група пуста!");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    //записує магазин в файл
    //todo ????????? видалити?
    public static void addStoreToFile(Store store) {
        /*try (BufferedWriter bw = new BufferedWriter(new FileWriter("./" + "Store.txt"))) {
            String s = "Магазин ";  //всі товари
            bw.write(s);
            bw.newLine();
            long sumMoney = 0; //сума грошей
            double q = 0; //кількість одиниць товару
            for (int i = 0; i < store.getGroups().size(); i++) {
                if (store.getGroups().get(i).getProducts() != null) {
                    bw.newLine();
                    bw.write(store.getGroups().get(i).getName().toUpperCase());
                    bw.write("Опис: " + store.getGroups().get(i).getDescription());
                    bw.write("Загальна вартість товарів в групі" + calculateGroupMoney(store.getGroups().get(i)));
                    bw.newLine();
                    for (int d = 0; d < store.getGroups().get(i).getProducts().size(); d++) {
                        bw.newLine();
                        bw.write(store.getGroups().get(i).getProducts().get(d).getName().toUpperCase());
                        bw.newLine();
                        bw.write("Ціна: " + store.getGroups().get(i).getProducts().get(d).getPrice());
                        bw.newLine();
                        bw.write("Кількість " + Math.round(store.getGroups().get(i).getProducts().get(d).getNumber()));
                        bw.newLine();
                        bw.write("Виробник: " + store.getGroups().get(i).getProducts().get(d).getBrand());
                        bw.newLine();
                        bw.write("Опис " + store.getGroups().get(i).getProducts().get(d).getDescription());
                        bw.newLine();
                    }
                    String[] str = s.split("\\s{3}");
                    sumMoney += calculateMoney(store.getGroups().get(i));
                    q += calculateQuantity(store.getGroups().get(i));

                } else {
                    bw.newLine();
                    bw.write(store.getGroups().get(i).getName() + ": ця група пуста");
                    bw.newLine();
                }
            }
            bw.newLine();
            bw.write("В магазині товари на суму: " + sumMoney + " гривень");
            bw.newLine();
            bw.write("Кількість одиниць товару: " + q);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
    }
}