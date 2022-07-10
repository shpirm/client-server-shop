package Interface.Program;
import java.util.ArrayList;
import java.util.Objects;
public class Group {
    public String name;
    public String description;
    public ArrayList<Product> products;
    /**
     * Конструктор для створення групи товарів
     *
     * @param name
     */
    public Group(String name, String description) {
        this.name = name;
        this.description = description;
        products = new ArrayList<>(5);
    }
    public Group(String name) {
        this.name = name;
    }
    /**
     * Додає об'єкт класу Product до групи товарів
     *
     * @param product товар, який необхідно додати до групи
     * @return true - якщо додавання успішне, в іншому разі - false
     */
    public boolean addProduct(Product product) {
        return products.add(product);
    }
    /**
     * Видаляє об'єкт класу Product з групи товарів
     *
     * @param productName назва товару, який треба видалити
     * @return true - якщо видалення успішне, в іншому разі - false
     */
    public boolean deleteProduct(String productName) {
        return products.remove(new Product(productName));
    }
    public Product getProduct(String name) {
        try {
            return products.get(products.indexOf(new Product(name)));
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<Product> getProducts() {
        ArrayList<Product> productsAvailable = new ArrayList<>(1);
        if (this.products != null) {
            for (Product p : products) {
                if (p != null) {
                    if (!p.isDeleted()) {
                        productsAvailable.add(p);
                    }
                }
            }
        }
        return productsAvailable;
    }
    @Override
    public String toString() {
        String str = name + " (" + description + ") \n";
        for (Product product : products) {
            str += product + "\n";
        }
        return str;
    }
    /**
     * Повертає true, якщо назви двох груп товарів співпадають, в
     іншому випадку повертає false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
