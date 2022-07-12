package Interface.Program;
public class Product {
    private String name;
    private String description;
    private String brand;
    private boolean deleted = false;
    private double price;
    private int number;
    private static int numberAll = 0;

    /**
     * Стандартний конструктор для створення об'єкта Product
     *
     * @param name        назва товару
     * @param price       ціна товару
     * @param number      кількість товару на складі
     * @param brand       виробник товару
     * @param description опис товару
     */
    public Product(String name, double price, int number, String brand,
                   String description) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.brand = brand;
        this.description = description;
        numberAll++;
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

    public static int getNumberAll() {
        return numberAll;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}