package Interface.Program;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Store {
    public ArrayList<Group> groups = new ArrayList<>(); //список груп товарів

    public ArrayList<Product> searchedProducts = new ArrayList<>();

    public ArrayList<Product> findProducts() {
        return searchedProducts;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
//    /**
//     * Конструктор створення складу
//     */
//    /**
//     * Додає групу товарів до складу
//     *
//     * @param groupName назва групи товарів, яку необхідно додати
//     * @return true, якщо додавання успішне, в іншому випадку - false
//     */
//    public boolean addGroup(String groupName, String description) {
//        try {
//            new Connector().insertGroup(groupName, description);
//            return true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return false;
//    }
//    /**
//     * Видаляє групу товарів зі складу
//     *
//     * @param name назва групи товаів, яку необхідно видалити
//     * @return true, якщо видалення успішне, в іншому випадку - false
//     */
//    public boolean deleteGroup(String name) {
//        try {
//            new Connector().deleteGroup(name);
//            return true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return false;
//    }
    public Group getGroup(String name) {
        for (Group group : groups) {
            if (Objects.equals(group.getName(), name)) return group;
        }
        return null;
    }
//    /**
//     * Повертає список усіх продуктів складу
//     */
//    public ArrayList<Product> getProducts() {
//        return new Connector().productListByCriteria(new Criteria());
//    }
//    public ArrayList<Product> findProducts(String searchText) {
//        String text = searchText.toLowerCase();
//        return new Connector().productListByCriteria(new Criteria().setName(text));
//    }
//    public ArrayList<Group> getGroups() {
//        return new Connector().groupListByCriteria(new Criteria());
//    }
//
//    public ArrayList<String> getGroupsNames() {
//        ArrayList<Group> groups = new Connector().groupListByCriteria(new Criteria());
//        ArrayList<String> names = new ArrayList<>();
//        for(int i = 0; i < groups.size(); i++){
//            names.add(groups.get(i).getName().toLowerCase());
//        }
//        return names;
//    }
//
//    public ArrayList<String> getProductNames() {
//        ArrayList<Product> products = new Connector().productListByCriteria(new Criteria());
//        ArrayList<String> names = new ArrayList<>();
//        for(int i = 0; i < products.size(); i++){
//            names.add(products.get(i).getName().toLowerCase());
//        }
//        return names;
//    }
//
//    public String toString() {
//        String str = "";
//        ArrayList<Group> groups = new Connector().groupListByCriteria(new Criteria());
//        for (Group group : groups) {
//            str += group + "\n\n";
//        }
//        return str;
//    }
}