package Interface.GUI;

import Interface.Program.Group;
import Interface.Program.Product;
import Interface.Program.Store;
import Structure.Client.ClientTCP;
import Structure.Client.User;
import Structure.Commands.UserCommand;
import org.json.JSONObject;

public class Tester {

    public static void main(String[] args) throws Exception {

        Store store = new Store();
        Product TV = new Product("Телевізор", 37000, 5, "LG", "Діагональ екрана 55 / Роздільна здатність 3840x2160");
        Product fridge = new Product("Холодильник", 19999, 15, "BOSCH", "двокамерний / 400 л");
        Product washingMachine = new Product("Пральна машина", 18300, 22, "Samsung", "7 кг / 84.8 x 59.8 x 59 см");
        Product smartphone = new Product("Смартфон", 7000, 5, "Sumsung", "Тут може бути Ваша реклама :)");
        Product laptop = new Product("Ноутбук", 49999, 11, "Asus", "Опис ноутбука");
        Product cooker = new Product("Плита", 8400, 8, "Lux", "8 кг");

        //створюємо групу товарів
        Group appliances = new Group("Техніка", "якийсь опис");

        //додаємо вже створені товари до групи
        appliances.addProduct(TV);
        appliances.addProduct(fridge);
        appliances.addProduct(washingMachine);
        appliances.addProduct(smartphone);
        appliances.addProduct(laptop);
        appliances.addProduct(cooker);


        //створюємо інші товари
        Product piano = new Product("Фортепіано", 130000, 2, "Steinway & sons", "Розміри (132-152-68 см) / Вага (305 кг)");
        Product cello = new Product("Віолончель", 21000, 4, "STENTOR", "Розмір 4/4");
        Product violin = new Product("Скрипка", 29999, 15, "Yamaha", "Розмір 4/4");
        Product synt = new Product("Синтезатор", 12000, 3, "Yamaha", "Тут має бути якийсь опис");
        Product royalPiano = new Product("Рояль", 630000, 1, "Kawai", "Напоганий такий рояль");
        Product sax = new Product("Саксофон", 13000, 12, "Yamaha", "Опис інструменту");
        Product guitar = new Product("Акустична гітара", 5000, 20, "Stagg", "Струни окремо :)");
        Product basGuitar = new Product("Бас гітара", 11300, 12, "Yamaha", "Тут можу бути Ваша реклама:)");

        //--------------------------------------------------------------------------------------------------

        //створюємо іншу групу
        Group instruments = new Group("Музичні інструменти", "якийсь опис");

        //додаємо товари до іншої групи
        instruments.addProduct(piano);
        instruments.addProduct(cello);
        instruments.addProduct(violin);
        instruments.addProduct(synt);
        instruments.addProduct(royalPiano);
        instruments.addProduct(sax);
        instruments.addProduct(guitar);
        instruments.addProduct(basGuitar);

        store.addGroup(instruments);
        store.addGroup(appliances);

        ProgramWindow w = new ProgramWindow(store);
    }
}
