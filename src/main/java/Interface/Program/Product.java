package Interface.Program;
public class Product {
    private int productID;
    private String name;
    private String description;
    private String brand;
    private double price;
    private int number;
    private int groupID;

    /**
     * Стандартний конструктор для створення об'єкта Product
     *
     * @param name        назва товару
     * @param price       ціна товару
     * @param number      кількість товару на складі
     * @param brand       виробник товару
     * @param description опис товару
     */
    public Product(int productID, String name, int number, double price, int groupID, String brand,
                   String description) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.number = number;
        this.groupID = groupID;
        this.brand = brand;
        this.description = description;

    }

    /**
     * Конструктор лише для видалення об'єкта класу Product
     *
     * @param name назва товару
     */
    public Product(String name) {
        this.name = name;
    }

    public void add(int n) {
        number += n;
    }

    public void remove(int n) {
        if (number == 0) return;
        if (n > number) {
            number = 0;
        } else {
            number -= n;
        }
    }

    /**
     * Повертає true, якщо назви двох товарів співпадають,
     * в іншому випадку повертає false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public String toString() {
        return name + "\n" +
                " Ціна: " + price + " грн \n" +
                " Кількість: " + number + " штук(и) \n" +
                " Виробник: " + brand + "\n" +
                " Опис: " + description;
    }

    public int getProductID() { return productID; }

    public void setProductID(int productID) { this.productID = productID; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}