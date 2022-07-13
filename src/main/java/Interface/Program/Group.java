package Interface.Program;
import java.util.ArrayList;
import java.util.Objects;
public class Group {
    private String name;
    private String description;

    /**
     * Конструктор для створення групи товарів
     *
     * @param name
     */

    private ArrayList<Product> products;

    public Group(String name, String description) {
        this.name = name;
        this.description = description;

        this.products = new ArrayList<>();
    }
    public Group(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String str = name + " (" + description + ") \n";
        /*for (Product product : products) {
            str += product + "\n";
        }*/
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

    public ArrayList<Product> getProducts() {
        return products;
    }
}
