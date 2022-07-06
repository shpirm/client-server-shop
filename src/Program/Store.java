package Program;
import java.util.ArrayList;
import java.util.Locale;
public class Store {
    ArrayList<Group> groups; //список груп товарів
    /**
     * Конструктор створення складу
     */
    public Store() {
        groups = new ArrayList<Group>(1);
    }
    /**
     * Додає групу товарів до складу
     *
     * @param group група товарів, яку необхідно додати
     * @return true, якщо додавання успішне, в іншому випадку - false
     */
    public boolean addGroup(Group group) {
        return groups.add(group);
    }
    /**
     * Видаляє групу товарів зі складу
     *
     * @param name назва групи товаів, яку необхідно видалити
     * @return true, якщо видалення успішне, в іншому випадку - false
     */
    public boolean deleteGroup(String name) {
        return groups.remove(new Group(name));
    }
    public Group getGroup(String name) {
        try {
            return groups.get(groups.indexOf(new Group(name)));
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
    /**
     * Повертає список усіх продуктів складу
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>(1);
        for (Group group : groups) {
            for (Product product : group.products) {
                products.add(product);
            }
        }
        return products;
    }
    public ArrayList<Product> findProducts(String searchText) {
        String text = searchText.toLowerCase();
        ArrayList<Product> products = new ArrayList<>(1);
        for (Group group : groups) {
            if (group != null) {
                if (group.getProducts()!=null) {
                    for (Product product : group.getProducts()) {
                        if (product != null) {
                            String name
                                    =product.getName().toLowerCase(Locale.ROOT);
                            if (name.contains(text)) {
                                products.add(product);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(products.size());
        return products;
    }
    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<String> getGroupsNames() {
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            names.add(groups.get(i).getName().toLowerCase());
        }
        return names;
    }

    public ArrayList<String> getProductNames() {
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            Group g = groups.get(i);
            for(int j = 0; j < g.getProducts().size(); j++){
                names.add(g.getProducts().get(j).getName().toLowerCase());
            }
        }
        return names;
    }

    public String toString() {
        String str = "";
        for (Group group : groups) {
            str += group + "\n\n";
        }
        return str;
    }
}