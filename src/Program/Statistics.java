package Program;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {

    public static void showGroupStatistics(Group group) {
        JTextArea text = new JTextArea();
        text.setCaretPosition(0);
        text.setEditable(false);
        text.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        text.setBackground(new Color(255, 255, 255));

        String s = group.getName() + "\n";
        long sumMoney = 0; //сума грошей
        int q = 0; //кількість одиниць товару

        for (int d = 0; d < group.getProducts().size(); d++) {
            if (group.getProducts() != null) {

                float price = group.getProducts().get(d).getPrice();
                int num = group.getProducts().get(d).getNumber();
                sumMoney += price * num;
                q+=num;
                s = s + "\n" + (d + 1) + ") " + group.getProducts().get(d).getName().toUpperCase() + "\n";
                s = s + "Ціна: " + group.getProducts().get(d).getPrice() + "\n";
                s = s + "Кількість " + Math.round(group.getProducts().get(d).getNumber()) + "\n";
                s = s + "Виробник: " + group.getProducts().get(d).getBrand() + "\n";
                s = s + "Опис: \n" + group.getProducts().get(d).getDescription() + "\n";

            }
        }


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

        for (int i = 0; i < store.getGroups().size(); i++) {
            if (store.getGroups().get(i).getProducts().size() != 0) {

                s = s + "\n\n";
                s = s + store.getGroups().get(i).getName().toUpperCase() + "\n";
                for (int d = 0; d < store.getGroups().get(i).getProducts().size(); d++) {

                    float price = store.getGroups().get(i).getProducts().get(d).getPrice();
                    int num = store.getGroups().get(i).getProducts().get(d).getNumber();
                    sumMoney += price * num;
                    q+=num;
                    s = s + "\n" + store.getGroups().get(i).getProducts().get(d).getName().toUpperCase() + "\n";
                    s = s + "Ціна: " + store.getGroups().get(i).getProducts().get(d).getPrice() + "\n";
                    s = s + "Кількість " + Math.round(store.getGroups().get(i).getProducts().get(d).getNumber()) + "\n";
                    s = s + "Виробник: " + store.getGroups().get(i).getProducts().get(d).getBrand() + "\n";
                    s = s + "Опис: " + store.getGroups().get(i).getProducts().get(d).getDescription() + "\n";
                }


            } else {
                s = s + "\n" + store.getGroups().get(i).getName().toUpperCase() + "\n ця група пуста" + "\n\n";
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
        if (group.getProducts() != null) {
            for (int i = 0; i < group.getProducts().size(); i++)
                sumMoney += (long) (group.getProducts().get(i).getPrice() * group.getProducts().get(i).getNumber());
        } else sumMoney = 0;
        return sumMoney;
    }

    //повертає кількість одиниць товару в групі
    public static int calculateQuantity(Group group) {
        int q = 0; //сума грошей
        if (group.getProducts() != null) {
            for (int i = 0; i < group.getProducts().size(); i++)
                q += group.getProducts().get(i).getNumber();
        } else q = 0;
        return q;
    }

    //записує все в файл
    public static void addGroupToFile(Group group) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:/" + group.getName() + ".txt"))) {
            bw.write(group.getName().toUpperCase());
            bw.newLine();
            if (group.getProducts() != null) {
                for (int d = 0; d < group.getProducts().size(); d++) {
                    bw.newLine();
                    bw.write(group.getProducts().get(d).getName().toUpperCase());
                    bw.newLine();
                    bw.write("Ціна: " + group.getProducts().get(d).getPrice());
                    bw.newLine();
                    bw.write("Кількість: " + Math.round(group.getProducts().get(d).getNumber()));
                    bw.newLine();
                    bw.write("Виробник: " + group.getProducts().get(d).getBrand());
                    bw.newLine();
                    bw.write("Опис " + group.getProducts().get(d).getDescription());
                    bw.newLine();
                }
                bw.newLine();
                bw.write("Товари на суму: " + calculateMoney(group) + " гривень");
                bw.newLine();
                bw.write("Кількість одиниць товару: " + calculateQuantity(group));
            } else bw.write("Ця група пуста!");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //записує магазин в файл
    public static void addStoreToFile(Store store) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./" + "Store.txt"))) {
            String s = "Магазин ";  //всі товари
            bw.write(s);
            bw.newLine();
            long sumMoney = 0; //сума грошей
            double q = 0; //кількість одиниць товару
            for (int i = 0; i < store.getGroups().size(); i++) {
                if (store.getGroups().get(i).getProducts() != null) {
                    bw.newLine();
                    bw.write(store.getGroups().get(i).getName().toUpperCase());
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
        }
    }
}