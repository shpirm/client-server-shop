package Interface.Program;
import Structure.DataBase.Connector;
import Structure.DataBase.Criteria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
public class Store {
    //ArrayList<Group> groups; //список груп товарів
    /**
     * Конструктор створення складу
     */
    /**
     * Додає групу товарів до складу
     *
     * @param groupName назва групи товарів, яку необхідно додати
     * @return true, якщо додавання успішне, в іншому випадку - false
     */
    public boolean addGroup(String groupName, String description) {
        try {
            new Connector().insertGroup(groupName, description);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    /**
     * Видаляє групу товарів зі складу
     *
     * @param name назва групи товаів, яку необхідно видалити
     * @return true, якщо видалення успішне, в іншому випадку - false
     */
    public boolean deleteGroup(String name) {
        try {
            new Connector().deleteGroup(name);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public Group getGroup(String name) {
        try {
            return new Connector().readGroup(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    /**
     * Повертає список усіх продуктів складу
     */
    public ArrayList<Product> getProducts() {
        return new Connector().productListByCriteria(new Criteria());
    }
    public ArrayList<Product> findProducts(String searchText) {
        String text = searchText.toLowerCase();
        return new Connector().productListByCriteria(new Criteria().setName(text));
    }
    public ArrayList<Group> getGroups() {
        return new Connector().groupListByCriteria(new Criteria());
    }

    public ArrayList<String> getGroupsNames() {
        ArrayList<Group> groups = new Connector().groupListByCriteria(new Criteria());
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            names.add(groups.get(i).getName().toLowerCase());
        }
        return names;
    }

    public ArrayList<String> getProductNames() {
        ArrayList<Product> products = new Connector().productListByCriteria(new Criteria());
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            names.add(products.get(i).getName().toLowerCase());
        }
        return names;
    }

    public String toString() {
        String str = "";
        ArrayList<Group> groups = new Connector().groupListByCriteria(new Criteria());
        for (Group group : groups) {
            str += group + "\n\n";
        }
        return str;
    }
}